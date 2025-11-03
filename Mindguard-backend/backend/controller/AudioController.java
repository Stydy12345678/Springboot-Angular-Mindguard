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

    @GetMapping("/questions")
    public List<AudioQuestion> getQuestions() {
        return questionRepo.findAll();
    }

    @GetMapping("/answers")
    public List<AudioAnswer> getAllAnswers() {
        return answerRepo.findAll();
    }

    @PostMapping("/submit-answer")
    public String submitAnswer(
            @RequestParam("file") MultipartFile file,
            @RequestParam("questionId") Long questionId,
            @RequestParam("username") String username,
            @RequestParam("role") String role
    ) {
        try {
        	
                // 🔹 Role check: allow only 'user'
                if (!"user".equalsIgnoreCase(role)) {
                    return "Submission failed: Only users can submit audio answers.";
                }
            // Base folder: automatically inside project
            String baseDirPath = System.getProperty("user.dir") + "/audioanswer";

            // User folder
            File userDir = new File(baseDirPath + "/" + username);
            if (!userDir.exists()) {
                if (!userDir.mkdirs()) {
                    throw new RuntimeException("Cannot create folder for user: " + username);
                }
            }

            // File name
            String fileName = "user_q" + questionId + "_" + System.currentTimeMillis() + ".wav";
            File destFile = new File(userDir, fileName);

            // Save file
            file.transferTo(destFile);

            // Save DB record
            AudioAnswer answer = new AudioAnswer();
            answer.setUsername(username);
            answer.setRole(role);
            answer.setQuestionId(questionId);
            answer.setFilePath("audioanswer/" + username + "/" + fileName);
            answer.setSubmittedAt(LocalDateTime.now());

            answerRepo.save(answer);

            return "Answer submitted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
        }
    }
}
