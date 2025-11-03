package com.mindguard.backend.repository;

import com.mindguard.backend.model.AudioAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioAnswerRepo extends JpaRepository<AudioAnswer, Long> {
	
}
