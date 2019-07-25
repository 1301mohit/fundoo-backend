package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;

public interface NoteService {
	public Response addNote(NoteDto noteDto, String token);
	public Response updateNote(Long noteId,NoteDto noteDto, String token);
	public List<Note> getAllNotes(String token);
	public Response trashNote(Long noteId, String token);
	public Response deleteNote(Long noteId, String token) ;
	public Response pinNote(Long noteId, String token);
	public Response archiveNote(Long noteId, String token);
	public Response colorOfNote(Long noteId, String token, String color);
//	public void delete(Long noteId);
//	public void update(Long noteId);
	public Response restoreNote(Long noteId, String token);
	public Response remainder(Long noteId, String token, String date);
	public Response removeRemainder(Long noteId, String token);
	public Response addCollaborator(Long noteId, String email, String token);
	public Response removeCollaborator(Long noteId, String email, String token);
	public Set<User> getAllCollaborator(Long noteId,String token);
	public List<Note> getAllListOfNotes(String token, String isArchive, String isTrash);
	public List<Note> getAllSearchNotes(String token, String query);
}
