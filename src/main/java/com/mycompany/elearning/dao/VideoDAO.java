package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Video;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * DAO pour Video
 */
public class VideoDAO {
    
    public Video save(Video video) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(video);
            tx.commit();
            return video;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Video update(Video video) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(video);
            tx.commit();
            return video;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Video video) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(video);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Video findById(Long id) {
        Session session = null;
        Video video = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            video = session.get(Video.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return video;
    }
    
    public Video findByLessonId(Long lessonId) {
        Session session = null;
        Video video = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Video> query = session.createQuery(
                "FROM Video v WHERE v.lesson.id = :lessonId", 
                Video.class);
            query.setParameter("lessonId", lessonId);
            video = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return video;
    }
}