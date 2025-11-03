package com.mindguard.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindguard.backend.model.ChatMessage;
import com.mindguard.backend.model.User;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUser(User user); // works with username PK
}
