package com.bridgelabz.fundoo.RabbitMq;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MessageListenerImpl implements MessageListener{

	@Override
	public void onMessage(String message) {
		
		System.out.println(new Date());
//		try {
//		   Thread.sleep(5000);
//		}catch (InterruptedException e) {
//		    e.printStackTrace();
//		 }
		 System.out.println("Message Received:"+message);
		 String msg = new String(message);
		 
		 System.out.println(new Date());
	}
}
