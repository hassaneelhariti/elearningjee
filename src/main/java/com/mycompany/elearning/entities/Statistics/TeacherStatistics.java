/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Statistics;


import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "teacher_statistics")
public class TeacherStatistics extends Statistics {
    
    private Integer totalStudents;
    
    private Float averageRating;
    
    private Integer courseViews;
    
    private Float completionRate;
    
    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    // Constructeurs
    public TeacherStatistics() {
        super();
        this.totalStudents = 0;
        this.averageRating = 0.0f;
        this.courseViews = 0;
        this.completionRate = 0.0f;
    }
    
    public TeacherStatistics(Teacher teacher) {
        super();
        this.teacher = teacher;
        this.totalStudents = 0;
        this.averageRating = 0.0f;
        this.courseViews = 0;
        this.completionRate = 0.0f;
    }
    
    // Getters et Setters
    public Integer getTotalStudents() {
        return totalStudents;
    }
    
    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }
    
    public Float getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }
    
    public Integer getCourseViews() {
        return courseViews;
    }
    
    public void setCourseViews(Integer courseViews) {
        this.courseViews = courseViews;
    }
    
    public Float getCompletionRate() {
        return completionRate;
    }
    
    public void setCompletionRate(Float completionRate) {
        this.completionRate = completionRate;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}