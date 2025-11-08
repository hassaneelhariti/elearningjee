/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Contenu;


import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.entities.Interactions.Comment;
import com.mycompany.elearning.entities.QuizTests.Quiz;
import jakarta.persistence.*;

import java.util.List;


/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private Integer orderIndex;
    
    private Integer duration;
    
    @Column(nullable = false)
    private Boolean isFree;
    
    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Video video;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private PDF pdf;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Quiz quiz;
    
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<LessonProgress> progresses;
    
    // Constructeurs
    public Lesson() {
        this.isFree = false;
    }
    
    public Lesson(String title, Integer orderIndex, Section section) {
        this.title = title;
        this.orderIndex = orderIndex;
        this.section = section;
        this.isFree = false;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getOrderIndex() {
        return orderIndex;
    }
    
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Boolean getIsFree() {
        return isFree;
    }
    
    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }
    
    public Section getSection() {
        return section;
    }
    
    public void setSection(Section section) {
        this.section = section;
    }
    
    public Video getVideo() {
        return video;
    }
    
    public void setVideo(Video video) {
        this.video = video;
    }
    
    public PDF getPdf() {
        return pdf;
    }
    
    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }
    
    public Quiz getQuiz() {
        return quiz;
    }
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    
    public List<Comment> getComments() {
        return comments;
    }
    
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public List<LessonProgress> getProgresses() {
        return progresses;
    }
    
    public void setProgresses(List<LessonProgress> progresses) {
        this.progresses = progresses;
    }
}
