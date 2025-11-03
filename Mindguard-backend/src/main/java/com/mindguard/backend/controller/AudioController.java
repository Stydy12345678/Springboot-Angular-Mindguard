package com.mindguard.backend.controller;

import com.mindguard.backend.model.AudioAnswer;
import com.mindguard.backend.model.AudioQuestion;
import com.mindguard.backend.repository.AudioAnswerRepo;
import com.mindguard.backend.repository.AudioQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/audio")
public class AudioController {

    @Autowired
    private AudioQuestionRepo questionRepo;

    @Autowired
    private AudioAnswerRepo answerRepo;

    // ✅ Get all questions
    @GetMapping("/questions")
    public List<AudioQuestion> getQuestions() {
        return questionRepo.findAll();
    }

    // ✅ Get all answers
    @GetMapping("/answers")
    public List<AudioAnswer> getAllAnswers() {
        return answerRepo.findAll();
    }

    // ✅ Submit audio answer (using mood sent from frontend)
    @PostMapping("/submit-answer")
    public String submitAnswer(
            @RequestParam("file") MultipartFile file,
            @RequestParam("questionId") Long questionId,
            @RequestParam("username") String username,
            @RequestParam("role") String role,
            @RequestParam(value = "mood", required = false) String mood
    ) {
        try {
            if (username == null || username.trim().isEmpty()) username = "UnknownUser";
            if (role == null || role.trim().isEmpty()) role = "user";

            if (!"user".equalsIgnoreCase(role)) {
                return "❌ Only users can submit audio answers.";
            }

            // 📁 Save audio file
            String baseDirPath = System.getProperty("user.dir") + "/audioanswer";
            File userDir = new File(baseDirPath + "/" + username);
            if (!userDir.exists()) userDir.mkdirs();

            String fileName = "user_q" + questionId + "_" + System.currentTimeMillis() + ".wav";
            File destFile = new File(userDir, fileName);
            file.transferTo(destFile);

            // ✅ Use mood from frontend (don’t overwrite)
            if (mood == null || mood.trim().isEmpty()) {
                mood = "Unknown";
            }

            // 💾 Save to DB
            AudioAnswer answer = new AudioAnswer();
            answer.setUsername(username);
            answer.setRole(role);
            answer.setQuestionId(questionId);
            answer.setFilePath("audioanswer/" + username + "/" + fileName);
            answer.setSubmittedAt(LocalDateTime.now());
            answer.setMood(mood);

            answerRepo.save(answer);

            // ✅ Show frontend-detected mood in response
            return "✅ Answer submitted successfully! Detected mood (from frontend): " + mood;

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Upload failed: " + e.getMessage();
        }
    }
}
