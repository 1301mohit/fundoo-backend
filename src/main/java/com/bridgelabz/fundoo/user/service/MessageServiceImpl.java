package com.bridgelabz.fundoo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.RabbitMq.MessageProducer;
import com.bridgelabz.fundoo.RabbitMq.RabbitMqBody;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.util.Utility;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment environment;
	
//	@Autowired
//	private MessageProducer messageProcedure;
//	
//	@Autowired
//	private RabbitMqBody rabitMqBody;

	@Override
//	public void sendEmail(RabbitMqBody rabbitMqBody) throws Exception {
	public void sendEmail(RabbitMqBody rabbitmqBody) throws Exception{
		System.out.println("Send email");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(rabbitmqBody.getToEmailId());
		mail.setSubject(rabbitmqBody.getSubject());
//		String userActivationLink = Utility.getUrl(userId);
		mail.setText(rabbitmqBody.getUrl());
		System.out.println("Message is ready");
		javaMailSender.send(mail);
		System.out.println("Message is sent");
	}
}
//		System.out.println("Send email");
//		SimpleMailMessage mail = new SimpleMailMessage();
//	//	rabitMqBody.setToEmailId(user.getEmail());
//	//	rabitMqBody.setFromEmailId(environment.getProperty("spring.mail.username"));
//	//	rabitMqBody.setSubject(environment.getProperty("status.register.mail.registration"));
//		mail.setTo(rabbitMqBody.getToEmailId());
////		mail.setFrom(environment.getProperty("spring.mail.username"));
//		mail.setSubject(environment.getProperty("status.register.mail.registration"));
//	//	String userActivationLink = Utility.getUrl(user.getUserId);
//		String userActivationLink = rabbitMqBody.getUrl();
//		//rabitMqBody.setUrl(userActivationLink);
//		System.out.println("Useractivationlink:"+userActivationLink);
//		mail.setText(userActivationLink);
//		System.out.println("Message is ready");
//	//	messageProcedure.sendMessage(userActivationLink);
//		javaMailSender.send(mail);
//	//	messageProcedure.sendMessage(rabitMqBody);
//		System.out.println("Message is sent");
//	}
//}
