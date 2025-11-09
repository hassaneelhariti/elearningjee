/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.controller.teacher;

/**
 *
 * @author ousam713
 */


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

@WebServlet("/teacher/lessons/*")
public class LessonServlet extends HttpServlet {
    
    @Inject
    private CourseService courseService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        String action = (pathInfo != null) ? pathInfo.substring(1) : "create";
        
        if ("create".equals(action)) {
            showCreateLessonForm(request, response, teacher);
        }
    }
    
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
                createLesson(request, response, teacher);
                break;
            case "add-video":
                addVideoToLesson(request, response, teacher);
                break;
        }
    }
    
    private void showCreateLessonForm(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        try {
            Long sectionId = Long.parseLong(request.getParameter("sectionId"));
            // Vérifier les permissions et afficher le formulaire
            request.setAttribute("sectionId", sectionId);
            request.getRequestDispatcher("/teacher/create-lesson.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de section invalide");
        }
    }
    
    private void createLesson(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        try {
            Long sectionId = Long.parseLong(request.getParameter("sectionId"));
            String title = request.getParameter("title");
            Integer orderIndex = Integer.parseInt(request.getParameter("orderIndex"));
            
            // Implémentation de la création de leçon
            // Redirection vers la gestion du contenu du cours
            response.sendRedirect(request.getContextPath() + "/teacher/courses/content?id=COURSE_ID");
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création de la leçon: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/teacher/courses");
        }
    }
    
    private void addVideoToLesson(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws IOException {
        
        try {
            Long lessonId = Long.parseLong(request.getParameter("lessonId"));
            String url = request.getParameter("url");
            Integer duration = Integer.parseInt(request.getParameter("duration"));
            
            // Implémentation de l'ajout de vidéo
            response.sendRedirect(request.getContextPath() + "/teacher/courses/content?id=COURSE_ID");
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données invalides");
        }
    }
}

