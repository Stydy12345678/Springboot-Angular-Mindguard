package com.mindguard.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindguard.backend.model.ChatMessage;
import com.mindguard.backend.services.ChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

	 private final ChatService chatService;

	    public ChatController(ChatService chatService) { 
	        this.chatService = chatService; 
	    }

	    @PostMapping("/nextQuestion")
	    public Map<String, Object> nextQuestion(@RequestBody Map<String, String> body) {
	        String username = body.get("username");
	        String answer = body.get("answer");
	        return chatService.getNextQuestion(username, answer);
	    }

	    @GetMapping("/history")
	    public List<ChatMessage> getHistory(@RequestParam String username) {
	        return chatService.getChatHistory(username);
	    }

	    @PostMapping("/reset")
	    public void resetQuiz(@RequestParam String username) {
	        chatService.resetUserQuiz(username);
	    }
}

	



