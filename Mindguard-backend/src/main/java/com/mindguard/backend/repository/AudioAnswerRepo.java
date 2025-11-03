package com.mindguard.backend.repository;

import java.util.List;

import com.mindguard.backend.model.AudioAnswer;

public interface AudioAnswerRepo {

	void save(AudioAnswer answer);

	List<AudioAnswer> findAll();

}
