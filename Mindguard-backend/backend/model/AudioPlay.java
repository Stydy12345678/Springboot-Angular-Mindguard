package com.mindguard.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="audio_play")
public class AudioPlay {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long audioId;
  private LocalDateTime playedAt = LocalDateTime.now();
  private String moodAtPlay;
  public Long getId() {
	return id;
  }
  public void setId(Long id) {
	this.id = id;
  }
  public Long getUserId() {
	return userId;
  }
  public void setUserId(Long userId) {
	this.userId = userId;
  }
  public Long getAudioId() {
	return audioId;
  }
  public void setAudioId(Long audioId) {
	this.audioId = audioId;
  }
  public LocalDateTime getPlayedAt() {
	return playedAt;
  }
  public void setPlayedAt(LocalDateTime playedAt) {
	this.playedAt = playedAt;
  }
  public String getMoodAtPlay() {
	return moodAtPlay;
  }
  public void setMoodAtPlay(String moodAtPlay) {
	this.moodAtPlay = moodAtPlay;
  }

  // getters/setters
}
