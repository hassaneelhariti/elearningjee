/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.dao;

/**
 *
 * @author ousam713
 */


import com.mycompany.elearning.entities.Utilisateurs.User;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.Admin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * DAO pour la gestion des utilisateurs (Version simplifiée)
 */
@ApplicationScoped
public class UserDAO {
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    /**
     * Créer un nouvel utilisateur
     */
    @Transactional
    public void save(User user) {
        em.persist(user);
    }
    
    /**
     * Mettre à jour un utilisateur
     */
    @Transactional
    public void update(User user) {
        em.merge(user);
    }
    
    /**
     * Trouver un utilisateur par ID
     */
    public User findById(Long id) {
        return em.find(User.class, id);
    }
    
    /**
     * Trouver un Student par ID
     */
    public Student findStudentById(Long id) {
        return em.find(Student.class, id);
    }
    
    /**
     * Trouver un Teacher par ID
     */
    public Teacher findTeacherById(Long id) {
        return em.find(Teacher.class, id);
    }
    
    /**
     * Trouver un Admin par ID
     */
    public Admin findAdminById(Long id) {
        return em.find(Admin.class, id);
    }
    
    /**
     * Supprimer un utilisateur
     */
    @Transactional
    public void delete(User user) {
        if (!em.contains(user)) {
            user = em.merge(user);
        }
        em.remove(user);
    }
}