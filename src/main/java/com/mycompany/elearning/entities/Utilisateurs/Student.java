/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Utilisateurs;


import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Interactions.Comment;
import com.mycompany.elearning.entities.Interactions.Message;
import com.mycompany.elearning.entities.Interactions.Review;
import com.mycompany.elearning.entities.QuizTests.QuizAttempt;
import com.mycompany.elearning.entities.Statistics.StudentStatistics;
import jakarta.persistence.*;

import java.util.List;

/**
 *
 * @author ousam713
 */

@Entity
@Table(name = "student")
public class Student extends User {
    
    private Integer enrolledCoursesCount;
    
    private Integer completedCoursesCount;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<QuizAttempt> quizAttempts;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Review> reviews;
    
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentStatistics statistics;
    
    // Constructeurs
    public Student() {
        super();
        this.enrolledCoursesCount = 0;
        this.completedCoursesCount = 0;
    }
    
    public Student(String username, String email, String password, String firstName, String lastName) {
        super(username, email, password, firstName, lastName);
        this.enrolledCoursesCount = 0;
        this.completedCoursesCount = 0;
    }
    
    // Getters et Setters
    public Integer getEnrolledCoursesCount() {
        return enrolledCoursesCount;
    }
    
    public void setEnrolledCoursesCount(Integer enrolledCoursesCount) {
        this.enrolledCoursesCount = enrolledCoursesCount;
    }
    
    public Integer getCompletedCoursesCount() {
        return completedCoursesCount;
    }
    
    public void setCompletedCoursesCount(Integer completedCoursesCount) {
        this.completedCoursesCount = completedCoursesCount;
    }
    
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    
    public List<QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }
    
    public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }
    
    public List<Comment> getComments() {
        return comments;
    }
    
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public List<Message> getSentMessages() {
        return sentMessages;
    }
    
    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    public StudentStatistics getStatistics() {
        return statistics;
    }
    
    public void setStatistics(StudentStatistics statistics) {
        this.statistics = statistics;
    }
}