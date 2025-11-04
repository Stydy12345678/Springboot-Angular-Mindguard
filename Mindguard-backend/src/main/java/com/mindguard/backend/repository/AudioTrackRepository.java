package com.mindguard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindguard.backend.model.AudioTrack;

public interface AudioTrackRepository extends JpaRepository<AudioTrack, Long> {

}