package com.bridgelabz.fundoo.note.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.note.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{
	Optional<Note> findBynoteId(Long noteId);
}
