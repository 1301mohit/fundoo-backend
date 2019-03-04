package com.bridgelabz.fundoo.util;

import com.bridgelabz.fundoo.user.model.User;

public class Utility {
	
	public static String getBody(User user, String link) throws Exception {
		return "http://localhost:4200/"+link+"/"+UserToken.generateToken(user.getuserId());
	}
}
