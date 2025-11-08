/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.QuizTests;


import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "quiz_attempt")
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer score;
    
    @Column(nullable = false)
    private Boolean isPassed;
    
    @Column(nullable = false)
    private Integer attemptNumber;
    
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    @Column(columnDefinition = "TEXT")
    private String answers;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
    
    // Constructeurs
    public QuizAttempt() {
        this.startTime = LocalDateTime.now();
        this.isPassed = false;
    }
    
    public QuizAttempt(Integer attemptNumber, Student student, Quiz quiz) {
        this.attemptNumber = attemptNumber;
        this.student = student;
        this.quiz = quiz;
        this.startTime = LocalDateTime.now();
        this.isPassed = false;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public Boolean getIsPassed() {
        return isPassed;
    }
    
    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
    
    public Integer getAttemptNumber() {
        return attemptNumber;
    }
    
    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getAnswers() {
        return answers;
    }
    
    public void setAnswers(String answers) {
        this.answers = answers;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Quiz getQuiz() {
        return quiz;
    }
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
