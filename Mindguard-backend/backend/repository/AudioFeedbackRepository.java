package com.mindguard.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindguard.backend.model.AudioFeedback;

public interface AudioFeedbackRepository extends JpaRepository<AudioFeedback, Long> {
	 List<AudioFeedback> findByAudioId(Long audioId);
	    @Query("SELECT AVG(f.rating) FROM AudioFeedback f WHERE f.audioId = :audioId")
	    Double averageRatingByAudioId(Long audioId);
}
