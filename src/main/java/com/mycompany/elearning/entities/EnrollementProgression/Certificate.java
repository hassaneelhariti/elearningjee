/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.EnrollementProgression;


import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String certificateNumber;
    
    @Column(nullable = false)
    private LocalDateTime issueDate;
    
    @Column(nullable = false, unique = true)
    private String verificationCode;
    
    private String pdfUrl;
    
    @OneToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
    
    // Constructeurs
    public Certificate() {
        this.issueDate = LocalDateTime.now();
    }
    
    public Certificate(String certificateNumber, String verificationCode, Enrollment enrollment) {
        this.certificateNumber = certificateNumber;
        this.verificationCode = verificationCode;
        this.enrollment = enrollment;
        this.issueDate = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCertificateNumber() {
        return certificateNumber;
    }
    
    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
    
    public LocalDateTime getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    
    public String getVerificationCode() {
        return verificationCode;
    }
    
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    
    public String getPdfUrl() {
        return pdfUrl;
    }
    
    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
    
    public Enrollment getEnrollment() {
        return enrollment;
    }
    
    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }
}