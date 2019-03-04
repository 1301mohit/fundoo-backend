package com.bridgelabz.fundoo.exception;

public class EmailException extends RuntimeException{
	
	int code;
	
	public EmailException(String message) {
		super(message);
	}

	public EmailException(String message, int code) {
		super(message);
		this.code = code;
	}
}
