package com.bridgelabz.fundoo.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import lombok.experimental.UtilityClass;

//import lombok.experimental.UtilityClass;

//import lombok.experimental.UtilityClass;

//@UtilityClass
//@UtilityClass
public class EmailUtil {
	
	//@Autowired
	//private JavaMailSender javaMailSender;
	
	public static void send(String toEmail, String subject, String body) throws MessagingException {
		
		 final String fromEmail = "fundooapp1@gmail.com";//"fundoodata413@gmail.com";
		 final String password = "fundoo1@pp";//"fundoo@123!";
		 
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getInstance(properties,auth);
		
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Context-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress("no_reply@gmail.com","NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse("mohitkr1301@gmail.com",false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			System.out.println(msg);
			Transport.send(msg);
			System.out.println("Email sent Successfully!!");
		}
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
				
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper;
//		
//		helper = new MimeMessageHelper(message, true);
//		
//		helper.setSubject(subject);
//		helper.setTo(to);
//		helper.setText(body, true);
//		
//		javaMailSender.send(message);
	}
	
	
}
