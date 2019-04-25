package com.bridgelabz.fundoo.RabbitMq;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.note.model.Note;

@Component
public class SearchNoteBody {

	private Note note;
	private SearchNoteOperation noteOperation;
	
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public SearchNoteOperation getNoteOperation() {
		return noteOperation;
	}
	public void setNoteOperation(SearchNoteOperation noteOperation) {
		this.noteOperation = noteOperation;
	}
	
	@Override
	public String toString() {
		return "SearchNoteBody [note=" + note + ", noteOperation=" + noteOperation + "]";
	}
	
}
