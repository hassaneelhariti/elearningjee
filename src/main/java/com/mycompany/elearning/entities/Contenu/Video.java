/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Contenu;


import jakarta.persistence.*;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String url; // URL YouTube, Vimeo, etc.
    
    private Integer duration; // en secondes
    
    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    
    // Constructeurs
    public Video() {
    }
    
    public Video(String url, Integer duration, Lesson lesson) {
        this.url = url;
        this.duration = duration;
        this.lesson = lesson;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Lesson getLesson() {
        return lesson;
    }
    
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}