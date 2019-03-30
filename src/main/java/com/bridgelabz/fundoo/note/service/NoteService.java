package com.bridgelabz.fundoo.note.service;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface NoteService {
	public Response addNote(NoteDto noteDto, String token) throws Exception;
	public Response updateNote(Long noteId,NoteDto noteDto, String token) throws Exception;
	public List<Note> getAllNotes(String token) throws Exception;
	public Response trashNote(Long noteId, String token) throws Exception;
	public Response deleteNote(Long noteId, String token) throws Exception;
	public Response pinNote(Long noteId, String token) throws Exception;
	public Response archiveNote(Long noteId, String token) throws Exception;
	public Response colorOfNote(Long noteId, String token, String color) throws Exception;
//	public void delete(Long noteId);
//	public void update(Long noteId);
	public Response restoreNote(Long noteId, String token) throws Exception;
}
