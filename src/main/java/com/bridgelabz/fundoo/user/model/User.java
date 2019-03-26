package com.bridgelabz.fundoo.user.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.bridgelabz.fundoo.note.model.Note;

//import org.hibernate.validator.constraints.UniqueElements;

//import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name="user")
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@NotEmpty(message="please fill the firstname")
	private String firstName;
	
	@NotEmpty(message="please fill the lastname")
	private String lastName;
	
	//@UniqueElements
	@Column(unique = true, nullable=false)
	@NotEmpty(message="please fill the email")
	private String email;
	
	@Pattern(regexp = "[0-9]{10}", message = "Number Should Only Be Digit And 10 digit only")
	@NotEmpty(message="please fill the mobilenumber")
	private String mobileNumber;
	
	@NotEmpty(message="please fill the password")
	private String password;
	
	private LocalDate registeredDate;
	
	private LocalDate accountUpdateDate;
	
	private boolean isVerification;
	
//	@OneToMany(targetEntity=Note.class,cascade=CascadeType.ALL) //fetch=FetchType.LAZY)
//	@JoinColumn(name="use_id")//,referencedColumnName="userId")
//	private List<Note> notes;
	
	public User() 
	{
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long UserId) {
		this.userId = userId;
	}

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isIsverification() {
		return isVerification;
	}

	public void setIsverification(boolean isverification) {
		this.isVerification = isverification;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDate getAccountUpdateDate() {
		return accountUpdateDate;
	}

	public void setAccountUpdateDate(LocalDate accountUpdateDate) {
		this.accountUpdateDate = accountUpdateDate;
	}

//	public List<Note> getNotes() {
//		return notes;
//	}
//
//	public void setNotes(List<Note> notes) {
//		this.notes = notes;
//	}
	
	
}










//	@Override
//	public String toString() {
//		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
//				+ ", mobileNumber=" + mobileNumber + ", password=" + password + ", isVerification=" + isVerification
//				+ "]";
//	}
