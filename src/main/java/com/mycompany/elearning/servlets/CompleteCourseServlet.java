package com.mycompany.elearning.servlets;

import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/complete-course")
public class CompleteCourseServlet extends HttpServlet {

    @PersistenceUnit(unitName = "elearningPU")
    private EntityManagerFactory emf;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            Long courseId = Long.valueOf(request.getParameter("courseId"));
            Long studentId = getCurrentStudentId(request);

            if (studentId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            transaction.begin();

            // Mark enrollment as completed by setting progress to 100%
            markCourseAsCompleted(em, courseId, studentId);

            transaction.commit();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            em.close();
        }
    }

    private void markCourseAsCompleted(EntityManager em, Long courseId, Long studentId) {
        // Find enrollment
        TypedQuery<Enrollment> query = em.createQuery(
                "SELECT e FROM Enrollment e " +
                        "WHERE e.course.id = :courseId AND e.student.id = :studentId",
                Enrollment.class
        );
        query.setParameter("courseId", courseId);
        query.setParameter("studentId", studentId);

        List<Enrollment> results = query.getResultList();

        if (!results.isEmpty()) {
            Enrollment enrollment = results.get(0);
            // Set progress to 100% to mark as completed
            enrollment.setProgress(100.0f);
            em.merge(enrollment);
        }
    }

    private Long getCurrentStudentId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        return student != null ? student.getId() : null;
    }
}