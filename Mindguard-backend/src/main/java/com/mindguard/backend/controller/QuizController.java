package com.mindguard.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindguard.backend.dto.quiz.SubmitAnswerDto;
import com.mindguard.backend.services.QuizService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitQuiz(@RequestBody List<SubmitAnswerDto> answers, HttpSession session) {
        String role = (String) session.getAttribute("role");

        if (!"user".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("Only users can submit the quiz");
        }

        quizService.submitAnswers(answers);
        return ResponseEntity.ok("success");
    }
}
