package com.bridgelabz.fundoo.applicationconfig;

import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoo.util.EmailUtil;
import com.bridgelabz.fundoo.util.UserToken;
/**
 * Purpose: Configuration
 * 
 * @author  Mohit Kumar
 */
@Configuration
public class ApplicationConfiguration {
	
	@Bean
	public ModelMapper getMapper()
	{
		return new ModelMapper();
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{ 
		return new BCryptPasswordEncoder(); 
	}
	
//	@Bean
//	public DateTimeFormatter getDateTimeFormatter() {
//		
//		return new DateTimeFormatter();
//	}
	
}
