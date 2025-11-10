
package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Utilisateurs.Admin;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

/**
 * DAO pour Admin
 */
public class AdminDAO {
    
    public Admin save(Admin admin) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(admin);
            tx.commit();
            return admin;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Admin update(Admin admin) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(admin);
            tx.commit();
            return admin;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Admin admin) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(admin);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Admin findById(Long id) {
        Session session = null;
        Admin admin = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            admin = session.get(Admin.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return admin;
    }
    
    public List<Admin> findAll() {
        Session session = null;
        List<Admin> admins = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Admin> query = session.createQuery("FROM Admin", Admin.class);
            admins = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return admins;
    }
    
    public Admin findByEmail(String email) {
        Session session = null;
        Admin admin = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Admin> query = session.createQuery(
                "FROM Admin WHERE email = :email", Admin.class);
            query.setParameter("email", email);
            admin = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return admin;
    }
}