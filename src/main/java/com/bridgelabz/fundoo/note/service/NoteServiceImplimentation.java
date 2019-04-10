package com.bridgelabz.fundoo.note.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.exception.exceptionhandler.FundooExceptionHandler;
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
//	private DateTimeFormatter formatter;
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
		Response response = StatusUtil.statusInfo(environment.getProperty("status.add.success"), environment.getProperty("status.code.success"));
		return response;
	}
		
	public Response updateNote(Long noteId,NoteDto noteDto, String token) throws Exception {
		logger.info("Uddate a note");
		logger.info("NoteId:"+noteId);
		logger.info("Token:"+token);
		Long userId = UserToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Optional<Note> noteAvailable = noteRepository.findById(noteId);
		//Note note = noteAvailable.get();
		if(userId == note.getUser().getUserId()) {
			note.setLastModifiedStamp(LocalDateTime.now());
			note.setLastModifiedStamp(LocalDateTime.now());
			note.setTitle(noteDto.getTitle());
			note.setDescription(noteDto.getDescription());
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.update.success"), environment.getProperty("status.code.success"));
			return response;
		}
		Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
		return response;
	}
	
	public Response trashNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.trace("Add note in trash");
		Long userId = UserToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Optional<Note> note = noteRepository.findById(noteId);
		if(note.getUser().getUserId() == userId) {
			note.setTrash(true);
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.trash.success"), environment.getProperty("status.code.success"));
			return response;
		}
		Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
		return response;
	}
	
	public Response deleteNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.info("Permanently delete note");
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Optional<Note> note = noteRepository.findById(noteId);
		if(note.isTrash()) {
			Long userId = UserToken.tokenVerify(token);
			if(userId == note.getUser().getUserId()) {
			//	note.getLabel().forEach(label -> label.getNotes().remove(note));
			//	note.getCollaboratedUser().forEach(user -> user.getCollaboratedNote().remove(note));
				noteRepository.delete(note);
				Response response = StatusUtil.statusInfo(environment.getProperty("status.delete.success"), environment.getProperty("status.code.success"));
				return response;
			}
			else {
				Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
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
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Optional<Note> note = noteRepository.findById(noteId);
		Long userId = UserToken.tokenVerify(token);
		if(userId == note.getUser().getUserId()) {
			if(note.isPinned()) {
				note.setPinned(false);
				noteRepository.save(note);
				Response response = StatusUtil.statusInfo(environment.getProperty("status.unpin.note"), environment.getProperty("status.code.success"));
				//Response response = StatusUtil.statusInfo("Note unpin", "201");
				return response;
			}
			else {
				note.setPinned(true);
				noteRepository.save(note);
				Response response = StatusUtil.statusInfo(environment.getProperty("status.pin.note"), environment.getProperty("status.code.success"));
				//Response response = StatusUtil.statusInfo("Note pin", "201");
				return response;
			}
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}
	}
	
	@Override
	public Response archiveNote(Long noteId, String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.info("Token:"+token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Optional<Note> note = noteRepository.findById(noteId);
		Long userId = UserToken.tokenVerify(token);
		if(userId == note.getUser().getUserId()) {
			if(note.isArchive()) {
				note.setArchive(false);
				noteRepository.save(note);
				Response response = StatusUtil.statusInfo(environment.getProperty("status.not.archive.note"), environment.getProperty("status.code.success"));
				return response;
			}
			else
			{
				note.setArchive(true);
				noteRepository.save(note);
				Response response = StatusUtil.statusInfo(environment.getProperty("status.archive.note"), environment.getProperty("status.code.success"));
				return response;
			}
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}
	}
	
	@Override
	public List<Note> getAllNotes(String token) throws Exception {
		logger.info("Token:"+token);
		Long userId = UserToken.tokenVerify(token);
		List<Note> listOfNote = noteRepository.findAll();
		List<Note> list = new ArrayList<Note>();
		Optional<User> user = userRepository.findById(userId);
		
		List<Note> collaboratedNote = user.get().getCollaboratedNote().stream().collect(Collectors.toList());
		
		for(int i=0; i<listOfNote.size(); i++) {
			if(userId == listOfNote.get(i).getUser().getUserId()) {
				System.out.println("listOfNotes"+listOfNote.get(i));
				list.add(listOfNote.get(i));
			}
		}
		list.addAll(collaboratedNote);
		System.out.println("list:"+list);
		return list;
	}
	
	public Response colorOfNote(Long noteId, String token, String color) throws Exception {
		logger.info("noteId"+noteId);
		logger.info("color"+color);
		logger.info("Token"+token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Optional<Note> note = noteRepository.findById(noteId);
	//	Note note1 = note.get();
		Long userId = UserToken.tokenVerify(token);
		if(userId == note.getUser().getUserId()) {
			note.setColor(color);
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.note.color"), environment.getProperty("status.code.success"));
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}
	}

	@Override
	public Response restoreNote(Long noteId, String token) throws Exception {
		logger.info("Restore note in Service");
		Long userId = UserToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Note note = noteRepository.findById(noteId).get();
		if(userId == note.getUser().getUserId()) {
			note.setTrash(false);
			note.setLastModifiedStamp(LocalDateTime.now());
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo("Restore note successfully", environment.getProperty("status.code.success"));
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}
	}

	@Override
	public Response remainder(Long noteId, String token, String date) throws Exception {
		logger.info("Remainder Service");
		logger.info("Date:"+date);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
		//Note note = noteRepository.findById(noteId).get();
		Long userId = UserToken.tokenVerify(token);
		if(note.getUser().getUserId() == userId) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date1 = dateFormat.parse(date);
		//	Create a DateTimeFormatter with your required format:
			//DateTimeFormatter dateTimeFormat = new DateTimeFormatter(DateTimeFormatter.BASIC_ISO_DATE);

		//	Next parse the date from the @RequestParam, specifying the TO type as a TemporalQuery:
			//LocalDateTime date1 = dateTimeFormat.parse(date, LocalDateTime::from);
			
//			LocalDateTime date1 = LocalDateTime.parse(date, formatter);
			
			//LocalDateTime date1 = LocalDateTime.parse(date, LocalDateTime::from);
			
			note.setRemainder(date1);
			note.setLastModifiedStamp(LocalDateTime.now());
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.note.remainder.add.success"), environment.getProperty("status.code.success"));
			return response;
		}
		Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
		return response;
	}

	@Override
	public Response removeRemainder(Long noteId, String token) throws Exception {
		logger.info("Remove remainder service");
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException("status.note.absent"));
	//	Note note = noteRepository.findById(noteId).get();
		Long userId = UserToken.tokenVerify(token);
		if(userId == note.getUser().getUserId()) {
			note.setRemainder(null);
			note.setLastModifiedStamp(LocalDateTime.now());
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.note.remainder.remove.success"), environment.getProperty("status.code.success"));
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}
		
	}

	@Override
	public Response addCollaborator(Long noteId, String email,String token) throws Exception {
		logger.info("Add collaborator Service");
		Long userId = UserToken.tokenVerify(token);
		User user = userRepository.findByEmail(email).orElseThrow(()->new UserException(environment.getProperty("status.user.absent")));
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(environment.getProperty("status.user.absent")));
		//Note note = noteRepository.findById(noteId).get();
		if(userId == note.getUser().getUserId()) {
			user.getCollaboratedNote().add(note);
			note.getCollaboratedUser().add(user);
			userRepository.save(user);
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.note.collaborator.add.success"), environment.getProperty("status.code.success"));
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}	
	}
	
	public Response removeCollaborator(Long noteId, String email, String token) throws Exception{
		logger.info("Remove collaborator Service");
		Long userId = UserToken.tokenVerify(token);
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(environment.getProperty("status.user.absent")));
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(environment.getProperty("status.user.absent")));
	//	Note note = noteRepository.findById(noteId).get();
		if(userId == note.getUser().getUserId()) {
			user.getCollaboratedNote().remove(note);
			note.getCollaboratedUser().remove(user);
			userRepository.save(user);
			noteRepository.save(note);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.note.collaborator.remove.success"), environment.getProperty("status.code.success"));
			return response;
		}
		else {
			Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
			return response;
		}
	}
	
	public Set<User> getAllCollaborator(Long noteId,String token) throws Exception {
		logger.info("Get all collaborator Service");
		Long userId = UserToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		Set<User> user2 = note.get().getCollaboratedUser();
		return user2;
	}
}	
	
	
	
//	List<Note> list = noteRepository.findAllByUserId(userId);	
		
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

