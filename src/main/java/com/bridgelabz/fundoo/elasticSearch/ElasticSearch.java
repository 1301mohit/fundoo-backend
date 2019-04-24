package com.bridgelabz.fundoo.elasticSearch;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoo.note.model.Note;

public interface ElasticSearch {
	
	public String createNote(Note note) throws IOException;
	public String deleteNote(Note note) throws IOException;
	public String updateNote(Note note) throws IOException;
	public List<Note> search(String query, Long userId);
	
}
