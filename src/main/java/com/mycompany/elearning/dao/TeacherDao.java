package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class TeacherDao {
    private EntityManager em;

    public TeacherDao(EntityManager em) {
        this.em = em;
    }

    public String findTeacherById(Long id) {
        List<String> results = em.createQuery("SELECT t.username FROM Teacher t WHERE t.id = :id", String.class)
                .setParameter("id", id)
                .getResultList();

        return results.isEmpty() ? "Professeur inconnu" : results.get(0);
    }

    // Nouvelles méthodes pour le sous-système enseignant
    public Teacher findTeacherEntityById(Long id) {
        return em.find(Teacher.class, id);
    }

    public Teacher findByEmail(String email) {
        try {
            TypedQuery<Teacher> query = em.createQuery(
                    "SELECT t FROM Teacher t WHERE t.email = :email",
                    Teacher.class
            );
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Teacher> findAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }
}