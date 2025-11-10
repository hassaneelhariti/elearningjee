package com.mycompany.elearning.controller;

import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.services.EnrollmentService;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Section;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    
    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
        enrollmentService = new EnrollmentService();
    }
    
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
            case "byLevel":
                coursesByLevel(request, response);
                break;
            default:
                listCourses(request, response);
                break;
        }
    }
    
    private void listCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courses = courseService.getPublishedCourses();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/WEB-INF/views/courses/list.jsp").forward(request, response);
    }
    
    private void viewCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long courseId = Long.parseLong(request.getParameter("id"));
        Course course = courseService.getCourseById(courseId);
        List<Section> sections = courseService.getCourseSections(courseId);
        long enrollmentCount = courseService.getEnrollmentCount(courseId);
        
        request.setAttribute("course", course);
        request.setAttribute("sections", sections);
        request.setAttribute("enrollmentCount", enrollmentCount);
        request.getRequestDispatcher("/WEB-INF/views/courses/view.jsp").forward(request, response);
    }
    
    private void searchCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Course> courses = courseService.searchCourses(keyword);
        request.setAttribute("courses", courses);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/WEB-INF/views/courses/list.jsp").forward(request, response);
    }
    
    private void coursesByLevel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String level = request.getParameter("level");
        List<Course> courses = courseService.getCoursesByLevel(level);
        request.setAttribute("courses", courses);
        request.setAttribute("level", level);
        request.getRequestDispatcher("/WEB-INF/views/courses/list.jsp").forward(request, response);
    }
}