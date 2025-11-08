/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao.student;

import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author ousam713
 */



/**
 * DAO pour la gestion de la progression des leçons
 */
@ApplicationScoped
class LessonProgressDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    /**
     * Créer une nouvelle progression
     */
    @Transactional
    public void save(LessonProgress progress) {
        em.persist(progress);
    }
    
    /**
     * Mettre à jour une progression
     */
    @Transactional
    public void update(LessonProgress progress) {
        em.merge(progress);
    }
    
    /**
     * Trouver une progression par ID
     */
    public LessonProgress findById(Long id) {
        return em.find(LessonProgress.class, id);
    }
    
    /**
     * Supprimer une progression
     */
    @Transactional
    public void delete(LessonProgress progress) {
        if (!em.contains(progress)) {
            progress = em.merge(progress);
        }
        em.remove(progress);
    }
}