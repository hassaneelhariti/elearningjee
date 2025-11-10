package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.dao.SectionDao;
import com.mycompany.elearning.dao.EnrollmentDao;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Section;
import java.util.List;

public class CourseService {
    
    private CourseDao courseDao;
    private SectionDao sectionDao;
    private EnrollmentDao enrollmentDao;
    
    public CourseService() {
        this.courseDao = new CourseDao();
        this.sectionDao = new SectionDao();
        this.enrollmentDao = new EnrollmentDao();
    }
    
    public Course createCourse(Course course) {
        return courseDao.save(course);
    }
    
    public Course updateCourse(Course course) {
        return courseDao.update(course);
    }
    
    public void deleteCourse(Long courseId) {
        Course course = courseDao.findById(courseId);
        if (course != null) {
            courseDao.delete(course);
        }
    }
    
    public Course getCourseById(Long id) {
        return courseDao.findById(id);
    }
    
    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }
    
    public List<Course> getPublishedCourses() {
        return courseDao.findPublishedCourses();
    }
    
    public List<Course> getCoursesByLevel(String level) {
        return courseDao.findByLevel(level);
    }
    
    public List<Course> searchCourses(String keyword) {
        return courseDao.searchByTitle(keyword);
    }
    
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseDao.findByTeacherId(teacherId);
    }
    
    public void publishCourse(Long courseId) {
        Course course = courseDao.findById(courseId);
        if (course != null) {
            course.setIsPublished(true);
            courseDao.update(course);
        }
    }
    
    public void unpublishCourse(Long courseId) {
        Course course = courseDao.findById(courseId);
        if (course != null) {
            course.setIsPublished(false);
            courseDao.update(course);
        }
    }
    
    public List<Section> getCourseSections(Long courseId) {
        return sectionDao.findByCourseId(courseId);
    }
    
    public long getEnrollmentCount(Long courseId) {
        return enrollmentDao.countByCourseId(courseId);
    }
}