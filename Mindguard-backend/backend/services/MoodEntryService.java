package com.mindguard.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindguard.backend.model.MoodEntry;

import com.mindguard.backend.repository.MoodEntryRepository;


@Service
public class MoodEntryService {
	 
	 @Autowired
	    private MoodEntryRepository repo;

	    public MoodEntry saveMood(String userId, String mood, String note) {
	        LocalDate today = LocalDate.now();
	        MoodEntry existing = repo.findByUserIdAndDate(userId, today);
	        if (existing != null) {
	            existing.setMood(mood);
	            existing.setNote(note);
	            return repo.save(existing);
	        }
	        return repo.save(new MoodEntry(userId, mood, today, note));
	    }

	    public List<MoodEntry> getHistory(String userId) {
	        return repo.findByUserId(userId);
	    }
}