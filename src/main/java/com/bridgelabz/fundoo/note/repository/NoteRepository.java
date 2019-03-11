package com.bridgelabz.fundoo.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.model.User;

public interface NoteRepository extends JpaRepository<Note, Long>{
	Optional<Note> findBynoteId(Long noteId);
//	List<Note> findAllNoteByUserId(Long userId);
//	List<Note> findAllByUser(User user);
//	List<Note> findAllByUserId(long userId);
}