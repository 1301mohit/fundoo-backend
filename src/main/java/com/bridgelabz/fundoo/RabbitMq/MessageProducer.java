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
 
	public void sendMessage(String message) {
		System.out.println(new Date());
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,RabbitMqConfig.ROUTING_KEY, message);
//		System.out.println("Is listener returned ::: "+rabbitTemplate.isReturnListener());
		System.out.println(new Date());
	}
 
}
