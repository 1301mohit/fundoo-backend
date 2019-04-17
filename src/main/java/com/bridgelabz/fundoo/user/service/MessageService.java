package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.RabbitMq.RabbitMqBody;
import com.bridgelabz.fundoo.user.model.User;

public interface MessageService {
	public void sendEmail(RabbitMqBody rabitMqBody) throws Exception;
}
