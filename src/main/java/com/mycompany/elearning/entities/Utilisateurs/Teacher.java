/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Utilisateurs;


import com.mycompany.elearning.entities.Contenu.Course;
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
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;
    
    // Constructeurs
    public Teacher() {
        super();
    }
    
    public Teacher(String username, String email, String password, String firstName, String lastName, String bio) {
        super(username, email, password, firstName, lastName);
        this.bio = bio;
    }
    
    // Getters et Setters
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}