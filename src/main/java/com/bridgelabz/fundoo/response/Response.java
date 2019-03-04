package com.bridgelabz.fundoo.response;

import org.springframework.stereotype.Component;

//
//@Getter
//@Setter
//@ToString
@Component
public class Response {
	
	private String statusMessage;
	private int statusCode;
	private String token;
	
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
