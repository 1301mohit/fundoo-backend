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
		Response response = StatusUtil.statusInfo("Create a label", "101");
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
			Response response = StatusUtil.statusInfo("Delete a label", "101");
			return response;
		}
		else {
			throw new UserException("User not match","301");
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
	
	public Response updateLabel(String token, Long labelId, LabelDto labelDto) throws Exception {
		Long userId = UserToken.tokenVerify(token);
		Optional<Label> label = labelRepository.findById(labelId);
		if(label.get().getUser().getUserId() == userId) {
			label.get().setModifiedDate(LocalDateTime.now());
			label.get().setName(labelDto.getName());
			labelRepository.save(label.get());
			Response response = StatusUtil.statusInfo("Update a label", "101");
			return response;
		}
		Response response = StatusUtil.statusInfo("User not match", "301");
		return response;
	}
}
