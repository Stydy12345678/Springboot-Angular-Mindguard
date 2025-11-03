package com.mindguard.backend.repository;

import com.mindguard.backend.model.AudioQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioQuestionRepo extends JpaRepository<AudioQuestion, Long> {
	
}

