/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Utilisateurs;


import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author ousam713
 */

@Entity
@Table(name = "student")
public class Student extends User {
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
    
    // Constructeurs
    public Student() {
        super();
    }
    
    public Student(String username, String email, String password, String firstName, String lastName) {
        super(username, email, password, firstName, lastName);
    }
    
    // Getters et Setters
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}