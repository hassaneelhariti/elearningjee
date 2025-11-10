package com.mycompany.elearning.dao;

import jakarta.persistence.*;
import java.util.List;

public abstract class GenericDAO<T> {
    
    protected EntityManager em;
    private final Class<T> entityClass;
    
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.em = EntityManagerUtil.getEntityManager();
    }
    
    public T findById(Long id) {
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<T> findAll() {
        try {
            String query = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(query, entityClass).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public T save(T entity) {
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            System.out.println("✅ Entity saved: " + entity);
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("❌ Error saving entity: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public T update(T entity) {
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            T updatedEntity = em.merge(entity);
            transaction.commit();
            return updatedEntity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
    
    public void delete(Long id) {
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}