package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class LessonProgressDao {
    
    public LessonProgress save(LessonProgress progress) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(progress);
            tx.commit();
            return progress;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public LessonProgress update(LessonProgress progress) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(progress);
            tx.commit();
            return progress;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(LessonProgress progress) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(progress);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public LessonProgress findById(Long id) {
        Session session = null;
        LessonProgress progress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            progress = session.get(LessonProgress.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return progress;
    }
    
    public List<LessonProgress> findAll() {
        Session session = null;
        List<LessonProgress> progresses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LessonProgress> query = session.createQuery("FROM LessonProgress", LessonProgress.class);
            progresses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return progresses;
    }
    
    public LessonProgress findByEnrollmentAndLesson(Long enrollmentId, Long lessonId) {
        Session session = null;
        LessonProgress progress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LessonProgress> query = session.createQuery(
                "FROM LessonProgress lp WHERE lp.enrollment.id = :enrollmentId AND lp.lesson.id = :lessonId", 
                LessonProgress.class);
            query.setParameter("enrollmentId", enrollmentId);
            query.setParameter("lessonId", lessonId);
            progress = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return progress;
    }
    
    public List<LessonProgress> findByEnrollmentId(Long enrollmentId) {
        Session session = null;
        List<LessonProgress> progresses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LessonProgress> query = session.createQuery(
                "FROM LessonProgress lp WHERE lp.enrollment.id = :enrollmentId", 
                LessonProgress.class);
            query.setParameter("enrollmentId", enrollmentId);
            progresses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return progresses;
    }
}