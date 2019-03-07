package com.bridgelabz.fundoo.note.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.util.StatusUtil;
import com.bridgelabz.fundoo.util.UserToken;

@Service
@PropertySource("classpath:message.properties")
public class NoteServicesImpl implements NoteServices{
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private Response response;
	
//	@Autowired
//	private StatusUtil statusUtil; 
	
	static final Logger logger = LoggerFactory.getLogger(NoteServicesImpl.class);
	
	public Response create(NoteDto noteDto, String token) throws Exception {
		logger.info("noteDto:"+noteDto);
		logger.info("Token"+token);
		logger.trace("Create a note");
		Note note = modelMapper.map(noteDto, Note.class);
		Long userId = UserToken.tokenVerify(token);
		System.out.println("UserId:"+userId);
		Optional<User> user = userRepository.findByUserId(userId);
		user.get().getNotes().add(note);
		note.setCreateStamp(LocalDateTime.now());
		note.setLastModifiedStamp(LocalDateTime.now());
		userRepository.save(user.get());
		Response response = StatusUtil.statusInfo(environment.getProperty("11"), "201");
		return response;
	}
	
	public Response update(Note note, String token) throws Exception {
		logger.info("note:"+note);
		logger.info("Token:"+token);
		logger.trace("Note update");
//		Long userId;
//		userId = UserToken.tokenVerify(token);
//		Optional<User> user = userRepository.findByUserId(userId);
//		Note n = user.get().getNotes().get(note.getNoteId().intValue());
//		Optional<Note> note1 = noteRepository.findBynoteId(note.getNoteId());
//		note1.get().setArchive(note.isArchive());
//		note1.get().setColor(note.getColor());
//		note1.get().setCreateStamp(note.getCreateStamp());
//		note1.get().setDescription(note.getDescription());
//		note1.get().setLastModifiedStamp(LocalDateTime.now());
//		note1.get().setNoteId(note.getNoteId());
//		note1.get().setPinned(note.isPinned());
//		note1.get().setRemainder(note.getRemainder());
//		note1.get().setTitle(note.getTitle());
//		note1.get().setTrash(note.isTrash());
//		System.out.println("note1:"+note1.get());
//		noteRepository.save(note1.get());
		Long userId = UserToken.tokenVerify(token);
		Optional<User> user = userRepository.findByUserId(userId);
		noteRepository.save(note);
		Response response = StatusUtil.statusInfo(environment.getProperty("11"), "201");
		return response;	
	}
	
	public List<Note> getAllNotes(String token) throws Exception {
		logger.info("Token:"+token);
		logger.trace("Get all notes");
		Long userId = UserToken.tokenVerify(token);
		Optional<User> user = userRepository.findByUserId(userId);
		return user.get().getNotes();
	}
	
	public Response trash(Note note, String token) throws Exception {
		logger.info("note:"+note);
		logger.trace("Add note in trash");
		Long userId = UserToken.tokenVerify(token);
		Optional<Note> note1 = noteRepository.findBynoteId(note.getNoteId());
		//note1.get().setTrash(true);
		note.setTrash(true);
		noteRepository.save(note);
		Response response = StatusUtil.statusInfo("Successfully note add to trash", "201");
		return response;
	}
	
	public Response delete(Note note, String token) throws Exception {
		logger.info("note:"+note);
		logger.trace("Permanently delete note");
		if(note.isTrash()) {
			Long userId = UserToken.tokenVerify(token);
			noteRepository.delete(note);
			Response response = StatusUtil.statusInfo("Delete note Successfully", "201");
			return response;
		}
		else {
			return trash(note,token);
		}
	}
	
//	public Response delete(String token) {
//		logger.info("Token:"+token);
//		logger.info("Permanently delete note");
//		Long userId = UserToken.tokenVerify(token);
//		Optional<User> user = userRepository.findByUserId(userId);
//		Note note = user.get().getNotes().remove(index)
//		if(note.isTrash()) {
//			
//		}
//	}
//	@Override
//	public Response create(NoteDto noteDto, String token) {//throws Exception {
//		logger.info("noteDto:"+noteDto);
//		logger.info("Token"+token);
//		logger.trace("Create a note");
//		Long userId = UserToken.tokenVerify(token);
//		System.out.println("UserId1:"+userId);
//	//	noteDto.setUserId(userId);
//		Optional<Note> noteAvailable = noteRepository.findById(userId);
//		Note note = modelMapper.map(noteDto, Note.class);
//		note.setCreateStamp(LocalDate.now());
//		note.setUserId(userId);
//		noteRepository.save(note);
//		System.out.println("UserId2:"+note.getUserId());
//		System.out.println("Date:"+note.getCreateStamp());
//		response.setStatusMessage(environment.getProperty("11"));
//		response.setStatusCode(201);
//		return response;
//	}

//	@Override
//	public Response trash(Long noteId, String token) {
//		
//		Note note = noteRepository.findById(noteId);
//		noteRepository.delete(note);
//	}
	
//	public Response update(NoteDto noteDto, String token) throws Exception {
//		logger.info("noteDto:"+noteDto);
//		logger.trace("Note update");
//		Long userId = UserToken.tokenVerify(token);
//		Note note = modelMapper.map(noteDto, Note.class);
//		Optional<Note> noteAvailable = noteRepository.findById(note.getNoteId());
//		if(noteAvailable.isPresent() && userId == note.getUserId()) {
//			note.setLastModifiedStamp(LocalDate.now());
//			noteRepository.save(note);
//			response.setStatusMessage("update complete");
//			response.setStatusCode(22);
//			return response;
//		}
//		else {
//			response.setStatusMessage("Not updated");
//			response.setStatusCode(23);
//			return response;
//		}
//	}

//	@Override
//	public Response updateNote(NoteDto note) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Response create(NoteDto noteDto, String token) throws Exception {
//		logger.info("UserDto:"+noteDto);
//		logger.info("Token:"+token);
//		long userId = UserToken.tokenVerify(token);
//		Note note = modelMapper.map(noteDto, Note.class);
//		List<Note> noteList = new ArrayList<>();
//		note.setCreateStamp(LocalDate.now());
//		Note newNote = noteRepository.save(note);
//		List<Note> listOfNotes = listOfNotes(userId);
//		
//	}
}
