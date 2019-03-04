package com.bridgelabz.fundoo.exception;

public class UserException extends RuntimeException{
	
	int code;
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException(int code, String message) {
		super(message);
		this.code = code;
	}

}
