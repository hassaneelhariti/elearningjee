package com.mycompany.elearning.util;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.*;

public class HibernateTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // 1. Créer EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("my_persistence_unit");
            
            // 2. Créer EntityManager
            em = emf.createEntityManager();
            
            // 3. Démarrer transaction
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            
            // 4. Créer et sauvegarder un Teacher
            Teacher teacher = new Teacher(
                "prof_amine", 
                "amine@elearning.com", 
                "password123", 
                "Amine", 
                "Alami", 
                "Expert en développement Java"
            );
            
            em.persist(teacher);
            
            // 5. Créer un Course associé
            Course course = new Course(
                "Java Programming",
                "Apprendre Java de A à Z",
                "Beginner",
                teacher
            );
            
            em.persist(course);
            
            // 6. Commit transaction
            tx.commit();
            
            System.out.println("✅ Teacher sauvegardé avec ID: " + teacher.getId());
            System.out.println("✅ Course sauvegardé avec ID: " + course.getId());
            
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // 7. Fermer les ressources
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}