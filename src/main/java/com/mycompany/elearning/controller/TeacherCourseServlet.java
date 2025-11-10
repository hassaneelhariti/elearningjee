package com.mycompany.elearning.controller;

import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/courses")
public class TeacherCourseServlet extends HttpServlet {
    
    private CourseService courseService;
    
    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userType") == null ||
                !"TEACHER".equals(session.getAttribute("userType"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listTeacherCourses(request, response);
                break;
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCourse(request, response);
                break;
            case "publish":
                publishCourse(request, response);
                break;
            default:
                listTeacherCourses(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"TEACHER".equals(session.getAttribute("userType"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            createCourse(request, response);
        } else if ("update".equals(action)) {
            updateCourse(request, response);
        }
    }
    
    private void listTeacherCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long teacherId = (Long) request.getSession().getAttribute("userId");
        List<Course> courses = courseService.getCoursesByTeacher(teacherId);
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/WEB-INF/views/teacher/courses.jsp").forward(request, response);
    }
    
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/teacher/course-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long courseId = Long.parseLong(request.getParameter("id"));
        Course course = courseService.getCourseById(courseId);
        request.setAttribute("course", course);
        request.getRequestDispatcher("/WEB-INF/views/teacher/course-form.jsp").forward(request, response);
    }
    
    private void createCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String level = request.getParameter("level");
        String thumbnail = request.getParameter("thumbnail");
        
        Course course = new Course(title, description, level, teacher);
        course.setThumbnail(thumbnail);
        courseService.createCourse(course);
        
        response.sendRedirect(request.getContextPath() + "/teacher/courses");
    }
    
    private void updateCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long courseId = Long.parseLong(request.getParameter("id"));
        Course course = courseService.getCourseById(courseId);
        
        course.setTitle(request.getParameter("title"));
        course.setDescription(request.getParameter("description"));
        course.setLevel(request.getParameter("level"));
        course.setThumbnail(request.getParameter("thumbnail"));
        
        courseService.updateCourse(course);
        response.sendRedirect(request.getContextPath() + "/teacher/courses");
    }
    
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long courseId = Long.parseLong(request.getParameter("id"));
        courseService.deleteCourse(courseId);
        response.sendRedirect(request.getContextPath() + "/teacher/courses");
    }
    
    private void publishCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long courseId = Long.parseLong(request.getParameter("id"));
        String publish = request.getParameter("publish");
        
        if ("true".equals(publish)) {
            courseService.publishCourse(courseId);
        } else {
            courseService.unpublishCourse(courseId);
        }
        
        response.sendRedirect(request.getContextPath() + "/teacher/courses");
    }
}