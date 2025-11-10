package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.EnrollmentDao;
import com.mycompany.elearning.dao.LessonProgressDao;
import com.mycompany.elearning.dao.StudentDao;
import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Contenu.Course;
import java.util.List;

public class EnrollmentService {
    
    private EnrollmentDao enrollmentDao;
    private LessonProgressDao lessonProgressDao;
    private StudentDao studentDao;
    private CourseDao courseDao;
    
    public EnrollmentService() {
        this.enrollmentDao = new EnrollmentDao();
        this.lessonProgressDao = new LessonProgressDao();
        this.studentDao = new StudentDao();
        this.courseDao = new CourseDao();
    }
    
    public Enrollment enrollStudent(Long studentId, Long courseId) {
        Enrollment existing = enrollmentDao.findByStudentAndCourse(studentId, courseId);
        if (existing != null) {
            return existing;
        }
        
        Student student = studentDao.findById(studentId);
        Course course = courseDao.findById(courseId);
        
        if (student == null || course == null) {
            return null;
        }
        
        Enrollment enrollment = new Enrollment(student, course);
        return enrollmentDao.save(enrollment);
    }
    
    public List<Enrollment> getStudentEnrollments(Long studentId) {
        return studentDao.getStudentEnrollments(studentId);
    }
    
    public Enrollment getEnrollment(Long enrollmentId) {
        return enrollmentDao.findById(enrollmentId);
    }
    
    public void updateProgress(Long enrollmentId, Float progress) {
        Enrollment enrollment = enrollmentDao.findById(enrollmentId);
        if (enrollment != null) {
            enrollment.setProgress(progress);
            enrollmentDao.update(enrollment);
        }
    }
    
    public LessonProgress markLessonComplete(Long enrollmentId, Long lessonId) {
        LessonProgress progress = lessonProgressDao.findByEnrollmentAndLesson(enrollmentId, lessonId);
        if (progress == null) {
            return null;
        }
        progress.setIsCompleted(true);
        return lessonProgressDao.update(progress);
    }
    
    public LessonProgress updateLessonPosition(Long enrollmentId, Long lessonId, Integer position) {
        LessonProgress progress = lessonProgressDao.findByEnrollmentAndLesson(enrollmentId, lessonId);
        if (progress == null) {
            return null;
        }
        progress.setLastPosition(position);
        return lessonProgressDao.update(progress);
    }
}