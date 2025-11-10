package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.services.EnrollmentService;
import com.mycompany.elearning.services.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller pour l'inscription aux cours
 */
@WebServlet(name = "EnrollServlet", urlPatterns = {"/student/enroll"})
public class EnrollServlet extends HttpServlet {
    
    private EnrollmentService enrollmentService = new EnrollmentService();
    private CourseService courseService = new CourseService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        Student student = (Student) session.getAttribute("user");
        String courseIdParam = request.getParameter("courseId");
        
        try {
            Long courseId = Long.parseLong(courseIdParam);
            Course course = courseService.getCourseById(courseId);
            
            if (course != null && course.getIsPublished()) {
                enrollmentService.enrollStudent(student.getId(), courseId);
                request.setAttribute("success", "Inscription réussie au cours: " + course.getTitle());
            } else {
                request.setAttribute("error", "Cours non disponible");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de cours invalide");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'inscription: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/student/dashboard");
    }
}