/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Interactions;


import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.*;

import java.time.LocalDateTime;
/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String subject;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(nullable = false)
    private LocalDateTime dateSent;
    
    @Column(nullable = false)
    private Boolean isRead;
    
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Student sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Teacher receiver;
    
    // Constructeurs
    public Message() {
        this.dateSent = LocalDateTime.now();
        this.isRead = false;
    }
    
    public Message(String subject, String content, Student sender, Teacher receiver) {
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.dateSent = LocalDateTime.now();
        this.isRead = false;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getDateSent() {
        return dateSent;
    }
    
    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }
    
    public Boolean getIsRead() {
        return isRead;
    }
    
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
    
    public Student getSender() {
        return sender;
    }
    
    public void setSender(Student sender) {
        this.sender = sender;
    }
    
    public Teacher getReceiver() {
        return receiver;
    }
    
    public void setReceiver(Teacher receiver) {
        this.receiver = receiver;
    }
}
