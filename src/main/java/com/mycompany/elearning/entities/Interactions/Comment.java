/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Interactions;


import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(nullable = false)
    private LocalDateTime datePosted;
    
    private LocalDateTime dateEdited;
    
    private Integer likes;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;
    
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;
    
    // Constructeurs
    public Comment() {
        this.datePosted = LocalDateTime.now();
        this.likes = 0;
    }
    
    public Comment(String content, Student student, Lesson lesson) {
        this.content = content;
        this.student = student;
        this.lesson = lesson;
        this.datePosted = LocalDateTime.now();
        this.likes = 0;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getDatePosted() {
        return datePosted;
    }
    
    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
    
    public LocalDateTime getDateEdited() {
        return dateEdited;
    }
    
    public void setDateEdited(LocalDateTime dateEdited) {
        this.dateEdited = dateEdited;
    }
    
    public Integer getLikes() {
        return likes;
    }
    
    public void setLikes(Integer likes) {
        this.likes = likes;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Lesson getLesson() {
        return lesson;
    }
    
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
    
    public Comment getParentComment() {
        return parentComment;
    }
    
    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }
    
    public List<Comment> getReplies() {
        return replies;
    }
    
    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
