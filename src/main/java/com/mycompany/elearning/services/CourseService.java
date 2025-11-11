package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.dao.TeacherDao;
import com.mycompany.elearning.dto.CourseDto;
import com.mycompany.elearning.entities.Contenu.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
    private CourseDao courseDao;
    private TeacherDao teacherDao;

    public  CourseService(){}

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
                            course.getId(), // BIEN REMPLIR L'ID ICI
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

        String teacherName=teacherDao.findTeacherById(course.getId());
        courseDto.setTeacher(teacherName);
        return courseDto;

    }

    public Course findCourseByName(String courseName) {
        courseName = courseName.toLowerCase();
        Course course = courseDao.findCourseByName(courseName);
        return course;

    }
}
