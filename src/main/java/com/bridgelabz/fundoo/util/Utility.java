package com.bridgelabz.fundoo.util;

import com.bridgelabz.fundoo.user.model.User;

public class Utility {
	
	public static String getBody(User user, String link) throws Exception {
		return "http://localhost:4200/"+link+"/"+UserToken.generateToken(user.getUserId());
	}
	
	public static String getUrl(Long id) throws Exception
	{
		return "192.168.0.84:8080/user/"+UserToken.generateToken(id);
	}
}
