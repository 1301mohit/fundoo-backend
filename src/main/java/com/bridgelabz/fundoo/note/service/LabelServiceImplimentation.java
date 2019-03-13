package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoo.note.controller.LabelController;
import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.util.StatusUtil;
import com.bridgelabz.fundoo.util.UserToken;

public class LabelServiceImplimentation implements LabelService{
	
	static final Logger logger = LoggerFactory.getLogger(LabelService.class);
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Response createLabel(Long noteId, LabelDto labelDto, String token) throws Exception {
		logger.info("noteId"+noteId);
		logger.info("labelDto"+labelDto);
		logger.info("Token"+token);
		Optional<Note> note = noteRepository.findById(noteId);
		Long userId = UserToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		Label label = modelMapper.map(labelDto, Label.class);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		label.getNotes().add(note.get());
		label.setUser(user.get());
		Response response = StatusUtil.statusInfo("Create a label", "101");
		return response;
	}
	
}
