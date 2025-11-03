package com.mindguard.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mindguard.backend.model.AudioFeedback;
import com.mindguard.backend.model.AudioPlay;
import com.mindguard.backend.model.AudioTrack;
import com.mindguard.backend.model.Category;
import com.mindguard.backend.repository.AudioFeedbackRepository;
import com.mindguard.backend.repository.AudioPlayRepository;
import com.mindguard.backend.repository.AudioTrackRepository;
import com.mindguard.backend.repository.CategoryRepository;

@RestController
@RequestMapping("/api/audiotherapy")
@CrossOrigin(origins = "http://localhost:4200")
public class AudioTherapyController {

    @Autowired
    private AudioTrackRepository trackRepo;

    @Autowired
    private AudioFeedbackRepository feedbackRepo;

    @Autowired
    private AudioPlayRepository playRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    // ===== Audio Tracks =====
    @GetMapping("/tracks")
    public List<AudioTrack> getAllTracks() {
        return trackRepo.findAll();
    }

    @PostMapping("/tracks/upload")
    public AudioTrack uploadTrack(@RequestBody AudioTrack track) {
        return trackRepo.save(track);
    }

    @DeleteMapping("/tracks/delete/{id}")
    public void deleteTrack(@PathVariable Long id) {
        trackRepo.deleteById(id);
    }

    // ===== Feedback =====
    @PostMapping("/feedback")
    public AudioFeedback submitFeedback(@RequestBody AudioFeedback fb) {
        return feedbackRepo.save(fb);
    }

    @GetMapping("/feedback/{audioId}")
    public List<AudioFeedback> getFeedback(@PathVariable Long audioId) {
        return feedbackRepo.findByAudioId(audioId);
    }

    @GetMapping("/feedback/{audioId}/avg")
    public Double getAvgRating(@PathVariable Long audioId) {
        Double avg = feedbackRepo.averageRatingByAudioId(audioId);
        return avg != null ? avg : 0.0;
    }

    // ===== Category CRUD =====
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @PostMapping("/categories/create")
    public Category createCategory(@RequestBody Category c) {
        return categoryRepo.save(c);
    }

    @PutMapping("/categories/update/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category payload) {
        Category c = categoryRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        c.setName(payload.getName());
        c.setDescription(payload.getDescription());
        return categoryRepo.save(c);
    }

    @DeleteMapping("/categories/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepo.deleteById(id);
    }

    // ===== Audio Plays / Analytics =====
    @PostMapping("/log-play")
    public AudioPlay logPlay(@RequestBody AudioPlay play) {
        return playRepo.save(play);
    }

    @GetMapping("/analytics/top-tracks")
    public List<Map<String,Object>> topTracks() {
        List<Object[]> rows = playRepo.topTracks();
        return rows.stream().map(r -> Map.of(
                "audioId", r[0],
                "title", trackRepo.findById((Long) r[0]).map(AudioTrack::getTitle).orElse("Unknown"),
                "plays", r[1]
        )).toList();
    }

    @GetMapping("/analytics/top-moods")
    public List<Map<String,Object>> topMoods() {
        List<Object[]> rows = playRepo.topMoods();
        return rows.stream().map(r -> Map.of(
                "mood", r[0],
                "count", r[1]
        )).toList();
    }
}
