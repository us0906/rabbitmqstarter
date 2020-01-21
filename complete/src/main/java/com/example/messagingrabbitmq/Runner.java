package com.example.messagingrabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	@Value(value = "${mpbridge.messageToSend}")
	private String messageToSend;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message..." + messageToSend);
		rabbitTemplate.convertAndSend(
				MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", messageToSend);
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}

}
