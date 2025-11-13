package com.mycompany.elearning.dto;

import java.time.LocalDateTime;

public class CourseDto {
    private long courseId;
    private String courseName;
    private LocalDateTime dateCreated;
    private String teacher;
    private String description;
    private String level;
    private String thumbnail;


    public CourseDto() {}

    public CourseDto(Long courseId, String courseName, String teacher, String description, String level) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.dateCreated = dateCreated;
        this.teacher = teacher;
        this.description = description;
        this.level = level;
        this.thumbnail = thumbnail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}