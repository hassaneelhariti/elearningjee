/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao.teacher;

/**
 *
 * @author ousam713
 */


import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.Contenu.Video;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * DAO pour la gestion des sections
 */
@ApplicationScoped
public class SectionDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    @Transactional
    public void save(Section section) {
        em.persist(section);
    }
    
    @Transactional
    public void update(Section section) {
        em.merge(section);
    }
    
    public Section findById(Long id) {
        return em.find(Section.class, id);
    }
    
    @Transactional
    public void delete(Section section) {
        if (!em.contains(section)) {
            section = em.merge(section);
        }
        em.remove(section);
    }
}