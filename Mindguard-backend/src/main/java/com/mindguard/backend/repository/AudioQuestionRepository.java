package com.mindguard.backend.repository;

import java.util.List;

import com.mindguard.backend.model.AudioQuestion;

public interface AudioQuestionRepository {

	List<AudioQuestion> findAll();

}
