/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Statistics;


import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "student_statistics")
public class StudentStatistics extends Statistics {
    
    private Integer coursesCompleted;
    
    private Integer coursesInProgress;
    
    private Integer totalLearningTime;
    
    private Integer certificatesEarned;
    
    private Float averageQuizScore;
    
    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    // Constructeurs
    public StudentStatistics() {
        super();
        this.coursesCompleted = 0;
        this.coursesInProgress = 0;
        this.totalLearningTime = 0;
        this.certificatesEarned = 0;
        this.averageQuizScore = 0.0f;
    }
    
    public StudentStatistics(Student student) {
        super();
        this.student = student;
        this.coursesCompleted = 0;
        this.coursesInProgress = 0;
        this.totalLearningTime = 0;
        this.certificatesEarned = 0;
        this.averageQuizScore = 0.0f;
    }
    
    // Getters et Setters
    public Integer getCoursesCompleted() {
        return coursesCompleted;
    }
    
    public void setCoursesCompleted(Integer coursesCompleted) {
        this.coursesCompleted = coursesCompleted;
    }
    
    public Integer getCoursesInProgress() {
        return coursesInProgress;
    }
    
    public void setCoursesInProgress(Integer coursesInProgress) {
        this.coursesInProgress = coursesInProgress;
    }
    
    public Integer getTotalLearningTime() {
        return totalLearningTime;
    }
    
    public void setTotalLearningTime(Integer totalLearningTime) {
        this.totalLearningTime = totalLearningTime;
    }
    
    public Integer getCertificatesEarned() {
        return certificatesEarned;
    }
    
    public void setCertificatesEarned(Integer certificatesEarned) {
        this.certificatesEarned = certificatesEarned;
    }
    
    public Float getAverageQuizScore() {
        return averageQuizScore;
    }
    
    public void setAverageQuizScore(Float averageQuizScore) {
        this.averageQuizScore = averageQuizScore;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
}