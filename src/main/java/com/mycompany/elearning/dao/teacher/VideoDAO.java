/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao.teacher;

import com.mycompany.elearning.entities.Contenu.Video;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author ousam713
 */


/**
 * DAO pour la gestion des vidéos
 */
@ApplicationScoped
public class VideoDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    @Transactional
    public void save(Video video) {
        em.persist(video);
    }
    
    @Transactional
    public void update(Video video) {
        em.merge(video);
    }
    
    public Video findById(Long id) {
        return em.find(Video.class, id);
    }
    
    @Transactional
    public void delete(Video video) {
        if (!em.contains(video)) {
            video = em.merge(video);
        }
        em.remove(video);
    }
}