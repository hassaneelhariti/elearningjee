/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.service;

/**
 *
 * @author ousam713
 */


import dao.CourseDAO;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CourseService {
    
    @Inject
    private CourseDAO courseDAO;
    
    public Course createCourse(String title, String description, String level, Teacher teacher) {
        Course course = new Course(title, description, level, teacher);
        return courseDAO.save(course);
    }
    
    public Section addSectionToCourse(Course course, String title, Integer orderIndex) {
        Section section = new Section(title, orderIndex, course);
        course.getSections().add(section);
        courseDAO.save(course);
        return section;
    }
    
    public Course findCourseById(Long id) {
        Optional<Course> course = courseDAO.findById(id);
        return course.orElse(null);
    }
    
    public void updateCourse(Course course) {
        courseDAO.save(course);
    }
    
    public List<Course> getTeacherCourses(Teacher teacher) {
        return courseDAO.findByTeacher(teacher);
    }
}