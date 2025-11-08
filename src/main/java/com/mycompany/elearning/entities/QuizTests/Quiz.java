/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.QuizTests;

import com.mycompany.elearning.entities.Contenu.Lesson;
import jakarta.persistence.*;

import java.util.List;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    private Integer passingScore;
    
    private Integer timeLimit;
    
    private Integer maxAttempts;
    
    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;
    
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAttempt> attempts;
    
    // Constructeurs
    public Quiz() {
    }
    
    public Quiz(String title, Integer passingScore, Lesson lesson) {
        this.title = title;
        this.passingScore = passingScore;
        this.lesson = lesson;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getPassingScore() {
        return passingScore;
    }
    
    public void setPassingScore(Integer passingScore) {
        this.passingScore = passingScore;
    }
    
    public Integer getTimeLimit() {
        return timeLimit;
    }
    
    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public Integer getMaxAttempts() {
        return maxAttempts;
    }
    
    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
    
    public Lesson getLesson() {
        return lesson;
    }
    
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    public List<QuizAttempt> getAttempts() {
        return attempts;
    }
    
    public void setAttempts(List<QuizAttempt> attempts) {
        this.attempts = attempts;
    }
}
