package com.bridgelabz.fundoo.RabbitMq;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.applicationconfig.RabbitMqConfig;

@Component
public class MessageProducer {

	@Autowired
    private AmqpTemplate rabbitTemplate;
 
	public void sendMessage(RabbitMqBody body) {
		System.out.println(new Date());
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,RabbitMqConfig.ROUTING_KEY, body);
	System.out.println("Is listener returned ::: "+body);
		System.out.println(new Date());
	}
 
}
