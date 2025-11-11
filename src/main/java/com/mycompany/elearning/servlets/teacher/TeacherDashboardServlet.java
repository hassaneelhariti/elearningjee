package com.mycompany.elearning.servlets.teacher;

import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.services.TeacherService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.io.IOException;

@WebServlet("/teacher/dashboard")
public class TeacherDashboardServlet extends HttpServlet {

    @PersistenceUnit(unitName = "elearningPU")
    private EntityManagerFactory emf;

    private CourseService courseService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        EntityManager em = emf.createEntityManager();
        this.courseService = new CourseService(em);
        this.teacherService = new TeacherService(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Teacher teacher = getCurrentTeacher(request);
        if (teacher == null) {
            response.sendRedirect("../login");
            return;
        }

        try {
            // Get teacher's courses with statistics
            var courses = courseService.getCoursesByTeacher(teacher.getId());
            Long totalStudents = courseService.getStudentCountByTeacher(teacher.getId());
            Long totalLessons = courseService.getLessonCountByTeacher(teacher.getId());

            request.setAttribute("teacher", teacher);
            request.setAttribute("courses", courses);
            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("totalLessons", totalLessons);
            request.setAttribute("totalCourses", courses.size());

            request.getRequestDispatcher("/teacher/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading dashboard");
        }
    }

    private Teacher getCurrentTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (userObj instanceof Teacher && "TEACHER".equalsIgnoreCase(role)) {
            return (Teacher) userObj;
        }
        return null;
    }
}