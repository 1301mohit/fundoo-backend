package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.services.NoteServices;
import com.bridgelabz.fundoo.response.Response;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@PropertySource("classpath:message.properties")
@RestController
//@RequestMapping("/user/note")
public class NoteController {
	
	@Autowired
	private NoteServices noteServices;
	
	@Autowired
	Environment environment;
	
	@Autowired
	Response response;
	
	static final Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@PostMapping("/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto noteDto,@RequestParam String token) throws Exception{
		System.out.println("create");
		logger.info("noteDto:"+noteDto);
		logger.trace("Create Note");
		Response response = noteServices.create(noteDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
//	public ResponseEntity<Response> delete(@RequestParam Long noteId, @RequestParam String token){
//		logger.info("noteId:"+noteId);
//		logger.trace("token:"+token);
//		Response response = noteServices.delete(noteId,token);
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
	@PutMapping("/update")
	public ResponseEntity<Response> update(@RequestBody Note note, @RequestParam String token) throws Exception {
		logger.info("note:"+note);
		logger.info("Token:"+token);
		logger.trace("Update Note");
		Response response = noteServices.update(note,token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PostMapping("/getAllNotes")
	public List<Note> getAllNotes(@RequestParam String token) throws Exception{
		logger.info("Token:"+token);
		logger.trace("Get all notes");
		List<Note> list = noteServices.getAllNotes(token);
		return list;
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> delete(@RequestBody Note note, @RequestParam String token) throws Exception{
		logger.info("Note:"+note);
		logger.trace("Delete note");
		Response response = noteServices.delete(note,token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
