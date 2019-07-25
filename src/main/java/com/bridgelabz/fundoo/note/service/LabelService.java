package com.bridgelabz.fundoo.note.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;


public interface LabelService {
	public Response createLabel(LabelDto labelDto, String token);
	public Response deleteLabel(Long labelId, String token);
	public List<Label> getAllLabels(String token);
	public Response updateLabel(String token, Long labelId, LabelDto labelDto);
	public Response addLabelInNote(String token, Long labelId, Long noteId);
	public Response deleteLabelFromNote(String token, Long labelId, Long noteId);
	public Set<Label> getLabelOfNote(String token, Long noteId);
	public List<Note> getNoteOfLabel(String token, Long labelId);
}
