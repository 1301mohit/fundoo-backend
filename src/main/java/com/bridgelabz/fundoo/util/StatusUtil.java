package com.bridgelabz.fundoo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.response.Response;

@Component
public class StatusUtil {
	
	/**
	 * Purpose : This method set statusMessage and statusCode and return response object.
	 * 
	 * @param statusMessage
	 * @param statusCode
	 * @return response Object
	 */
	public static Response statusInfo(String statusMessage, String statusCode)
	{
		System.out.println("Status Message:"+statusMessage);
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		return response;
	}
	
	/**
	 * Purpose : This method set statusMessage, statusCode and token response object.
	 * 
	 * @param statusMessage
	 * @param statusCode
	 * @param token
	 * @return response Object
	 */
	public static Response tokenStatusInfo(String statusMessage, String statusCode, String token)
	{
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		response.setToken(token);
		return response;
	}
}
