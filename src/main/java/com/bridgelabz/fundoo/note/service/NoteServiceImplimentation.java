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
public class NoteServiceImplimentation implements NoteService{
	
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
	
	static final Logger logger = LoggerFactory.getLogger(NoteServiceImplimentation.class);
	
	public Response addNote(NoteDto noteDto, String token) throws Exception {
		logger.info("Add a note");
		logger.info("noteDto:"+noteDto);
		logger.info("Token"+token);
		Long userId = UserToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		Note note = modelMapper.map(noteDto, Note.class);
		note.setLastModifiedStamp(LocalDateTime.now());
		note.setCreateStamp(LocalDateTime.now());
		note.setUser(user.get());
		noteRepository.save(note);
		System.out.println(environment.getProperty("status.code.success"));
		Response response = StatusUtil.statusInfo(environment.getProperty("status.addNote.success"), environment.getProperty("status.code.success"));
		return response;
	}
		
	public Response updateNote(Long noteId, String token) throws Exception {
		logger.info("Uddate a note");
		logger.info("NoteId:"+noteId);
		logger.info("Token:"+token);
		Long userId = UserToken.tokenVerify(token);
		Optional<Note> noteAvailable = noteRepository.findById(noteId);
		Note note = noteAvailable.get();
		if(userId == note.getUser().getUserId() && noteAvailable.isPresent()) {
			note.setLastModifiedStamp(LocalDateTime.now());
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo("Successfully updated", "101");
			return response;
		}
		Response response = StatusUtil.statusInfo("User not match", "401");
		return response;
	}
	
	public Response trashNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.trace("Add note in trash");
		Long userId = UserToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		//Optional<Note> note1 = noteRepository.findBynoteId(note.getNoteId());
		//note1.get().setTrash(true);
		if(note.get().getUser().getUserId() == userId) {
			note.get().setTrash(true);
			noteRepository.save(note.get());
			Response response = StatusUtil.statusInfo("Successfully note add to trash", "101");
			return response;
		}
		Response response = StatusUtil.statusInfo("User not match", "401");
		return response;
	}
	
	public Response deleteNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.info("Permanently delete note");
		Optional<Note> note = noteRepository.findById(noteId);
		if(note.get().isTrash()) {
			Long userId = UserToken.tokenVerify(token);
			if(userId == note.get().getUser().getUserId()) {
				noteRepository.delete(note.get());
				Response response = StatusUtil.statusInfo("Delete note Successfully", "201");
				return response;
			}
			else {
				Response response = StatusUtil.statusInfo("User not match", "401");
				return response;
			}
		}
		else {
			Response response = trashNote(noteId,token);
			return response;
		}
	}
	
	@Override
	public Response pinNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.info("Token:"+token);
		Optional<Note> note = noteRepository.findById(noteId);
		Long userId = UserToken.tokenVerify(token);
		if(userId == note.get().getUser().getUserId()) {
			note.get().setPinned(true);
			noteRepository.save(note.get());
			Response response = StatusUtil.statusInfo("Note pin", "201");
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo("User not match", "401");
			return response;
		}
	}
	
	@Override
	public Response archiveNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.info("Token:"+token);
		Optional<Note> note = noteRepository.findById(noteId);
		Long userId = UserToken.tokenVerify(token);
		if(userId == note.get().getUser().getUserId()) {
			note.get().setArchive(true);
			noteRepository.save(note.get());
			Response response = StatusUtil.statusInfo("Note archive", "201");
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo("User not match", "401");
			return response;
		}
	}
	
	@Override
	public List<Note> getAllNotes(String token) throws Exception {
		logger.info("Token:"+token);
		Long userId = UserToken.tokenVerify(token);
		List<Note> list = noteRepository.findAll();
		List<Note> list1 = new ArrayList<Note>();
		for(int i=0; i<=list.size(); i++) {
			if(userId == list.get(i).getUser().getUserId()) {
				list1.add(list.add());
			}
		}
		return null;
	}
		
		
//		Long userId = UserToken.tokenVerify(token);
//		System.out.println("UserId:"+userId);
//		Optional<User> user = userRepository.findByUserId(userId);
//		user.get().getNotes().add(note);
//		note.setCreateStamp(LocalDateTime.now());
//		note.setLastModifiedStamp(LocalDateTime.now());
//		userRepository.save(user.get());
	
	//
	
//	public Response update(Note note, String token) throws Exception {
//		logger.info("note:"+note);
//		logger.info("Token:"+token);
//		logger.trace("Note update");
	
	//
		
		
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
	
	//
//		Long userId = UserToken.tokenVerify(token);
//		Optional<User> user = userRepository.findByUserId(userId);
//		noteRepository.save(note);
//		Response response = StatusUtil.statusInfo(environment.getProperty("11"), "201");
//		return response;	
//	}
	
	//
	
//	public List<Note> getAllNotes(String token) throws Exception {
//		logger.info("Token:"+token);
//		logger.trace("Get all notes");
//		Long userId = UserToken.tokenVerify(token);
//		Optional<User> user = userRepository.findByUserId(userId);
//		return user.get().getNotes();
//	}
	
	



	

	

//	@Override
//	public Response update(Note note, String token) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Response updateNote(Note note, String token) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Response trashNote(Note note, String token) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Response deleteNote(Note note, String token) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
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
