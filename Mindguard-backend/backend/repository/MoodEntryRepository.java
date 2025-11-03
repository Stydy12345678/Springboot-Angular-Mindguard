package com.mindguard.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindguard.backend.model.MoodEntry;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
	  MoodEntry findByUserIdAndDate(String userId, LocalDate date);
	    List<MoodEntry> findByUserId(String userId);
}
