package com.bridgelabz.fundoo.note.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.response.Response;


public interface LabelService {
	public Response createLabel(LabelDto labelDto, String token) throws Exception;
	public Response deleteLabel(Long labelId, String token) throws Exception;
	public List<Label> getAllLabels(String token) throws Exception;
	public Response updateLabel(String token, Long labelId, LabelDto labelDto) throws Exception;
}
