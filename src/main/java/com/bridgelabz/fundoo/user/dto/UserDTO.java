package com.bridgelabz.fundoo.user.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

//import org.hibernate.validator.constraints.UniqueElements;

//@Getter
//@Setter
//@ToString
public class UserDTO {

	@NotEmpty(message="Please fill the firstname")
	private String firstName;
	
	@NotEmpty(message="Please fill the lastname")
	private String lastName;
	
	//@UniqueElements
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$",message="Not valid")
	@Column(unique = true, nullable = false)
	@NotEmpty(message="Please fill the email")
	private String email;
	
	@NotEmpty(message = "Please fill the password")
	private String password;
	
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
	
//	@NotNull(message="Can not be null")
//	//@NotEmpty(message="Please fill the name")
//	private String name;
//	
//	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$",message="Not valid")
//	@NotEmpty(message="Please fill the email")
//	private String email;
//	
//	@Pattern(regexp = "[0-9]{10}", message = "Number Should Only Be Digit And 10 digit only")
//	@NotEmpty(message = "Please fill the mobile number")
//	private String mobileNumber;
//	
//	@NotEmpty(message = "Please fill the password")
//	private String password;
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getMobileNumber() {
//		return mobileNumber;
//	}
//
//	public void setMobileNumber(String mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@Override
//	public String toString() {
//		return "UserDTO [name=" + name + ", email=" + email + ", mobileNumber=" + mobileNumber + ", password="
//				+ password + "]";
//	}
	
}
