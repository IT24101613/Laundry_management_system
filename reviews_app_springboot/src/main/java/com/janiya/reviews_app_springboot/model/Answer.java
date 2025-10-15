package com.janiya.reviews_app_springboot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String answerText;
    private String authorName;
    private String authorRole; // "customer" or "admin"
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    
    public Answer() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Answer(String answerText, String authorName, String authorRole, Question question) {
        this();
        this.answerText = answerText;
        this.authorName = authorName;
        this.authorRole = authorRole;
        this.question = question;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAnswerText() {
        return answerText;
    }
    
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorRole() {
        return authorRole;
    }
    
    public void setAuthorRole(String authorRole) {
        this.authorRole = authorRole;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
}
