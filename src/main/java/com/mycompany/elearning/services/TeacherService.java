package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.TeacherDao;

public class TeacherService {
    private TeacherDao teacherDao;

    public TeacherService(){}
    public TeacherService(TeacherDao teacherDao){ this.teacherDao = teacherDao; }


    public String findTeacherNameById(Long id){
        return teacherDao.findTeacherById(id).toString();
    }
}
