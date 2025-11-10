package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

/**
 * DAO pour Student
 */
public class StudentDAO {
    
    public Student save(Student student) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
            return student;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Student update(Student student) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(student);
            tx.commit();
            return student;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Student student) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Student findById(Long id) {
        Session session = null;
        Student student = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            student = session.get(Student.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return student;
    }
    
    public List<Student> findAll() {
        Session session = null;
        List<Student> students = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            students = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return students;
    }
    
    public Student findByEmail(String email) {
        Session session = null;
        Student student = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Student> query = session.createQuery(
                "FROM Student WHERE email = :email", Student.class);
            query.setParameter("email", email);
            student = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return student;
    }
    
    public List<Enrollment> getStudentEnrollments(Long studentId) {
        Session session = null;
        List<Enrollment> enrollments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Enrollment> query = session.createQuery(
                "FROM Enrollment e WHERE e.student.id = :studentId ORDER BY e.enrollmentDate DESC", 
                Enrollment.class);
            query.setParameter("studentId", studentId);
            enrollments = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return enrollments;
    }
    
    public boolean isEnrolledInCourse(Long studentId, Long courseId) {
        Session session = null;
        Long count = 0L;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                "SELECT COUNT(e) FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId", 
                Long.class);
            query.setParameter("studentId", studentId);
            query.setParameter("courseId", courseId);
            count = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return count > 0;
    }
}