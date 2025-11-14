/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Contenu;


import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
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
    
    @Column(name = "content_type")
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Video video;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private PDF pdf;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<LessonProgress> progresses;
    
    // Constructeurs
    public Lesson() {
    }
    
    public Lesson(String title, Integer orderIndex, Section section) {
        this.title = title;
        this.orderIndex = orderIndex;
        this.section = section;
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
        if (video != null && video.getLesson() != this) {
            video.setLesson(this);
        }
        this.video = video;
    }
    
    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        if (pdf != null && pdf.getLesson() != this) {
            pdf.setLesson(this);
        }
        this.pdf = pdf;
    }

    public List<LessonProgress> getProgresses() {
        return progresses;
    }
    
    public void setProgresses(List<LessonProgress> progresses) {
        this.progresses = progresses;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}