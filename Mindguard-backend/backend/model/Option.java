package com.mindguard.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "option_id")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    // Getters & Setters
    public Long getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
}
