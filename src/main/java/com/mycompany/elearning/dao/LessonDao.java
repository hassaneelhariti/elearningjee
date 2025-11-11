package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Lesson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class LessonDao {
    private EntityManager em;

    public LessonDao(EntityManager em) {
        this.em = em;
    }

    public Lesson findById(Long id) {
        return em.find(Lesson.class, id);
    }

    public List<Lesson> findBySectionId(Long sectionId) {
        TypedQuery<Lesson> query = em.createQuery(
                "SELECT l FROM Lesson l WHERE l.section.id = :sectionId ORDER BY l.orderIndex",
                Lesson.class
        );
        query.setParameter("sectionId", sectionId);
        return query.getResultList();
    }

    public Lesson save(Lesson lesson) {
        if (lesson.getId() == null) {
            em.persist(lesson);
            return lesson;
        } else {
            return em.merge(lesson);
        }
    }

    public void delete(Long id) {
        Lesson lesson = em.find(Lesson.class, id);
        if (lesson != null) {
            em.remove(lesson);
        }
    }
}