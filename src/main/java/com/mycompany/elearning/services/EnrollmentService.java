package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.EnrollmentDAO;
import com.mycompany.elearning.dao.LessonProgressDAO;
import com.mycompany.elearning.dao.CourseDAO;
import com.mycompany.elearning.dao.StudentDAO;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import java.util.List;

/**
 * Service pour gérer les inscriptions
 */
public class EnrollmentService {
    
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private LessonProgressDAO lessonProgressDAO = new LessonProgressDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private StudentDAO studentDAO = new StudentDAO();
    
    public Enrollment enrollStudent(Long studentId, Long courseId) {
        Student student = studentDAO.findById(studentId);
        Course course = courseDAO.findById(courseId);
        
        if (student != null && course != null) {
            // Vérifier si déjà inscrit
            Enrollment existing = enrollmentDAO.findByStudentAndCourse(studentId, courseId);
            if (existing == null) {
                Enrollment enrollment = new Enrollment(student, course);
                return enrollmentDAO.save(enrollment);
            }
            return existing;
        }
        return null;
    }
    
    public List<Enrollment> getStudentEnrollments(Long studentId) {
        return enrollmentDAO.findByStudentId(studentId);
    }
    
    public List<Enrollment> getCourseEnrollments(Long courseId) {
        return enrollmentDAO.findByCourseId(courseId);
    }
    
    public Enrollment getEnrollment(Long enrollmentId) {
        return enrollmentDAO.findById(enrollmentId);
    }
    
    public void updateProgress(Long enrollmentId, Long lessonId, boolean completed, Integer position) {
        LessonProgress progress = lessonProgressDAO.findByEnrollmentAndLesson(enrollmentId, lessonId);
        
        if (progress == null) {
            progress = new LessonProgress();
            progress.setEnrollment(enrollmentDAO.findById(enrollmentId));
            progress.setLesson(new com.mycompany.elearning.dao.LessonDAO().findById(lessonId));
            progress.setIsCompleted(completed);
            progress.setLastPosition(position);
            lessonProgressDAO.save(progress);
        } else {
            progress.setIsCompleted(completed);
            progress.setLastPosition(position);
            lessonProgressDAO.update(progress);
        }
        
        // Calculer et mettre à jour la progression globale
        updateEnrollmentProgress(enrollmentId);
    }
    
    private void updateEnrollmentProgress(Long enrollmentId) {
        Enrollment enrollment = enrollmentDAO.findById(enrollmentId);
        if (enrollment != null) {
            List<LessonProgress> allProgress = lessonProgressDAO.findByEnrollmentId(enrollmentId);
            long completedCount = lessonProgressDAO.countCompletedLessons(enrollmentId);
            
            if (!allProgress.isEmpty()) {
                float progressPercent = (completedCount * 100.0f) / allProgress.size();
                enrollment.setProgress(progressPercent);
                enrollmentDAO.update(enrollment);
            }
        }
    }
    
    public LessonProgress getLessonProgress(Long enrollmentId, Long lessonId) {
        return lessonProgressDAO.findByEnrollmentAndLesson(enrollmentId, lessonId);
    }
}