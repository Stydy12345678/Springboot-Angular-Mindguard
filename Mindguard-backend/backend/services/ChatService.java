package com.mindguard.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mindguard.backend.model.ChatMessage;
import com.mindguard.backend.model.QuestionChat;
import com.mindguard.backend.model.User;
import com.mindguard.backend.repository.ChatRepository;
import com.mindguard.backend.repository.QuestionChatRepository;
import com.mindguard.backend.repository.UserRepository;

@Service
public class ChatService {

	 private final ChatRepository chatRepo;
	    private final QuestionChatRepository questionRepo;
	    private final UserRepository userRepo;

	    public ChatService(ChatRepository chatRepo, QuestionChatRepository questionRepo, UserRepository userRepo) {
	        this.chatRepo = chatRepo;
	        this.questionRepo = questionRepo;
	        this.userRepo = userRepo;
	    }

	    public Map<String, Object> getNextQuestion(String username, String answer) {
	        User user = userRepo.findByUsername(username);
	        if (user == null) throw new RuntimeException("User not found: " + username);

	        List<QuestionChat> questions = questionRepo.findAll();
	        if (questions.isEmpty()) throw new RuntimeException("No questions in DB!");

	        int index = user.getLastQuestionIndex() != null ? user.getLastQuestionIndex() : 0;

	        // Save user answer
	        if (answer != null && !answer.isBlank()) {
	            chatRepo.save(new ChatMessage(user, "User", answer));
	        }

	        Map<String, Object> res = new HashMap<>();

	        // All questions done
	        if (index >= questions.size()) {
	            res.put("message", "🎉 Session complete! Great job taking time for yourself.");
	            return res;
	        }

	        // Send current question
	        QuestionChat q = questions.get(index);
	        chatRepo.save(new ChatMessage(user, "Bot", q.getQuestionText()));

	        res.put("question", q.getQuestionText());
	        res.put("options", q.getOptions());
	        res.put("botIcon", "🤖");

	        // Increment index for next question
	        user.setLastQuestionIndex(index + 1);
	        userRepo.save(user);

	        return res;
	    }

	    public List<ChatMessage> getChatHistory(String username) {
	        User user = userRepo.findByUsername(username);
	        if (user == null) throw new RuntimeException("User not found: " + username);
	        return chatRepo.findByUser(user);
	    }

	    public void resetUserQuiz(String username) {
	        User user = userRepo.findByUsername(username);
	        if (user != null) {
	            user.setLastQuestionIndex(0);
	            userRepo.save(user);
	        }
	    }
}
