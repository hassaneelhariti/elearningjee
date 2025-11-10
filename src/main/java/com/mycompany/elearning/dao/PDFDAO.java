package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.PDF;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * DAO pour PDF
 */
public class PDFDAO {
    
    public PDF save(PDF pdf) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(pdf);
            tx.commit();
            return pdf;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public PDF update(PDF pdf) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(pdf);
            tx.commit();
            return pdf;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(PDF pdf) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(pdf);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public PDF findById(Long id) {
        Session session = null;
        PDF pdf = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            pdf = session.get(PDF.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return pdf;
    }
    
    public PDF findByLessonId(Long lessonId) {
        Session session = null;
        PDF pdf = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<PDF> query = session.createQuery(
                "FROM PDF p WHERE p.lesson.id = :lessonId", 
                PDF.class);
            query.setParameter("lessonId", lessonId);
            pdf = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return pdf;
    }
}