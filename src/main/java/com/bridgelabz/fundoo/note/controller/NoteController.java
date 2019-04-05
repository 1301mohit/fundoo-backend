package com.bridgelabz.fundoo.note.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.NoteService;
import com.bridgelabz.fundoo.response.Response;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@PropertySource("classpath:message.properties")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/user/note")
public class NoteController {
	
	@Autowired
	private NoteService noteServices;
	
	@Autowired
	Environment environment;
	
	@Autowired
	Response response;
	
	static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@PostMapping("/addNote")
	public ResponseEntity<Response> addNote(@RequestBody NoteDto noteDto, @RequestHeader("token") String token) throws Exception{
		System.out.println("create");
		logger.info("noteDto:"+noteDto);
		logger.trace("Create Note");
		Response response = noteServices.addNote(noteDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/updateNote/{noteId}")
	public ResponseEntity<Response> updateNote(@PathVariable Long noteId, @RequestBody NoteDto noteDto, @RequestHeader("token") String token) throws Exception {
		logger.info("noteId:"+noteId);
		logger.info("Token:"+token);
		logger.trace("Update Note");
		Response response = noteServices.updateNote(noteId,noteDto,token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteNote/{noteId}")
	public ResponseEntity<Response> deleteNote(@PathVariable Long noteId, @RequestHeader("token") String token) throws Exception{
		logger.info("NoteId:"+noteId);
		logger.trace("Delete note");
		Response response = noteServices.deleteNote(noteId,token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/pinNote/{noteId}")
	public ResponseEntity<Response> pinNote(@PathVariable Long noteId, @RequestHeader("token") String token) throws Exception{
		logger.info("NoteId:"+noteId);
		logger.info("Token:"+token);
		Response response = noteServices.pinNote(noteId, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/archiveNote/{noteId}")
	public ResponseEntity<Response> archiveNote(@PathVariable Long noteId, @RequestHeader("token") String token) throws Exception{
		logger.info("NoteId:"+noteId);
		logger.info("Token:"+token);
		Response response = noteServices.archiveNote(noteId, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getAllNotes")
	public ResponseEntity<List<Note>> getAllNotes(@RequestHeader("token") String token) throws Exception{
		logger.info("Token:"+token);
		logger.info("Get all notes");
		List<Note> listOfNotes = noteServices.getAllNotes(token);
		System.out.println("List of Notes"+listOfNotes);
		return new ResponseEntity<>(listOfNotes, HttpStatus.OK);
	}
	
	@PostMapping("/color/{noteId}")
	public ResponseEntity<Response> colorOfNote(@PathVariable Long noteId,@RequestParam String color,@RequestHeader("token") String token) throws Exception{
		logger.info("Token:"+token);
		logger.info("Color is"+color);
		logger.info("NoteId is "+noteId);
		logger.info("Color of note");
		Response response = noteServices.colorOfNote(noteId,token,color);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/restoreNote/{noteId}")
	public ResponseEntity<Response> restoreNote(@PathVariable Long noteId, @RequestHeader("token") String token) throws Exception{
		logger.info("Token:"+token);
		logger.info("NoteId"+noteId);
		Response response = noteServices.restoreNote(noteId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/addRemainder/{noteId}")
	public ResponseEntity<Response> remainder(@PathVariable Long noteId, @RequestHeader("token") String token, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) throws Exception{
		logger.info("Token:"+token);
		logger.info("Date"+date);
		logger.info("NoteId"+noteId); 
		Response response = noteServices.remainder(noteId,token,date);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteRemainder/{noteId}")
	public ResponseEntity<Response> removeRemainder(@PathVariable Long noteId, @RequestHeader("token") String token) throws Exception{
		logger.info("Token:"+token);
		logger.info("NoteId",+noteId);
		Response response = noteServices.removeRemainder(noteId,token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/addCollaborator/{noteId}")
	public ResponseEntity<Response> addCollaborator(@PathVariable Long noteId, @RequestParam String email, @RequestHeader("token") String token) throws Exception{
		logger.info("Token"+token);
		logger.info("Note"+noteId);
		Response response = noteServices.addCollaborator(noteId,email,token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
//	public ResponseEntity<Response> delete(@RequestParam Long noteId, @RequestParam String token){
//		logger.info("noteId:"+noteId);
//		logger.trace("token:"+token);
//		Response response = noteServices.delete(noteId,token);
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
	
	
//	@PostMapping("/getAllNotes")
//	public List<Note> getAllNotes(@RequestParam String token) throws Exception{
//		logger.info("Token:"+token);
//		logger.trace("Get all notes");
//		List<Note> list = noteServices.getAllNotes(token);
//		return list;
//	}
	
//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	
}
