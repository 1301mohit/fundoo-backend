package com.bridgelabz.fundoo.user.service;


import javax.mail.MessagingException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;


public interface UserServices {
	
	public Response register(UserDTO userDTO);
	public Response login(LoginDTO loginuser);
	public String validateEmailId(String token); 
	public Response forgotPassword(String email);
	public Response resetPassword(String token, String password);
	public Response saveProfileImage(String token, MultipartFile file);
	public Resource getImage(String token);
	
}
