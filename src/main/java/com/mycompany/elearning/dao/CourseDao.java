package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class CourseDao {
    private EntityManager em;

    public CourseDao(EntityManager em) {
        this.em = em;
    }

    public List<Course> getPublishedCourse(){
        return em.createQuery("SELECT c FROM Course c WHERE c.isPublished = true", Course.class).getResultList();
    }

    public Course findCourseById(Long id){
        return em.find(Course.class, id);
    }

    public Course findCourseByName(String courseName){
        try {
            TypedQuery<Course> query = em.createQuery(
                    "SELECT c FROM Course c WHERE c.title = :courseName",
                    Course.class
            );
            query.setParameter("courseName", courseName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Course> findAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    // Dans CourseDao.java
    public Course save(Course course) {
        EntityTransaction transaction = em.getTransaction();
        boolean weStartedTransaction = false;

        try {
            // Vérifiez si une transaction est active
            if (!transaction.isActive()) {
                transaction.begin();
                weStartedTransaction = true;
                System.out.println("DEBUG: Transaction started");
            }

            System.out.println("DEBUG: Before save - Course ID: " + course.getId() + ", Title: " + course.getTitle());

            if (course.getId() == null) {
                System.out.println("DEBUG: Persisting new course");
                em.persist(course); // Pour les nouvelles entités
                em.flush(); // Force l'INSERT immédiat
                System.out.println("DEBUG: After persist - Course ID: " + course.getId());
            } else {
                System.out.println("DEBUG: Merging existing course");
                course = em.merge(course); // Pour les entités existantes
                em.flush();
                System.out.println("DEBUG: After merge - Course ID: " + course.getId());
            }

            // Commit seulement si nous avons démarré la transaction
            if (weStartedTransaction) {
                transaction.commit();
                System.out.println("DEBUG: Transaction committed");
            }

            return course;

        } catch (Exception e) {
            System.err.println("ERROR in CourseDao.save: " + e.getMessage());
            e.printStackTrace();

            // Rollback seulement si nous avons démarré la transaction
            if (weStartedTransaction && transaction.isActive()) {
                transaction.rollback();
                System.err.println("DEBUG: Transaction rolled back");
            }
            throw new RuntimeException("Failed to save course", e);
        }
    }

    // Nouvelles méthodes pour le sous-système enseignant
    public List<Course> findByTeacher(Long teacherId) {
        TypedQuery<Course> query = em.createQuery(
                "SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.enrollments WHERE c.teacher.id = :teacherId ORDER BY c.dateCreated DESC",
                Course.class
        );
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    public void delete(Long id) {
        Course course = em.find(Course.class, id);
        if (course != null) {
            em.remove(course);
        }
    }

    public Long countStudentsByTeacher(Long teacherId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(DISTINCT e.student.id) FROM Enrollment e " +
                        "WHERE e.course.teacher.id = :teacherId", Long.class
        );
        query.setParameter("teacherId", teacherId);
        return query.getSingleResult();
    }

    public Long countLessonsByTeacher(Long teacherId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(l) FROM Lesson l " +
                        "JOIN l.section s " +
                        "JOIN s.course c " +
                        "WHERE c.teacher.id = :teacherId", Long.class
        );
        query.setParameter("teacherId", teacherId);
        return query.getSingleResult();
    }
}