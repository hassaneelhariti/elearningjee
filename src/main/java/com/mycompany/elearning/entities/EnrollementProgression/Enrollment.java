/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.EnrollementProgression;


import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime enrollmentDate;
    
    private LocalDateTime completionDate;
    
    private Float progress;
    
    private LocalDateTime lastAccessedDate;
    
    @Column(nullable = false)
    private Boolean certificateIssued;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<LessonProgress> lessonProgresses;
    
    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private Certificate certificate;
    
    // Constructeurs
    public Enrollment() {
        this.enrollmentDate = LocalDateTime.now();
        this.progress = 0.0f;
        this.certificateIssued = false;
    }
    
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
        this.progress = 0.0f;
        this.certificateIssued = false;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
    
    public Float getProgress() {
        return progress;
    }
    
    public void setProgress(Float progress) {
        this.progress = progress;
    }
    
    public LocalDateTime getLastAccessedDate() {
        return lastAccessedDate;
    }
    
    public void setLastAccessedDate(LocalDateTime lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }
    
    public Boolean getCertificateIssued() {
        return certificateIssued;
    }
    
    public void setCertificateIssued(Boolean certificateIssued) {
        this.certificateIssued = certificateIssued;
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
    
    public List<LessonProgress> getLessonProgresses() {
        return lessonProgresses;
    }
    
    public void setLessonProgresses(List<LessonProgress> lessonProgresses) {
        this.lessonProgresses = lessonProgresses;
    }
    
    public Certificate getCertificate() {
        return certificate;
    }
    
    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
}
