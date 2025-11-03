package com.mindguard.backend.controller;

import com.mindguard.backend.model.MoodRecord;
import com.mindguard.backend.repository.MoodRecordRepository;
import com.mindguard.backend.services.MoodDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "http://localhost:4200")
public class QuizController {

    @Autowired
    private MoodDetectionService moodDetectionService;

    @Autowired
    private MoodRecordRepository moodRecordRepo;

    // ✅ Receive quiz responses and detect mood
    @PostMapping("/submit")
    public Map<String, String> submitQuiz(@RequestBody List<Map<String, Object>> answers) {
        if (answers == null || answers.isEmpty()) {
            throw new RuntimeException("No answers submitted!");
        }

        // Extract username from first answer (all have same user)
        String username = (String) answers.get(0).get("userIdentifier");

        // Convert selected options into array for mood detection
        String[] userAnswers = answers.stream()
                .map(a -> (String) a.get("selectedOption"))
                .toArray(String[]::new);

        // Detect mood and message
        String mood = moodDetectionService.detectMood(userAnswers);
        String message = moodDetectionService.generateMessage(mood);

        // Save mood record
        MoodRecord record = new MoodRecord();
        record.setUsername(username);
        record.setMood(mood);
        record.setMessage(message);
        record.setDateTime(LocalDateTime.now());
        moodRecordRepo.save(record);

        // Return result
        Map<String, String> response = new HashMap<>();
        response.put("mood", mood);
        response.put("message", message);
        return response;
    }

    // ✅ Get user's mood history
    @GetMapping("/mood/{username}")
    public List<MoodRecord> getUserMoodHistory(@PathVariable String username) {
        return moodRecordRepo.findByUsernameOrderByDateTimeDesc(username);
    }

    // ✅ Get all mood records (for admin/parent)
    @GetMapping("/mood/all")
    public List<MoodRecord> getAllMoodHistory() {
        return moodRecordRepo.findAllByOrderByDateTimeDesc();
    }
}
