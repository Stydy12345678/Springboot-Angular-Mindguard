package com.mindguard.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindguard.backend.model.AudioPlay;

public interface AudioPlayRepository extends JpaRepository<AudioPlay, Long>  {
	 @Query("SELECT a.audioId, COUNT(a) FROM AudioPlay a GROUP BY a.audioId ORDER BY COUNT(a) DESC")
	    List<Object[]> topTracks();

	    @Query("SELECT a.moodAtPlay, COUNT(a) FROM AudioPlay a GROUP BY a.moodAtPlay")
	    List<Object[]> topMoods();
}
