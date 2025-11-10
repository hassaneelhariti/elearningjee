package com.mycompany.elearning.dao;

import jakarta.persistence.*;

public class EntityManagerUtil {
    
    private static final String PERSISTENCE_UNIT_NAME = "elearningPU";
    private static EntityManagerFactory emf;
    
    public static EntityManager getEntityManager() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                System.out.println("✅ EntityManagerFactory created successfully");
            } catch (Exception e) {
                System.out.println("❌ Error creating EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return emf.createEntityManager();
    }
    
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}