package com.bridgelabz.fundoo.note.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.note.model.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
	Optional<Label> findBylabelId(Long labelId);
}
