package com.bridgelabz.fundoo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.util.Utility;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment environment;

	@Override
	public void sendEmail(User user) throws Exception {
		System.out.println("Send email");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(environment.getProperty("spring.mail.username"));
		mail.setSubject(environment.getProperty("status.register.mail.registration"));
		String userActivationLink = Utility.getUrl(user.getUserId());
		System.out.println("Useractivationlink:"+userActivationLink);
		mail.setText(userActivationLink);
		System.out.println("Message is ready");
		javaMailSender.send(mail);
		System.out.println("Message is sent");
	}
}
