package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
    public static void main(String[] args) {
        SessionFactory sessionFactory = null;
        Session session = null;

        try {
            System.out.println("Configuration Hibernate...");
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
            session = sessionFactory.openSession();

            System.out.println("✅ Connexion Hibernate réussie!");

            // Test transaction
            session.beginTransaction();

            User user = new Student();
            user.setUsername("ahiber1");
            user.setEmail("hibernate1@test.com");
            user.setPassword("pass123");
            user.setFirstName("Hibernate");
            user.setLastName("Test");

            session.persist(user);
            session.getTransaction().commit();

            System.out.println("✅ User créé avec Hibernate, ID: " + user.getId());

        } catch (Exception e) {
            System.out.println("❌ Erreur Hibernate: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
            if (sessionFactory != null) sessionFactory.close();
        }
    }
}