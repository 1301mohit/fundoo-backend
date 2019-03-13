package com.bridgelabz.fundoo.note.service;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.response.Response;

@Service
public interface LabelService {
	public Response createLabel(Long noteId, LabelDto labelDto, String token) throws Exception;
}
