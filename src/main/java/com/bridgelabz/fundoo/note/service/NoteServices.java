package com.bridgelabz.fundoo.note.service;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface NoteServices {
	public Response create(NoteDto noteDto, String token) throws Exception;
	public Response update(Note note, String token) throws Exception;
	public List<Note> getAllNotes(String token) throws Exception;
	public Response trash(Note note, String token) throws Exception;
	public Response delete(Note note, String token) throws Exception;
//	public void delete(Long noteId);
//	public void update(Long noteId);
}
