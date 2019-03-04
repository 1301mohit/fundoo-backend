package com.bridgelabz.fundoo.util;

import com.bridgelabz.fundoo.response.Response;

public class StatusUtil {
	
	/**
	 * Purpose : This method set statusMessage and statusCode and return response object.
	 * 
	 * @param statusMessage
	 * @param statusCode
	 * @return response Object
	 */
	public static Response statusInfo(String statusMessage, int statusCode)
	{
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
	public static Response tokenStatusInfo(String statusMessage, int statusCode, String token)
	{
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
		response.setToken(token);
		return response;
	}
}
