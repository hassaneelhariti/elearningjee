package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

/**
 * DAO pour LessonProgress
 */
public class LessonProgressDAO {
    
    public LessonProgress save(LessonProgress lessonProgress) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(lessonProgress);
            tx.commit();
            return lessonProgress;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public LessonProgress update(LessonProgress lessonProgress) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(lessonProgress);
            tx.commit();
            return lessonProgress;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(LessonProgress lessonProgress) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(lessonProgress);
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
        LessonProgress lessonProgress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            lessonProgress = session.get(LessonProgress.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lessonProgress;
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
    
    public LessonProgress findByEnrollmentAndLesson(Long enrollmentId, Long lessonId) {
        Session session = null;
        LessonProgress lessonProgress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LessonProgress> query = session.createQuery(
                "FROM LessonProgress lp WHERE lp.enrollment.id = :enrollmentId AND lp.lesson.id = :lessonId", 
                LessonProgress.class);
            query.setParameter("enrollmentId", enrollmentId);
            query.setParameter("lessonId", lessonId);
            lessonProgress = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lessonProgress;
    }
    
    public long countCompletedLessons(Long enrollmentId) {
        Session session = null;
        Long count = 0L;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(lp) FROM LessonProgress lp WHERE lp.enrollment.id = :enrollmentId AND lp.isCompleted = true", 
                Long.class);
            query.setParameter("enrollmentId", enrollmentId);
            count = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return count;
    }
}