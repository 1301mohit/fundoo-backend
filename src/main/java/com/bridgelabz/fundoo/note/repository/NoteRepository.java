package com.bridgelabz.fundoo.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.model.User;

public interface NoteRepository extends JpaRepository<Note, Long>{
	Optional<Note> findBynoteId(Long noteId);
	
//	@Query("Select * from note n where n.userId = ?1", nativeQuery = true)
//	List<Note> findByUserId(Long userId);
	

//	@Query(value="Select * from note n where n.note_id = :id" , nativeQuery = true)
//	List<Note> findByUser(@Param("id")Long id);
	@Query(value = "select * from note where user_id = :userId AND is_archive = :archive AND is_trash = :trash" , nativeQuery = true)
	Optional<List<Note>> findAllNotesByUserId(@Param("userId") Long userId, @Param("archive") Boolean is_archive, @Param("trash") Boolean is_trash);	
//	List<Note> findAllNoteByUserId(Long userId);
//	List<Note> findAllByUser(User user);
//	List<Note> findAllByUserId(long userId);
}