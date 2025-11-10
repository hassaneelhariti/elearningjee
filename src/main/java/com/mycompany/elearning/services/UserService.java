package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.StudentDao;
import com.mycompany.elearning.dao.TeacherDao;
import com.mycompany.elearning.dao.AdminDao;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.Admin;
import com.mycompany.elearning.entities.Utilisateurs.User;

public class UserService {
    
    private StudentDao studentDao;
    private TeacherDao teacherDao;
    private AdminDao adminDao;
    
    public UserService() {
        this.studentDao = new StudentDao();
        this.teacherDao = new TeacherDao();
        this.adminDao = new AdminDao();
    }
    
    public Student registerStudent(Student student) {
        return studentDao.save(student);
    }
    
    public Teacher registerTeacher(Teacher teacher) {
        return teacherDao.save(teacher);
    }
    
    public Admin registerAdmin(Admin admin) {
        return adminDao.save(admin);
    }
    
    public User login(String email, String password) {
        Student student = studentDao.findByEmail(email);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        
        Teacher teacher = teacherDao.findByEmail(email);
        if (teacher != null && teacher.getPassword().equals(password)) {
            return teacher;
        }
        
        Admin admin = adminDao.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        
        return null;
    }
    
    public boolean isEmailExists(String email) {
        return studentDao.findByEmail(email) != null || 
               teacherDao.findByEmail(email) != null || 
               adminDao.findByEmail(email) != null;
    }
    
    public boolean isUsernameExists(String username) {
        return studentDao.findByUsername(username) != null || 
               teacherDao.findByUsername(username) != null || 
               adminDao.findByUsername(username) != null;
    }
    
    public Student getStudentById(Long id) {
        return studentDao.findById(id);
    }
    
    public Teacher getTeacherById(Long id) {
        return teacherDao.findById(id);
    }
    
    public Admin getAdminById(Long id) {
        return adminDao.findById(id);
    }
}