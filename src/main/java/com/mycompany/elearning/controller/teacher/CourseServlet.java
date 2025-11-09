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
import java.util.List;

@WebServlet("/teacher/courses/*")
public class CourseServlet extends HttpServlet {
    
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
        String action = (pathInfo != null) ? pathInfo.substring(1) : "list";
        
        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response, teacher);
                break;
            case "content":
                showContentManagement(request, response, teacher);
                break;
            default:
                listCourses(request, response, teacher);
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
        String action = (pathInfo != null) ? pathInfo.substring(1) : "list";
        
        switch (action) {
            case "create":
                createCourse(request, response, teacher);
                break;
            case "update":
                updateCourse(request, response, teacher);
                break;
            case "publish":
                publishCourse(request, response, teacher);
                break;
            case "unpublish":
                unpublishCourse(request, response, teacher);
                break;
        }
    }
    
    private void listCourses(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        List<Course> courses = courseService.getTeacherCourses(teacher);
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/teacher/my-courses.jsp").forward(request, response);
    }
    
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/teacher/create-course.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        try {
            Long courseId = Long.parseLong(request.getParameter("id"));
            Course course = courseService.findCourseById(courseId);
            
            // Vérifier que le cours appartient à l'enseignant
            if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                request.setAttribute("course", course);
                request.getRequestDispatcher("/teacher/edit-course.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cours invalide");
        }
    }
    
    private void showContentManagement(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        try {
            Long courseId = Long.parseLong(request.getParameter("id"));
            Course course = courseService.findCourseById(courseId);
            
            if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                request.setAttribute("course", course);
                request.getRequestDispatcher("/teacher/manage-content.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cours invalide");
        }
    }
    
    private void createCourse(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String level = request.getParameter("level");
        String thumbnail = request.getParameter("thumbnail");
        
        try {
            Course course = courseService.createCourse(title, description, level, teacher);
            
            if (thumbnail != null && !thumbnail.trim().isEmpty()) {
                course.setThumbnail(thumbnail);
                courseService.updateCourse(course);
            }
            
            response.sendRedirect(request.getContextPath() + "/teacher/courses");
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création du cours: " + e.getMessage());
            request.getRequestDispatcher("/teacher/create-course.jsp").forward(request, response);
        }
    }
    
    private void updateCourse(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws ServletException, IOException {
        
        try {
            Long courseId = Long.parseLong(request.getParameter("id"));
            Course course = courseService.findCourseById(courseId);
            
            if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                course.setTitle(request.getParameter("title"));
                course.setDescription(request.getParameter("description"));
                course.setLevel(request.getParameter("level"));
                course.setThumbnail(request.getParameter("thumbnail"));
                course.setIsPublished("published".equals(request.getParameter("status")));
                
                courseService.updateCourse(course);
                response.sendRedirect(request.getContextPath() + "/teacher/courses");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la modification: " + e.getMessage());
            showEditForm(request, response, teacher);
        }
    }
    
    private void publishCourse(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws IOException {
        
        try {
            Long courseId = Long.parseLong(request.getParameter("id"));
            Course course = courseService.findCourseById(courseId);
            
            if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                course.setIsPublished(true);
                courseService.updateCourse(course);
            }
            
            response.sendRedirect(request.getContextPath() + "/teacher/courses");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cours invalide");
        }
    }
    
    private void unpublishCourse(HttpServletRequest request, HttpServletResponse response, Teacher teacher) 
            throws IOException {
        
        try {
            Long courseId = Long.parseLong(request.getParameter("id"));
            Course course = courseService.findCourseById(courseId);
            
            if (course != null && course.getTeacher().getId().equals(teacher.getId())) {
                course.setIsPublished(false);
                courseService.updateCourse(course);
            }
            
            response.sendRedirect(request.getContextPath() + "/teacher/courses");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cours invalide");
        }
    }
}
