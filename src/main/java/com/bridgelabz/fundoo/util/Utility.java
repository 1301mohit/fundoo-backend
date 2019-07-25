package com.bridgelabz.fundoo.util;

import com.bridgelabz.fundoo.user.model.User;

public class Utility {
	
	public static String getBody(Long userId, String link) {
		return "http://localhost:4200/"+link+"/"+UserToken.generateToken(userId);
	}
	
	public static String getUrl(Long id)
	{
		return "192.168.0.121:8080/user/"+UserToken.generateToken(id);
	}
}
