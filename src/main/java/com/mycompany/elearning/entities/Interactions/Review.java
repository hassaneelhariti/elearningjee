/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Interactions;


import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer rating;
    
    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @Column(nullable = false)
    private LocalDateTime datePosted;
    
    @Column(nullable = false)
    private Boolean isVerifiedPurchase;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    // Constructeurs
    public Review() {
        this.datePosted = LocalDateTime.now();
        this.isVerifiedPurchase = false;
    }
    
    public Review(Integer rating, String comment, Student student, Course course) {
        this.rating = rating;
        this.comment = comment;
        this.student = student;
        this.course = course;
        this.datePosted = LocalDateTime.now();
        this.isVerifiedPurchase = false;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public LocalDateTime getDatePosted() {
        return datePosted;
    }
    
    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
    
    public Boolean getIsVerifiedPurchase() {
        return isVerifiedPurchase;
    }
    
    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) {
        this.isVerifiedPurchase = isVerifiedPurchase;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
}