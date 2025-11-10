
package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.services.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller pour gérer les cours (enseignant)
 */
@WebServlet(name = "TeacherCourseServlet", urlPatterns = {"/teacher/course"})
public class TeacherCourseServlet extends HttpServlet {
    
    private CourseService courseService = new CourseService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/teacher/course-form.jsp")
                .forward(request, response);
        } else if ("edit".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    Long courseId = Long.parseLong(idParam);
                    Course course = courseService.getCourseById(courseId);
                    request.setAttribute("course", course);
                    request.getRequestDispatcher("/WEB-INF/views/teacher/course-form.jsp")
                        .forward(request, response);
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
                }
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        Teacher teacher = (Teacher) session.getAttribute("user");
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String level = request.getParameter("level");
            String thumbnail = request.getParameter("thumbnail");
            
            Course course = new Course(title, description, level, teacher);
            course.setThumbnail(thumbnail);
            courseService.createCourse(course);
            
            response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            
        } else if ("update".equals(action)) {
            String idParam = request.getParameter("id");
            try {
                Long courseId = Long.parseLong(idParam);
                Course course = courseService.getCourseById(courseId);
                
                if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                    course.setTitle(request.getParameter("title"));
                    course.setDescription(request.getParameter("description"));
                    course.setLevel(request.getParameter("level"));
                    course.setThumbnail(request.getParameter("thumbnail"));
                    
                    courseService.updateCourse(course);
                }
                
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
                
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            }
            
        } else if ("publish".equals(action)) {
            String idParam = request.getParameter("id");
            try {
                Long courseId = Long.parseLong(idParam);
                courseService.publishCourse(courseId);
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            }
            
        } else if ("unpublish".equals(action)) {
            String idParam = request.getParameter("id");
            try {
                Long courseId = Long.parseLong(idParam);
                courseService.unpublishCourse(courseId);
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
            }
        }
    }
}