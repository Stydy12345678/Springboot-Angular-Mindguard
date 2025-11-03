package com.mindguard.backend.controller;

import com.mindguard.backend.model.ResetRequest;
import com.mindguard.backend.model.User;
import com.mindguard.backend.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

    private final UserRepository repo;

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (repo.existsById(user.getUsername())) {
            return "already";  // username existsANMAYEE
            
        }
        repo.save(user);       // save user as plain text
        return "success";      // registration successful
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
        User existingUser = repo.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("username", existingUser.getUsername());
            session.setAttribute("role", existingUser.getRole());

            // Return JSON object instead of plain string
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("role", existingUser.getRole());

            return ResponseEntity.ok(response);
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", "fail");
        return ResponseEntity.status(401).body(response);
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetRequest req) {
        User existingUser = repo.findByUsername(req.getUsername());
        if (existingUser == null) {
            return "nouser";
        }
        
        existingUser.setPassword(req.getNewPassword());
        repo.save(existingUser);
        return "success";
    }
    
}
