/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Utilisateurs;


import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Interactions.Message;
import com.mycompany.elearning.entities.Statistics.TeacherStatistics;
import jakarta.persistence.*;

import java.util.List;
 
/**
 *
 * @author ousam713
 */

@Entity
@Table(name = "teacher")
public class Teacher extends User {
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    @ElementCollection
    @CollectionTable(name = "teacher_expertise", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "expertise")
    private List<String> expertise;
    
    private Float rating;
    
    private Integer totalStudents;
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages;
    
    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    private TeacherStatistics statistics;
    
    // Constructeurs
    public Teacher() {
        super();
        this.totalStudents = 0;
        this.rating = 0.0f;
    }
    
    public Teacher(String username, String email, String password, String firstName, String lastName, String bio) {
        super(username, email, password, firstName, lastName);
        this.bio = bio;
        this.totalStudents = 0;
        this.rating = 0.0f;
    }
    
    // Getters et Setters
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public List<String> getExpertise() {
        return expertise;
    }
    
    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }
    
    public Float getRating() {
        return rating;
    }
    
    public void setRating(Float rating) {
        this.rating = rating;
    }
    
    public Integer getTotalStudents() {
        return totalStudents;
    }
    
    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }
    
    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
    
    public TeacherStatistics getStatistics() {
        return statistics;
    }
    
    public void setStatistics(TeacherStatistics statistics) {
        this.statistics = statistics;
    }
}
