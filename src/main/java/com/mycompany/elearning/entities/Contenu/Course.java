/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Contenu;



import com.mycompany.elearning.entities.CategoriesTags.Category;
import com.mycompany.elearning.entities.CategoriesTags.Tag;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Interactions.Review;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String level;
    
    private String language;
    
    private String thumbnail;
    
    private Integer duration;
    
    private Float rating;
    
    private Integer totalEnrollments;
    
    @Column(nullable = false)
    private LocalDateTime dateCreated;
    
    private LocalDateTime lastUpdated;
    
    @Column(nullable = false)
    private Boolean isPublished;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Section> sections;
    
    @ManyToMany
    @JoinTable(
        name = "course_category",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    
    @ManyToMany
    @JoinTable(
        name = "course_tag",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Review> reviews;
    
    // Constructeurs
    public Course() {
        this.dateCreated = LocalDateTime.now();
        this.isPublished = false;
        this.totalEnrollments = 0;
        this.rating = 0.0f;
    }
    
    public Course(String title, String description, String level, String language, Teacher teacher) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.language = language;
        this.teacher = teacher;
        this.dateCreated = LocalDateTime.now();
        this.isPublished = false;
        this.totalEnrollments = 0;
        this.rating = 0.0f;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getThumbnail() {
        return thumbnail;
    }
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Float getRating() {
        return rating;
    }
    
    public void setRating(Float rating) {
        this.rating = rating;
    }
    
    public Integer getTotalEnrollments() {
        return totalEnrollments;
    }
    
    public void setTotalEnrollments(Integer totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }
    
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public Boolean getIsPublished() {
        return isPublished;
    }
    
    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public List<Section> getSections() {
        return sections;
    }
    
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
    
    public List<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public List<Tag> getTags() {
        return tags;
    }
    
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
