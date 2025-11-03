package com.mindguard.backend.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.mindguard.backend.model.MoodEntry;
import com.mindguard.backend.model.User;
import com.mindguard.backend.repository.UserRepository;
import com.mindguard.backend.services.MoodEntryService;
@RestController
@RequestMapping("/api/mood")
@CrossOrigin(origins = "http://localhost:4200")
public class MoodEntryController {

	 @Autowired
	    private MoodEntryService service;

	    @Autowired
	    private UserRepository userRepo;

	    // Everyone logged in can view history (USER sees own, ADMIN/PARENT can fetch others)
	    @GetMapping("/history")
	    public List<MoodEntry> getHistory(@RequestParam String username, @RequestParam String requester) {
	        User reqUser = userRepo.findByUsername(requester);
	        if (reqUser == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requester not found");

	        // USER can only see own history
	        if ("USER".equalsIgnoreCase(reqUser.getRole()) && !requester.equals(username)) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "USER can only see own history");
	        }

	        // ADMIN/PARENT can see any username
	        return service.getHistory(username);
	    }

	    // Only USER can submit mood
	    @PostMapping("/checkin")
	    public MoodEntry checkIn(@RequestBody Map<String, String> body) {
	        String username = body.get("username");
	        String mood = body.get("mood");
	        String note = body.getOrDefault("note", "");

	        User user = userRepo.findByUsername(username);
	        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

	        if (!"USER".equalsIgnoreCase(user.getRole())) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only USER can submit mood");
	        }

	        return service.saveMood(username, mood, note);
	    }
}
