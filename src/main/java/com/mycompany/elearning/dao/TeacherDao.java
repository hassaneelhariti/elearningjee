package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
@Transactional
public class TeacherDao {
    @PersistenceContext
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
}
