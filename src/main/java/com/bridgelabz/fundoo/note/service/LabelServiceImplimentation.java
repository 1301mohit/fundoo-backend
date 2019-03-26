package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.note.controller.LabelController;
import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.util.StatusUtil;
import com.bridgelabz.fundoo.util.UserToken;

@Service
public class LabelServiceImplimentation implements LabelService{
	
	static final Logger logger = LoggerFactory.getLogger(LabelService.class);
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Environment environment;

	@Override
	public Response createLabel(LabelDto labelDto, String token) throws Exception {
		logger.info("labelDto"+labelDto);
		logger.info("Token"+token);
		Long userId = UserToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(userId);
		Label label = modelMapper.map(labelDto, Label.class);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		label.setUser(user.get());
		labelRepository.save(label);
		Response response = StatusUtil.statusInfo(environment.getProperty("status.label.create"), environment.getProperty("status.code.success"));
		return response;
	}

	@Override
	public Response deleteLabel(Long labelId, String token) throws Exception {
		logger.info("labelId"+labelId);
		logger.info("Token"+token);
		Long userId = UserToken.tokenVerify(token);
		Optional<Label> label = labelRepository.findById(labelId);
		if(userId == label.get().getUser().getUserId()) {
			labelRepository.deleteById(labelId);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.label.delete"), environment.getProperty("status.code.success"));

			return response;
		}
		else {
			//throw new UserException("User not match","301");
			throw new UserException(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
		}
	}
	
	@Override
	public List<Label> getAllLabels(String token) throws Exception {
		logger.info("Token"+token);
		Long userId = UserToken.tokenVerify(token);
		List<Label> allLabels = labelRepository.findAll();
		List labels = new ArrayList();
		for(int i=0; i<allLabels.size(); i++) {
			if(allLabels.get(i).getUser().getUserId() == userId) {
				labels.add(allLabels.get(i));
			}
		}
		return labels;
	}
	
	@Override
	public Response updateLabel(String token, Long labelId, LabelDto labelDto) throws Exception {
		Long userId = UserToken.tokenVerify(token);
		Optional<Label> label = labelRepository.findById(labelId);
		if(label.get().getUser().getUserId() == userId) {
			label.get().setModifiedDate(LocalDateTime.now());
			label.get().setName(labelDto.getName());
			labelRepository.save(label.get());
			Response response = StatusUtil.statusInfo(environment.getProperty("status.label.update"), environment.getProperty("status.code.success"));
			//Response response = StatusUtil.statusInfo("Update a label", "101");
			return response;
		}
		Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
		//Response response = StatusUtil.statusInfo("User not match", "301");
		return response;
	}
	
//	@Override
//	public Response addLabelInNote(String token, Long labelId, Long noteId, LabelDto labelDto) throws Exception {
//		Long userId = UserToken.tokenVerify(token);
//		Optional<Label> label = labelRepository.findById(labelId);
//		Optional<Note> note = noteRepository.findById(noteId);
//		Optional<User> user = userRepository.findById(userId);
//		note.get().setLastModifiedStamp(LocalDateTime.now());
//		if(label.get().getUser().getUserId() == userId){
//			note.get().getLabel().add(label.get());
//			noteRepository.save(note.get());
//			Response response = StatusUtil.statusInfo("Add label in Note", "301");
//			return response;
//		}
//		else {
//			Label label1 = modelMapper.map(labelDto, Label.class);
//			label1.setCreatedDate(LocalDateTime.now());
//			note.get().getLabel().add(label.get());
//			label1.setUser(user.get());
//			noteRepository.save(note.get());
//			Response response = StatusUtil.statusInfo("Add label in Note and User", "301");
//			return response;
//		}
//	}
	

	@Override
	public Response addLabelInNote(String token, Long labelId, Long noteId) throws Exception {
		Long userId = UserToken.tokenVerify(token);
		Optional<Label> label = labelRepository.findById(labelId);
		Optional<Note> note = noteRepository.findById(noteId);
		if(label.get().getUser().getUserId() == userId){
			note.get().getLabel().add(label.get());
			note.get().setLastModifiedStamp(LocalDateTime.now());
			noteRepository.save(note.get());
			Response response = StatusUtil.statusInfo("Add label in Note", "301");
			return response;
		}
		//Response response = StatusUtil.statusInfo(environment.getProperty("status.user.verify"), environment.getProperty("status.code.error"));
		Response response = StatusUtil.statusInfo("User not match", "301");
		return response;
	}

	@Override
	public Response deleteLabelFromNote(String token, Long labelId, Long noteId) throws Exception {
		Long userId = UserToken.tokenVerify(token);
		Optional<Label> label = labelRepository.findById(labelId);
		Optional<Note> note = noteRepository.findById(noteId);
		if(label.get().getUser().getUserId() == userId) {
			note.get().getLabel().remove(label.get());
			note.get().setLastModifiedStamp(LocalDateTime.now());
			noteRepository.save(note.get());
			Response response = StatusUtil.statusInfo("Delete label from note", "301");
			return response;
		}
		Response response = StatusUtil.statusInfo("User not match", "301");
		return response;
	}

	@Override
	public Set<Label> getLabelOfNote(String token, Long noteId) throws Exception {
		logger.info("getLabelOfNote");
		Long userId = UserToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		Set<Label> label = note.get().getLabel();
		System.out.println("Labels:"+label);
		return label;
	}
	
}
