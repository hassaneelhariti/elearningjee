package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Section;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class SectionDao {
    private EntityManager em;

    public SectionDao(EntityManager em) {
        this.em = em;
    }

    public Section findById(Long id) {
        return em.find(Section.class, id);
    }

    public List<Section> findByCourseId(Long courseId) {
        TypedQuery<Section> query = em.createQuery(
                "SELECT s FROM Section s WHERE s.course.id = :courseId ORDER BY s.orderIndex",
                Section.class
        );
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    public Section save(Section section) {
        EntityTransaction transaction = em.getTransaction();
        boolean startedByDao = false;

        try {
            if (!transaction.isActive()) {
                transaction.begin();
                startedByDao = true;
            }

            if (section.getId() == null) {
                em.persist(section);
            } else {
                section = em.merge(section);
            }

            em.flush();

            if (startedByDao) {
                transaction.commit();
            }

            return section;
        } catch (Exception e) {
            if (startedByDao && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = em.getTransaction();
        boolean startedByDao = false;

        try {
            if (!transaction.isActive()) {
                transaction.begin();
                startedByDao = true;
            }

            Section section = em.find(Section.class, id);
            if (section != null) {
                em.remove(section);
            }

            if (startedByDao) {
                transaction.commit();
            }
        } catch (Exception e) {
            if (startedByDao && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}