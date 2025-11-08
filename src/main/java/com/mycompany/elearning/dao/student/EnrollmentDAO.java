/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao.student;

import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author ousam713
 */


/**
 * DAO pour la gestion des inscriptions (Version simplifiée)
 */
@ApplicationScoped
class EnrollmentDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    /**
     * Créer une nouvelle inscription
     */
    @Transactional
    public void save(Enrollment enrollment) {
        em.persist(enrollment);
    }
    
    /**
     * Mettre à jour une inscription
     */
    @Transactional
    public void update(Enrollment enrollment) {
        em.merge(enrollment);
    }
    
    /**
     * Trouver une inscription par ID
     */
    public Enrollment findById(Long id) {
        return em.find(Enrollment.class, id);
    }
    
    /**
     * Supprimer une inscription
     */
    @Transactional
    public void delete(Enrollment enrollment) {
        if (!em.contains(enrollment)) {
            enrollment = em.merge(enrollment);
        }
        em.remove(enrollment);
    }
}