package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class TeacherDao {
    
    public Teacher save(Teacher teacher) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(teacher);
            tx.commit();
            return teacher;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Teacher update(Teacher teacher) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(teacher);
            tx.commit();
            return teacher;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Teacher teacher) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(teacher);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Teacher findById(Long id) {
        Session session = null;
        Teacher teacher = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            teacher = session.get(Teacher.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return teacher;
    }
    
    public List<Teacher> findAll() {
        Session session = null;
        List<Teacher> teachers = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Teacher> query = session.createQuery("FROM Teacher", Teacher.class);
            teachers = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return teachers;
    }
    
    public Teacher findByEmail(String email) {
        Session session = null;
        Teacher teacher = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Teacher> query = session.createQuery(
                "FROM Teacher WHERE email = :email", Teacher.class);
            query.setParameter("email", email);
            teacher = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return teacher;
    }
    
    public Teacher findByUsername(String username) {
        Session session = null;
        Teacher teacher = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Teacher> query = session.createQuery(
                "FROM Teacher WHERE username = :username", Teacher.class);
            query.setParameter("username", username);
            teacher = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return teacher;
    }
    
    public List<Course> getTeacherCourses(Long teacherId) {
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
    
    public List<Course> getPublishedCoursesByTeacher(Long teacherId) {
        Session session = null;
        List<Course> courses = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Course> query = session.createQuery(
                "FROM Course c WHERE c.teacher.id = :teacherId AND c.isPublished = true ORDER BY c.dateCreated DESC", 
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
}