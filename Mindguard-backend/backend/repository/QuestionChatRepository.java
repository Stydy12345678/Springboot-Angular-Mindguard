package com.mindguard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindguard.backend.model.QuestionChat;

@Repository
public interface QuestionChatRepository extends JpaRepository<QuestionChat, Long> {}

