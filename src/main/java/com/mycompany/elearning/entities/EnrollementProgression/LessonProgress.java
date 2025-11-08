/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.EnrollementProgression;


import com.mycompany.elearning.entities.Contenu.Lesson;
import jakarta.persistence.*;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "lesson_progress")
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Boolean isCompleted;
    
    private Integer lastPosition; // Position dans la vidéo en secondes
    
    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
    
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    
    // Constructeurs
    public LessonProgress() {
        this.isCompleted = false;
        this.lastPosition = 0;
    }
    
    public LessonProgress(Enrollment enrollment, Lesson lesson) {
        this.enrollment = enrollment;
        this.lesson = lesson;
        this.isCompleted = false;
        this.lastPosition = 0;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    public Integer getLastPosition() {
        return lastPosition;
    }
    
    public void setLastPosition(Integer lastPosition) {
        this.lastPosition = lastPosition;
    }
    
    public Enrollment getEnrollment() {
        return enrollment;
    }
    
    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }
    
    public Lesson getLesson() {
        return lesson;
    }
    
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}