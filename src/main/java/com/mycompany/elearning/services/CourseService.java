package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.dao.TeacherDao;
import com.mycompany.elearning.dto.CourseDto;
import com.mycompany.elearning.entities.Contenu.Course;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
    private CourseDao courseDao;
    private TeacherDao teacherDao;
    private EntityManager em;

    public CourseService() {}

    public CourseService(EntityManager em) {
        this.em = em;
        this.courseDao = new CourseDao(em);
        this.teacherDao = new TeacherDao(em);
    }

    public CourseService(CourseDao courseDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.teacherDao = teacherDao;
    }

    public List<Course> getDispoCourses() {
        return courseDao.getPublishedCourse();
    }

    public List<CourseDto> getAllCourses() {
        try {
            List<Course> courses = courseDao.findAll();
            return courses.stream()
                    .map(course -> new CourseDto(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            course.getTeacher() != null ? course.getTeacher().getUsername() : "Non assigné",
                            course.getLevel()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des cours", e);
        }
    }

    private CourseDto convertToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseId(course.getId());
        courseDto.setCourseName(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setDateCreated(course.getDateCreated());
        courseDto.setLevel(course.getLevel());
        courseDto.setThumbnail(course.getThumbnail());

        String teacherName = teacherDao.findTeacherById(course.getTeacher().getId());
        courseDto.setTeacher(teacherName);
        return courseDto;
    }

    public Course findCourseByName(String courseName) {
        courseName = courseName.toLowerCase();
        Course course = courseDao.findCourseByName(courseName);
        return course;
    }

    // Nouvelles méthodes pour le sous-système enseignant
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseDao.findByTeacher(teacherId);
    }

    public Course getCourseById(Long id) {
        return courseDao.findCourseById(id);
    }

    public Course createCourse(Course course) {
        System.out.println("DEBUG: Starting createCourse - Title: " + course.getTitle());
        System.out.println("DEBUG: Teacher: " + (course.getTeacher() != null ? course.getTeacher().getId() : "null"));
        System.out.println("DEBUG: DateCreated: " + course.getDateCreated());

        try {
            Course savedCourse = courseDao.save(course);
            System.out.println("DEBUG: Course saved successfully - ID: " + savedCourse.getId());
            return savedCourse;
        } catch (Exception e) {
            System.err.println("ERROR in createCourse: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Course updateCourse(Course course) {
        return courseDao.save(course);
    }

    public void deleteCourse(Long id) {
        courseDao.delete(id);
    }

    public boolean isCourseOwner(Long courseId, Long teacherId) {
        Course course = courseDao.findCourseById(courseId);
        return course != null && course.getTeacher().getId().equals(teacherId);
    }

    public Long getStudentCountByTeacher(Long teacherId) {
        return courseDao.countStudentsByTeacher(teacherId);
    }

    public Long getLessonCountByTeacher(Long teacherId) {
        return courseDao.countLessonsByTeacher(teacherId);
    }

}