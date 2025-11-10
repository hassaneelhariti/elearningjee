package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class SectionDao {
    
    public Section save(Section section) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(section);
            tx.commit();
            return section;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Section update(Section section) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(section);
            tx.commit();
            return section;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public void delete(Section section) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(section);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public Section findById(Long id) {
        Session session = null;
        Section section = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            section = session.get(Section.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return section;
    }
    
    public List<Section> findAll() {
        Session session = null;
        List<Section> sections = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Section> query = session.createQuery("FROM Section", Section.class);
            sections = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return sections;
    }
    
    public List<Section> findByCourseId(Long courseId) {
        Session session = null;
        List<Section> sections = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Section> query = session.createQuery(
                "FROM Section s WHERE s.course.id = :courseId ORDER BY s.orderIndex", 
                Section.class);
            query.setParameter("courseId", courseId);
            sections = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return sections;
    }
}