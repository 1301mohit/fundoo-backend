package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.service.LabelService;
import com.bridgelabz.fundoo.response.Response;

@PropertySource("classpath:message.properties")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LabelController {
	
	static final Logger logger = LoggerFactory.getLogger(LabelController.class);
	
	@Autowired
	private LabelService labelService;
	
	@PostMapping("/createLabel")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto, @RequestHeader("token") String token) throws Exception{
		logger.info("labelDto"+labelDto);
		logger.info("Token"+token);
		logger.trace("Create label");
		Response response = labelService.createLabel(labelDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteLabel/{labelId}")
	public ResponseEntity<Response> deleteLabel(@PathVariable Long labelId, @RequestHeader("token") String token) throws Exception{
		logger.info("labelId"+labelId);
		logger.info("Token"+token);
		Response response = labelService.deleteLabel(labelId, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getAllLabels")
	public ResponseEntity<List<Label>> getAllLabels(@RequestHeader("token") String token) throws Exception{
		logger.info("Token"+token);
		List<Label> labels = labelService.getAllLabels(token);
		return new ResponseEntity<>(labels, HttpStatus.OK);
	}
	
	@PostMapping("/updateLabel/{labelId}")
	public ResponseEntity<Response> updateLabel(@RequestHeader("token") String token, @PathVariable Long labelId, @RequestBody LabelDto labelDto) throws Exception{
		logger.info("Token"+token);
		logger.info("LabelId"+labelId);
		logger.info("LabelDto"+labelDto);
		Response response = labelService.updateLabel(token, labelId, labelDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
