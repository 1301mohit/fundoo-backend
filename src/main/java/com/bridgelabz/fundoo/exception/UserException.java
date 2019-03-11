package com.bridgelabz.fundoo.exception;

public class UserException extends RuntimeException{
	
	String code;
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException(String code, String message) {
		super(message);
		this.code = code;
	}

}
