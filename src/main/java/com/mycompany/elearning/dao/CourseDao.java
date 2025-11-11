package com.mycompany.elearning.dao;

import com.mycompany.elearning.entities.Contenu.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public class CourseDao {
    private EntityManager em;

    public CourseDao(EntityManager em) {
        this.em = em;
    }

    public List<Course> getPublishedCourse(){
        return em.createQuery("SELECT c FROM Course c WHERE c.isPublished = true", Course.class).getResultList();
    }

    public Course findCourseById(Long id){
        return em.find(Course.class, id);
    }

    public Course findCourseByName(String courseName){
        try {
            TypedQuery<Course> query = em.createQuery(
                    "SELECT c FROM Course c WHERE c.title = :courseName",
                    Course.class
            );
            query.setParameter("courseName", courseName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Course> findAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    // Ajoutez cette méthode pour sauvegarder un cours si nécessaire
    public Course save(Course course) {
        if (course.getId() == null) {
            em.persist(course);
            return course;
        } else {
            return em.merge(course);
        }
    }
}