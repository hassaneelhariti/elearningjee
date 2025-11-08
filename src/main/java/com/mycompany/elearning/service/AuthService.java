/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.elearning.service;


import com.mycompany.elearning.dao.UserDAO;
import com.mycompany.elearning.entities.Utilisateurs.User;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.Admin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Service d'authentification et gestion des utilisateurs
 * @author ousam713
 */
@ApplicationScoped
public class AuthService {
    
    @Inject
    private UserDAO userDAO;
    
    @PersistenceContext(unitName = "elearningPU")
    private EntityManager em;
    
    // ==================== INSCRIPTION ====================
    
    /**
     * Inscrire un nouveau Student
     */
    @Transactional
    public Student registerStudent(String username, String email, String password, 
                                    String firstName, String lastName) {
        Student student = new Student(username, email, password, firstName, lastName);
        em.persist(student);
        return student;
    }
    
    /**
     * Inscrire un nouveau Teacher
     */
    @Transactional
    public Teacher registerTeacher(String username, String email, String password, 
                                    String firstName, String lastName, String bio) {
        Teacher teacher = new Teacher(username, email, password, firstName, lastName, bio);
        em.persist(teacher);
        return teacher;
    }
    
    /**
     * Inscrire un nouveau Admin
     */
    @Transactional
    public Admin registerAdmin(String username, String email, String password, 
                               String firstName, String lastName) {
        Admin admin = new Admin(username, email, password, firstName, lastName);
        em.persist(admin);
        return admin;
    }
    
    // ==================== CONNEXION ====================
    
    /**
     * Connexion simple (sans cryptage du mot de passe)
     * Retourne l'utilisateur si les credentials sont corrects, sinon null
     */
    public User login(String username, String password) {
        try {
            // Chercher l'utilisateur par username et password
            User user = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", 
                User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
            
            return user;
        } catch (Exception e) {
            return null; // Utilisateur non trouvé ou erreur
        }
    }
    
    // ==================== VÉRIFICATIONS ====================
    
    /**
     * Vérifier si un username existe déjà
     */
    public boolean usernameExists(String username) {
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", 
                Long.class)
                .setParameter("username", username)
                .getSingleResult();
            
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Vérifier si un email existe déjà
     */
    public boolean emailExists(String email) {
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", 
                Long.class)
                .setParameter("email", email)
                .getSingleResult();
            
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    // ==================== GESTION UTILISATEURS ====================
    
    /**
     * Trouver un utilisateur par ID
     */
    public User findUserById(Long id) {
        return userDAO.findById(id);
    }
    
    /**
     * Mettre à jour un utilisateur
     */
    public void updateUser(User user) {
        userDAO.update(user);
    }
    
    /**
     * Supprimer un utilisateur
     */
    public void deleteUser(User user) {
        userDAO.delete(user);
    }
}