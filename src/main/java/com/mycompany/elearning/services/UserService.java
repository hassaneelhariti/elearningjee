package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.StudentDAO;
import com.mycompany.elearning.dao.TeacherDAO;
import com.mycompany.elearning.dao.AdminDAO;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.Admin;

/**
 * Service pour gérer les utilisateurs
 */
public class UserService {
    
    private StudentDAO studentDAO = new StudentDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private AdminDAO adminDAO = new AdminDAO();
    
    // Méthodes pour Student
    public Student registerStudent(Student student) {
        return studentDAO.save(student);
    }
    
    public Student updateStudent(Student student) {
        return studentDAO.update(student);
    }
    
    public Student getStudentById(Long id) {
        return studentDAO.findById(id);
    }
    
    public Student getStudentByEmail(String email) {
        return studentDAO.findByEmail(email);
    }
    
    public Student loginStudent(String email, String password) {
        Student student = studentDAO.findByEmail(email);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }
    
    // Méthodes pour Teacher
    public Teacher registerTeacher(Teacher teacher) {
        return teacherDAO.save(teacher);
    }
    
    public Teacher updateTeacher(Teacher teacher) {
        return teacherDAO.update(teacher);
    }
    
    public Teacher getTeacherById(Long id) {
        return teacherDAO.findById(id);
    }
    
    public Teacher getTeacherByEmail(String email) {
        return teacherDAO.findByEmail(email);
    }
    
    public Teacher loginTeacher(String email, String password) {
        Teacher teacher = teacherDAO.findByEmail(email);
        if (teacher != null && teacher.getPassword().equals(password)) {
            return teacher;
        }
        return null;
    }
    
    // Méthodes pour Admin
    public Admin registerAdmin(Admin admin) {
        return adminDAO.save(admin);
    }
    
    public Admin getAdminById(Long id) {
        return adminDAO.findById(id);
    }
    
    public Admin getAdminByEmail(String email) {
        return adminDAO.findByEmail(email);
    }
    
    public Admin loginAdmin(String email, String password) {
        Admin admin = adminDAO.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}