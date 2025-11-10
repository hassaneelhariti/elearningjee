package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class LessonDao {
    
    public Lesson save(Lesson lesson) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(lesson);
            tx.commit();
            return lesson;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Lesson update(Lesson lesson) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(lesson);
            tx.commit();
            return lesson;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Lesson lesson) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(lesson);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Lesson findById(Long id) {
        Session session = null;
        Lesson lesson = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            lesson = session.get(Lesson.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lesson;
    }
    
    public List<Lesson> findAll() {
        Session session = null;
        List<Lesson> lessons = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Lesson> query = session.createQuery("FROM Lesson", Lesson.class);
            lessons = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lessons;
    }
    
    public List<Lesson> findBySectionId(Long sectionId) {
        Session session = null;
        List<Lesson> lessons = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Lesson> query = session.createQuery(
                "FROM Lesson l WHERE l.section.id = :sectionId ORDER BY l.orderIndex", 
                Lesson.class);
            query.setParameter("sectionId", sectionId);
            lessons = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lessons;
    }
}