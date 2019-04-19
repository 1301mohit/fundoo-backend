package com.bridgelabz.fundoo.RabbitMq;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.service.MessageService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageListenerImpl implements MessageListener{
	
	@Autowired
	private RabbitMqBody rabbitMqBody;
	
	@Autowired
	private MessageService messageService;

	@Override
	public void onMessage(byte[] message) throws Exception {
      
		System.out.println(new Date());
		try {
			Thread.sleep(5000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Message Received:"+message);
		String msg = new String(message);
		System.out.println("Message received:"+msg);
		ObjectMapper mapper = new ObjectMapper();
		rabbitMqBody = mapper.readValue(msg, RabbitMqBody.class);
	//	messageService.sendEmail(rabbitMqBody.getUrl(),rabbitMqBody.getToEmailId());
		messageService.sendEmail(rabbitMqBody);
		
//		String toEmailId = rabbitMqBody.getToEmailId();
//		String url = rabbitMqBody.getUrl();
//		String subject = rabbitMqBody.getSubject();
		
	//	messageService.sendEmail(rabbitMqBody);
		
		System.out.println(new Date());
	}
}
