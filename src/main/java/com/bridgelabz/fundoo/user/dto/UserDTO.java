package com.bridgelabz.fundoo.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserDTO {

	@NotEmpty(message="Please fill the firstname")
	private String firstName;
	
	@NotEmpty(message="Please fill the lastname")
	private String lastName;
	
	@Email(message="Please enter valid emailid")
	@Column(unique = true, nullable = false)
	@NotEmpty(message="Please fill the email")
	private String email;
	
	@NotEmpty(message = "Please fill the password")
	private String password;
	
	@Pattern(regexp="[0-9]{10}",message="Mobile number not valid")
	@NotEmpty(message = "Please fill the mobile number")
	private String mobileNumber;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	public void setLastName(String name) {
		this.lastName = name;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
}











//@UniqueElements
	//@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$",message="Not valid")
