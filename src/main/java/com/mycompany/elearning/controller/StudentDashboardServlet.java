package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.services.EnrollmentService;
import com.mycompany.elearning.services.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Controller pour le tableau de bord étudiant
 */
@WebServlet(name = "StudentDashboardServlet", urlPatterns = {"/student/dashboard"})
public class StudentDashboardServlet extends HttpServlet {
    
    private EnrollmentService enrollmentService = new EnrollmentService();
    private CourseService courseService = new CourseService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        Student student = (Student) session.getAttribute("user");
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(student.getId());
        
        request.setAttribute("student", student);
        request.setAttribute("enrollments", enrollments);
        request.setAttribute("availableCourses", courseService.getPublishedCourses());
        
        request.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp")
            .forward(request, response);
    }
}