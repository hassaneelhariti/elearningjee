/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.controller.teacher;

/**
 *
 * @author ousam713
 */



import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.service.CourseService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/teacher/sections/*")
public class SectionServlet extends HttpServlet {
    
    @Inject
    private CourseService courseService;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        String action = (pathInfo != null) ? pathInfo.substring(1) : "create";
        
        switch (action) {
            case "create":
                createSection(request, response, teacher);
                break;
            case "update":
                updateSection(request, response, teacher);
                break;
        }
    }
    
    private void createSection(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        try {
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            String title = request.getParameter("title");
            Integer orderIndex = Integer.parseInt(request.getParameter("orderIndex"));
            
            Course course = courseService.findCourseById(courseId);
            
            if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                courseService.addSectionToCourse(course, title, orderIndex);
                response.sendRedirect(request.getContextPath() + "/teacher/courses/content?id=" + courseId);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création de la section: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/teacher/courses");
        }
    }
    
    private void updateSection(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws IOException {
        
        // Implémentation similaire pour la modification
        response.sendRedirect(request.getContextPath() + "/teacher/courses");
    }
}
