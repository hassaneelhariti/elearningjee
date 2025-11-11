package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EnrollmentDao {
    private EntityManager em;

    public EnrollmentDao(EntityManager em) {
        this.em = em;
    }

    public Enrollment save(Enrollment enrollment) {
        try {
            em.getTransaction().begin();
            if (enrollment.getId() == null) {
                em.persist(enrollment);
            } else {
                enrollment = em.merge(enrollment);
            }
            em.getTransaction().commit();
            return enrollment;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde de l'inscription", e);
        }
    }

    public Enrollment findByStudentAndCourse(Long studentId, Long courseId) {
        try {
            TypedQuery<Enrollment> query = em.createQuery(
                    "SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId",
                    Enrollment.class
            );
            query.setParameter("studentId", studentId);
            query.setParameter("courseId", courseId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Enrollment> findByStudentId(Long studentId) {
        TypedQuery<Enrollment> query = em.createQuery(
                "SELECT e FROM Enrollment e WHERE e.student.id = :studentId",
                Enrollment.class
        );
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }
}