package com.mycompany.elearning.servlets;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    private EntityManager em;

    @Override
    public void init() throws ServletException {
        em = Persistence.createEntityManagerFactory("elearningPU").createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get statistics
            CourseDao courseDao = new CourseDao(em);

            Long totalStudents = countUsersByType(Student.class);
            Long totalTeachers = countUsersByType(Teacher.class);
            Long totalCourses = (long) courseDao.findAll().size();
            Long totalEnrollments = countTotalEnrollments();

            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("totalTeachers", totalTeachers);
            request.setAttribute("totalCourses", totalCourses);
            request.setAttribute("totalEnrollments", totalEnrollments);

            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Set default values if error
            request.setAttribute("totalStudents", 1000L);
            request.setAttribute("totalTeachers", 100L);
            request.setAttribute("totalCourses", 500L);
            request.setAttribute("totalEnrollments", 5000L);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private Long countUsersByType(Class<?> userType) {
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(*) FROM " + userType.getSimpleName(), Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0L;
        }
    }

    private Long countTotalEnrollments() {
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Enrollment e", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public void destroy() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

