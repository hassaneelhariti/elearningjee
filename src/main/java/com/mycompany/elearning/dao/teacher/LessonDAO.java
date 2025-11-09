/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao.teacher;

import com.mycompany.elearning.entities.Contenu.Lesson;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author ousam713
 */


/**
 * DAO pour la gestion des leçons
 */
@ApplicationScoped
public class LessonDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    @Transactional
    public void save(Lesson lesson) {
        em.persist(lesson);
    }
    
    @Transactional
    public void update(Lesson lesson) {
        em.merge(lesson);
    }
    
    public Lesson findById(Long id) {
        return em.find(Lesson.class, id);
    }
    
    @Transactional
    public void delete(Lesson lesson) {
        if (!em.contains(lesson)) {
            lesson = em.merge(lesson);
        }
        em.remove(lesson);
    }
}