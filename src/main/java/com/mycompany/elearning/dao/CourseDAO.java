package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

/**
 * DAO pour Course
 */
public class CourseDAO {
    
    public Course save(Course course) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(course);
            tx.commit();
            return course;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Course update(Course course) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(course);
            tx.commit();
            return course;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Course course) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(course);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Course findById(Long id) {
        Session session = null;
        Course course = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            course = session.get(Course.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return course;
    }
    
    public List<Course> findAll() {
        Session session = null;
        List<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery("FROM Course", Course.class);
            courses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return courses;
    }
    
    public List<Course> findPublishedCourses() {
        Session session = null;
        List<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery(
                "FROM Course c WHERE c.isPublished = true ORDER BY c.dateCreated DESC", 
                Course.class);
            courses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return courses;
    }
    
    public List<Course> findByLevel(String level) {
        Session session = null;
        List<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery(
                "FROM Course c WHERE c.level = :level AND c.isPublished = true ORDER BY c.dateCreated DESC", 
                Course.class);
            query.setParameter("level", level);
            courses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return courses;
    }
    
    public List<Course> searchByTitle(String keyword) {
        Session session = null;
        List<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery(
                "FROM Course c WHERE LOWER(c.title) LIKE LOWER(:keyword) AND c.isPublished = true ORDER BY c.dateCreated DESC", 
                Course.class);
            query.setParameter("keyword", "%" + keyword + "%");
            courses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return courses;
    }
    
    public List<Course> findByTeacherId(Long teacherId) {
        Session session = null;
        List<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery(
                "FROM Course c WHERE c.teacher.id = :teacherId ORDER BY c.dateCreated DESC", 
                Course.class);
            query.setParameter("teacherId", teacherId);
            courses = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return courses;
    }
    
    public long countByTeacherId(Long teacherId) {
        Session session = null;
        Long count = 0L;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(c) FROM Course c WHERE c.teacher.id = :teacherId", 
                Long.class);
            query.setParameter("teacherId", teacherId);
            count = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return count;
    }
}