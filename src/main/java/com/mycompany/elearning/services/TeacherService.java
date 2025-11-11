package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.TeacherDao;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TeacherService {
    private TeacherDao teacherDao;
    private EntityManager em;

    public TeacherService() {}

    public TeacherService(EntityManager em) {
        this.em = em;
        this.teacherDao = new TeacherDao(em);
    }

    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public String findTeacherNameById(Long id){
        return teacherDao.findTeacherById(id);
    }

    // Nouvelles méthodes pour le sous-système enseignant
    public Teacher getTeacherById(Long id) {
        return teacherDao.findTeacherEntityById(id);
    }

    public Teacher getTeacherByEmail(String email) {
        return teacherDao.findByEmail(email);
    }

    public boolean validateTeacherAccess(Long teacherId, Object sessionUser, String sessionRole) {
        if (!"TEACHER".equalsIgnoreCase(sessionRole)) {
            return false;
        }

        if (sessionUser instanceof Teacher) {
            Teacher teacher = (Teacher) sessionUser;
            return teacher.getId().equals(teacherId);
        }

        return false;
    }

    public List<Teacher> getAllTeachers() {
        return teacherDao.findAll();
    }
}