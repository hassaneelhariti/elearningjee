package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.services.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet pour gérer les cours (affichage public)
 */
@WebServlet(name = "CourseServlet", urlPatterns = {"/courses"})
public class CourseServlet extends HttpServlet {
    
    private CourseService courseService = new CourseService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listCourses(request, response);
                break;
            case "view":
                viewCourse(request, response);
                break;
            case "search":
                searchCourses(request, response);
                break;
            default:
                listCourses(request, response);
        }
    }
    
    private void listCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String level = request.getParameter("level");
        List<Course> courses;
        
        if (level != null && !level.isEmpty()) {
            courses = courseService.getCoursesByLevel(level);
            request.setAttribute("selectedLevel", level);
        } else {
            courses = courseService.getPublishedCourses();
        }
        
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/WEB-INF/views/courses/list.jsp").forward(request, response);
    }
    
    private void viewCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                Long courseId = Long.parseLong(idParam);
                Course course = courseService.getCourseById(courseId);
                
                if (course != null) {
                    request.setAttribute("course", course);
                    request.setAttribute("sections", courseService.getCourseSections(courseId));
                    request.setAttribute("enrollmentCount", courseService.getCourseEnrollmentCount(courseId));
                    request.getRequestDispatcher("/WEB-INF/views/courses/view.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/courses");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/courses");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }
    
    private void searchCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String keyword = request.getParameter("keyword");
        List<Course> courses;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            courses = courseService.searchCourses(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            courses = courseService.getPublishedCourses();
        }
        
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/WEB-INF/views/courses/list.jsp").forward(request, response);
    }
}