package com.bridgelabz.fundoo.note.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.service.LabelService;
import com.bridgelabz.fundoo.response.Response;

@PropertySource("classpath:message.properties")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LabelController {
	
	static final Logger logger = LoggerFactory.getLogger(LabelController.class);
	
	@Autowired
	private LabelService labelService;
	
	@PostMapping("/createLabel/{noteId}")
	public ResponseEntity<Response> createLabel(@RequestParam Long noteId, @RequestBody LabelDto labelDto, @RequestHeader("token") String token) throws Exception{
		logger.info("labelDto"+labelDto);
		logger.info("Token"+token);
		logger.trace("Create label");
		Response response = labelService.createLabel(noteId, labelDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
