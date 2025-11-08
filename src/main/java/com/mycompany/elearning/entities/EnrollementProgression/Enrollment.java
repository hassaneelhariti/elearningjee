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
    
    private Float progress; // 0.0 à 100.0
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<LessonProgress> lessonProgresses;
    
    // Constructeurs
    public Enrollment() {
        this.enrollmentDate = LocalDateTime.now();
        this.progress = 0.0f;
    }
    
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
        this.progress = 0.0f;
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
    
    public Float getProgress() {
        return progress;
    }
    
    public void setProgress(Float progress) {
        this.progress = progress;
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
}