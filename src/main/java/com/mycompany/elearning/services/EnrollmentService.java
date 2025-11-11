package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.dao.EnrollmentDao;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;

public class EnrollmentService {
    private EnrollmentDao enrollmentDao;
    private CourseDao courseDao; // Assurez-vous d'avoir cette propriété

    // Constructeur avec les deux DAO
    public EnrollmentService(EnrollmentDao enrollmentDao, CourseDao courseDao) {
        this.enrollmentDao = enrollmentDao;
        this.courseDao = courseDao;
    }

    // Constructeur par défaut (si nécessaire)
    public EnrollmentService() {}

    public boolean isStudentEnrolled(Long studentId, Long courseId) {
        return enrollmentDao.findByStudentAndCourse(studentId, courseId) != null;
    }

    public Enrollment enrollStudentInCourse(Student student, Long courseId) {
        try {
            // Vérifier que courseDao n'est pas null
            if (courseDao == null) {
                throw new RuntimeException("CourseDao n'est pas initialisé dans EnrollmentService");
            }

            // Récupérer le cours par son ID
            Course course = courseDao.findCourseById(courseId);

            if (course == null) {
                throw new RuntimeException("Cours non trouvé avec l'ID: " + courseId);
            }

            // Vérifier si l'étudiant est déjà inscrit
            if (isStudentEnrolled(student.getId(), courseId)) {
                throw new RuntimeException("Étudiant déjà inscrit à ce cours");
            }

            // Créer la nouvelle inscription
            Enrollment enrollment = new Enrollment(student, course);

            // Sauvegarder l'inscription
            return enrollmentDao.save(enrollment);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'inscription: " + e.getMessage(), e);
        }
    }

    // Setters pour l'injection de dépendances (au cas où)
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public void setEnrollmentDao(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }
}