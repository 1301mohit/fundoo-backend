package com.bridgelabz.fundoo.user.model;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@Email(message="Please enter valid emailid")
	@Column(unique = true, nullable=false)
	@NotEmpty(message="please fill the email")
	private String email;
	
	@Pattern(regexp = "[0-9]{10}", message = "Number Should Only Be Digit And 10 digit only")
	@NotEmpty(message="please fill the mobilenumber")
	private String mobileNumber;
	
	@NotEmpty(message="please fill the password")
	private String password;
	
	@JsonIgnore
	private LocalDate registeredDate;
	
	@JsonIgnore
	private LocalDate accountUpdateDate;
	
	private boolean isVerification;
	
	private String profileImage;
	
	@ManyToMany(mappedBy="collaboratedUser")
	@JsonIgnore
	private Set<Note> collaboratedNote;

	public User() 
	{
		
	}

	public Set<Note> getCollaboratedNote() {
		return collaboratedNote;
	}

	public void setCollaboratedNote(Set<Note> collaboratedNote) {
		this.collaboratedNote = collaboratedNote;
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

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

}
















//	@Override
//	public String toString() {
//		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
//				+ ", mobileNumber=" + mobileNumber + ", password=" + password + ", isVerification=" + isVerification
//				+ "]";
//	}


//@ManyToMany
//@JoinTable(name = "collaborate_user",
//   joinColumns = { @JoinColumn(name = "user_id")},
//   inverseJoinColumns = { @JoinColumn(name = "note_id")})
//private Set<Note> collaboratedNote;


//@OneToMany(targetEntity=Note.class,cascade=CascadeType.ALL) //fetch=FetchType.LAZY)
//@JoinColumn(name="use_id")//,referencedColumnName="userId")
//private List<Note> notes;

//public List<Note> getNotes() {
//return notes;
//}
//
//public void setNotes(List<Note> notes) {
//this.notes = notes;
//}
