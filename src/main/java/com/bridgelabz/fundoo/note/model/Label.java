package com.bridgelabz.fundoo.note.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.bridgelabz.fundoo.user.model.User;

@Entity
public class Label {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long labelId;
	
	private String name;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
//	@ManyToMany
//	@JoinColumn(name="note_id")
//	private List<Note> notes;
	
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
//	public List<Note> getNotes() {
//		return notes;
//	}
//	public void setNotes(List<Note> notes) {
//		this.notes = notes;
//	}
	
//	public Note getNote() {
//		return note;
//	}
//	public void setNote(Note note) {
//		this.note = note;
//	}
}
