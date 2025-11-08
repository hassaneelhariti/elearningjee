/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao.teacher;

/**
 *
 * @author ousam713
 */



import com.mycompany.elearning.entities.Contenu.Course;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * DAO pour la gestion des cours (Version simplifiée avec JPA basique)
 */
@ApplicationScoped
public class CourseDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    /**
     * Créer un nouveau cours
     */
    @Transactional
    public void save(Course course) {
        em.persist(course);
    }
    
    /**
     * Mettre à jour un cours existant
     */
    @Transactional
    public void update(Course course) {
        em.merge(course);
    }
    
    /**
     * Trouver un cours par son ID
     */
    public Course findById(Long id) {
        return em.find(Course.class, id);
    }
    
    /**
     * Supprimer un cours
     */
    @Transactional
    public void delete(Course course) {
        if (!em.contains(course)) {
            course = em.merge(course);
        }
        em.remove(course);
    }
    
    /**
     * Supprimer un cours par ID
     */
    @Transactional
    public void deleteById(Long id) {
        Course course = em.find(Course.class, id);
        if (course != null) {
            em.remove(course);
        }
    }
}