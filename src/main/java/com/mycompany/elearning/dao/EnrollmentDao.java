package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class EnrollmentDao {
    
    public Enrollment save(Enrollment enrollment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(enrollment);
            tx.commit();
            return enrollment;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Enrollment update(Enrollment enrollment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(enrollment);
            tx.commit();
            return enrollment;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Enrollment enrollment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(enrollment);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Enrollment findById(Long id) {
        Session session = null;
        Enrollment enrollment = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            enrollment = session.get(Enrollment.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return enrollment;
    }
    
    public List<Enrollment> findAll() {
        Session session = null;
        List<Enrollment> enrollments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Enrollment> query = session.createQuery("FROM Enrollment", Enrollment.class);
            enrollments = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return enrollments;
    }
    
    public Enrollment findByStudentAndCourse(Long studentId, Long courseId) {
        Session session = null;
        Enrollment enrollment = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Enrollment> query = session.createQuery(
                "FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId", 
                Enrollment.class);
            query.setParameter("studentId", studentId);
            query.setParameter("courseId", courseId);
            enrollment = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return enrollment;
    }
    
    public List<Enrollment> findByCourseId(Long courseId) {
        Session session = null;
        List<Enrollment> enrollments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Enrollment> query = session.createQuery(
                "FROM Enrollment e WHERE e.course.id = :courseId", 
                Enrollment.class);
            query.setParameter("courseId", courseId);
            enrollments = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return enrollments;
    }
    
    public long countByCourseId(Long courseId) {
        Session session = null;
        Long count = 0L;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(e) FROM Enrollment e WHERE e.course.id = :courseId", 
                Long.class);
            query.setParameter("courseId", courseId);
            count = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return count;
    }
}