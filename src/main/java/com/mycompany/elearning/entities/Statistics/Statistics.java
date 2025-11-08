/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Statistics;


import jakarta.persistence.*;

import java.time.LocalDateTime;
/**
 *
 * @author ousam713
 */
@Entity
@Table(name = "statistics")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime dateGenerated;
    
    // Constructeurs
    public Statistics() {
        this.dateGenerated = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDateGenerated() {
        return dateGenerated;
    }
    
    public void setDateGenerated(LocalDateTime dateGenerated) {
        this.dateGenerated = dateGenerated;
    }
}
