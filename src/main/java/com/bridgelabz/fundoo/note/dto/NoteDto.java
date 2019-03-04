package com.bridgelabz.fundoo.note.dto;

import java.time.LocalDateTime;

//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@ToString
public class NoteDto {
	
	//private Long userId;
	private String title;
	private String description;
	private boolean isPin;
	private String color;
//	private String image;
	private boolean isArchive;
//	private boolean isTrash;
//	private LocalDateTime remainder;
	
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setContent(String description) {
		this.description = description;
	}
	public boolean isPin() {
		return isPin;
	}
	public void setPin(boolean isPinned) {
		this.isPin = isPinned;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
//	public String getImage() {
//		return image;
//	}
//	public void setImage(String image) {
//		this.image = image;
//	}
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
//	public boolean isTrash() {
//		return isTrash;
//	}
//	public void setTrash(boolean isTrash) {
//		this.isTrash = isTrash;
//	}
//	public LocalDateTime getRemainder() {
//		return remainder;
//	}
//	public void setRemainder(LocalDateTime remainder) {
//		this.remainder = remainder;
//	}
}

