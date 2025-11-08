package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Utilisateurs.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.openSession();
        try {
            Query<User> query = session.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();
            return Optional.ofNullable(user);
        } finally {
            session.close();
        }
    }

    public Optional<User> findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            session.close();
        }
    }

    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM User", User.class).list();
        } finally {
            session.close();
        }
    }

    public void save(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}