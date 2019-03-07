package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.user.model.User;

public interface MessageService {
	public void sendEmail(User user) throws Exception;
}
