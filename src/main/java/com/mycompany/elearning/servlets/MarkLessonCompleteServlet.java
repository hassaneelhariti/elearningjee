package com.mycompany.elearning.servlets;

import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/mark-lesson-complete")
public class MarkLessonCompleteServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        try {
            emf = Persistence.createEntityManagerFactory("elearningPU");
        } catch (Exception e) {
            throw new ServletException("Failed to create EntityManagerFactory", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("MarkLessonCompleteServlet called");

        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            String lessonIdParam = request.getParameter("lessonId");
            if (lessonIdParam == null || lessonIdParam.trim().isEmpty()) {
                System.err.println("Lesson ID parameter is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Long lessonId = Long.valueOf(lessonIdParam);
            System.out.println("Marking lesson as completed: " + lessonId);

            // Get current student from session
            Student student = getCurrentStudent(request);
            if (student == null) {
                System.err.println("No student found in session");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            System.out.println("Student ID: " + student.getId());

            em = emf.createEntityManager();
            transaction = em.getTransaction();

            // Get the lesson to find the course
            Lesson lesson = em.find(Lesson.class, lessonId);
            if (lesson == null) {
                System.err.println("Lesson not found with ID: " + lessonId);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            System.out.println("Lesson found: " + lesson.getTitle());

            // Get enrollment for this course and student
            Long courseId = lesson.getSection().getCourse().getId();
            Enrollment enrollment = getEnrollment(em, courseId, student.getId());
            if (enrollment == null) {
                System.err.println("No enrollment found for student " + student.getId() + " in course " + courseId);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            System.out.println("Enrollment found: " + enrollment.getId());

            transaction.begin();

            // Mark lesson as completed
            markLessonAsCompleted(em, lessonId, enrollment.getId());

            // Update enrollment progress
            updateEnrollmentProgress(em, enrollment.getId());

            transaction.commit();

            System.out.println("Lesson marked as completed successfully");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (NumberFormatException e) {
            System.err.println("Invalid lesson ID format: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error in MarkLessonCompleteServlet: " + e.getMessage());
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private Student getCurrentStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Try different session attribute names
        Object userObj = session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        System.out.println("Session user: " + userObj);
        System.out.println("Session role: " + role);

        if (userObj instanceof Student) {
            return (Student) userObj;
        } else if (userObj instanceof com.mycompany.elearning.entities.Utilisateurs.User && "STUDENT".equalsIgnoreCase(role)) {
            // If it's a general User with student role, convert it
            com.mycompany.elearning.entities.Utilisateurs.User user =
                    (com.mycompany.elearning.entities.Utilisateurs.User) userObj;

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                Student student = em.find(Student.class, user.getId());
                if (student != null) {
                    // Store in session for future requests
                    session.setAttribute("student", student);
                    return student;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (em != null) em.close();
            }
        }

        return null;
    }

    private Enrollment getEnrollment(EntityManager em, Long courseId, Long studentId) {
        try {
            TypedQuery<Enrollment> query = em.createQuery(
                    "SELECT e FROM Enrollment e " +
                            "WHERE e.course.id = :courseId AND e.student.id = :studentId",
                    Enrollment.class
            );
            query.setParameter("courseId", courseId);
            query.setParameter("studentId", studentId);

            List<Enrollment> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            System.err.println("Error getting enrollment: " + e.getMessage());
            return null;
        }
    }

    private void markLessonAsCompleted(EntityManager em, Long lessonId, Long enrollmentId) {
        // Check if progress already exists
        TypedQuery<LessonProgress> query = em.createQuery(
                "SELECT lp FROM LessonProgress lp " +
                        "WHERE lp.lesson.id = :lessonId AND lp.enrollment.id = :enrollmentId",
                LessonProgress.class
        );
        query.setParameter("lessonId", lessonId);
        query.setParameter("enrollmentId", enrollmentId);

        List<LessonProgress> results = query.getResultList();
        LessonProgress progress;

        if (results.isEmpty()) {
            // Create new progress record
            progress = new LessonProgress();
            Lesson lesson = em.find(Lesson.class, lessonId);
            Enrollment enrollment = em.find(Enrollment.class, enrollmentId);

            progress.setLesson(lesson);
            progress.setEnrollment(enrollment);
            progress.setIsCompleted(true);

            em.persist(progress);
            System.out.println("Created new lesson progress record");
        } else {
            // Update existing progress record
            progress = results.get(0);
            progress.setIsCompleted(true);
            em.merge(progress);
            System.out.println("Updated existing lesson progress record");
        }
    }

    private void updateEnrollmentProgress(EntityManager em, Long enrollmentId) {
        try {
            // Count total lessons and completed lessons for this enrollment
            TypedQuery<Long> totalLessonsQuery = em.createQuery(
                    "SELECT COUNT(l) FROM Lesson l " +
                            "JOIN l.section s " +
                            "JOIN s.course c " +
                            "JOIN Enrollment e ON e.course.id = c.id " +
                            "WHERE e.id = :enrollmentId",
                    Long.class
            );
            totalLessonsQuery.setParameter("enrollmentId", enrollmentId);
            Long totalLessons = totalLessonsQuery.getSingleResult();

            TypedQuery<Long> completedLessonsQuery = em.createQuery(
                    "SELECT COUNT(lp) FROM LessonProgress lp " +
                            "WHERE lp.enrollment.id = :enrollmentId AND lp.isCompleted = true",
                    Long.class
            );
            completedLessonsQuery.setParameter("enrollmentId", enrollmentId);
            Long completedLessons = completedLessonsQuery.getSingleResult();

            // Calculate progress percentage
            float progressPercentage = 0.0f;
            if (totalLessons > 0) {
                progressPercentage = (completedLessons * 100.0f) / totalLessons;
            }

            System.out.println("Progress: " + completedLessons + "/" + totalLessons + " = " + progressPercentage + "%");

            // Update enrollment progress
            Enrollment enrollment = em.find(Enrollment.class, enrollmentId);
            if (enrollment != null) {
                enrollment.setProgress(progressPercentage);
                em.merge(enrollment);
                System.out.println("Updated enrollment progress to: " + progressPercentage + "%");
            }
        } catch (Exception e) {
            System.err.println("Error updating enrollment progress: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}