package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.CourseDAO;
import com.mycompany.elearning.dao.SectionDAO;
import com.mycompany.elearning.dao.EnrollmentDAO;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Section;
import java.util.List;

/**
 * Service pour gérer les cours
 */
public class CourseService {
    
    private CourseDAO courseDAO = new CourseDAO();
    private SectionDAO sectionDAO = new SectionDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    
    public Course createCourse(Course course) {
        return courseDAO.save(course);
    }
    
    public Course updateCourse(Course course) {
        return courseDAO.update(course);
    }
    
    public void deleteCourse(Long courseId) {
        Course course = courseDAO.findById(courseId);
        if (course != null) {
            courseDAO.delete(course);
        }
    }
    
    public Course getCourseById(Long courseId) {
        return courseDAO.findById(courseId);
    }
    
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }
    
    public List<Course> getPublishedCourses() {
        return courseDAO.findPublishedCourses();
    }
    
    public List<Course> getCoursesByLevel(String level) {
        return courseDAO.findByLevel(level);
    }
    
    public List<Course> searchCourses(String keyword) {
        return courseDAO.searchByTitle(keyword);
    }
    
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseDAO.findByTeacherId(teacherId);
    }
    
    public void publishCourse(Long courseId) {
        Course course = courseDAO.findById(courseId);
        if (course != null) {
            course.setIsPublished(true);
            courseDAO.update(course);
        }
    }
    
    public void unpublishCourse(Long courseId) {
        Course course = courseDAO.findById(courseId);
        if (course != null) {
            course.setIsPublished(false);
            courseDAO.update(course);
        }
    }
    
    public List<Section> getCourseSections(Long courseId) {
        return sectionDAO.findByCourseId(courseId);
    }
    
    public long getCourseEnrollmentCount(Long courseId) {
        return enrollmentDAO.countByCourseId(courseId);
    }
}