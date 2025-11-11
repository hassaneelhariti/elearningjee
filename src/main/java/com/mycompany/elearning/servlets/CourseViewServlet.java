package com.mycompany.elearning.servlets;

import com.mycompany.elearning.entities.Contenu.*;
import com.mycompany.elearning.entities.EnrollementProgression.LessonProgress;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/course-view")
public class CourseViewServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        try {
            System.out.println("Initializing EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("elearningPU");
            System.out.println("EntityManagerFactory created successfully");
        } catch (Exception e) {
            System.err.println("Failed to create EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Failed to create EntityManagerFactory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("CourseViewServlet called with parameters: " + request.getQueryString());

        EntityManager em = null;
        try {
            // Check if emf is initialized
            if (emf == null) {
                System.err.println("EntityManagerFactory is null, reinitializing...");
                init();
                if (emf == null) {
                    throw new ServletException("EntityManagerFactory is still null after reinitialization");
                }
            }

            em = emf.createEntityManager();
            System.out.println("EntityManager created successfully");

            // Get courseId parameter
            String courseIdParam = request.getParameter("courseId");
            if (courseIdParam == null || courseIdParam.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID is required");
                return;
            }

            Long courseId = Long.valueOf(courseIdParam);
            Long lessonId = request.getParameter("lessonId") != null ?
                    Long.valueOf(request.getParameter("lessonId")) : null;

            System.out.println("Loading course ID: " + courseId + ", lesson ID: " + lessonId);

            // Get current student ID
            Long studentId = getCurrentStudentId(request);
            if (studentId == null) {
                System.out.println("No student found in session, redirecting to login");
                response.sendRedirect("login");
                return;
            }

            System.out.println("Current student ID: " + studentId);

            // Get course and sections
            Course course = getCourseById(em, courseId);
            if (course == null) {
                System.err.println("Course not found with ID: " + courseId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
                return;
            }

            System.out.println("Course found: " + course.getTitle());

            List<Section> sections = getSectionsByCourseId(em, courseId);
            System.out.println("Found " + sections.size() + " sections");

            // Get student's enrollment for this course
            Enrollment enrollment = getEnrollment(em, courseId, studentId);
            if (enrollment == null) {
                System.err.println("No enrollment found for student " + studentId + " in course " + courseId);
                response.sendRedirect("courses");
                return;
            }

            System.out.println("Enrollment found: " + enrollment.getId());

            // Get student progress based on enrollment
            Map<Long, Boolean> lessonProgressMap = getLessonProgressMap(em, enrollment.getId());
            System.out.println("Lesson progress map size: " + lessonProgressMap.size());

            // Calculate progress percentage
            int totalLessons = sections.stream()
                    .mapToInt(s -> s.getLessons() != null ? s.getLessons().size() : 0)
                    .sum();
            int completedLessons = (int) lessonProgressMap.values()
                    .stream()
                    .filter(Boolean::booleanValue)
                    .count();

            float progressPercentage;
            if (totalLessons > 0) {
                progressPercentage = (completedLessons * 100.0f) / totalLessons;
            } else {
                progressPercentage = enrollment.getProgress() != null ? enrollment.getProgress() : 0.0f;
            }

            System.out.println("Progress: " + completedLessons + "/" + totalLessons + " = " + progressPercentage + "%");

            // Set attributes
            request.setAttribute("course", course);
            request.setAttribute("sections", sections);
            request.setAttribute("lessonProgressMap", lessonProgressMap);
            request.setAttribute("progressPercentage", Math.round(progressPercentage));
            request.setAttribute("totalLessons", totalLessons);
            request.setAttribute("enrollment", enrollment);

            // Handle current lesson
            if (lessonId != null) {
                Lesson currentLesson = getLessonById(em, lessonId);
                if (currentLesson != null) {
                    System.out.println("Current lesson: " + currentLesson.getTitle());

                    Lesson prevLesson = getPreviousLesson(em, currentLesson);
                    Lesson nextLesson = getNextLesson(em, currentLesson);
                    Lesson firstLesson = getFirstLesson(em, courseId);

                    // Get lesson progress for current lesson
                    LessonProgress currentLessonProgress = getLessonProgress(em, enrollment.getId(), lessonId);
                    request.setAttribute("currentLessonProgress", currentLessonProgress);
                    request.setAttribute("currentLesson", currentLesson);
                    request.setAttribute("prevLesson", prevLesson);
                    request.setAttribute("nextLesson", nextLesson);
                    request.setAttribute("firstLesson", firstLesson);

                    System.out.println("Previous lesson: " + (prevLesson != null ? prevLesson.getTitle() : "null"));
                    System.out.println("Next lesson: " + (nextLesson != null ? nextLesson.getTitle() : "null"));
                } else {
                    System.err.println("Lesson not found with ID: " + lessonId);
                }
            }

            // Forward to JSP
            System.out.println("Forwarding to course-view.jsp");
            request.getRequestDispatcher("/course-view.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("Invalid course ID format: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID format");
        } catch (Exception e) {
            System.err.println("Error in CourseViewServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading course: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("EntityManager closed");
            }
        }
    }

    // Helper methods (same implementation as before)
    private Course getCourseById(EntityManager em, Long courseId) {
        return em.find(Course.class, courseId);
    }

    private List<Section> getSectionsByCourseId(EntityManager em, Long courseId) {
        try {
            TypedQuery<Section> query = em.createQuery(
                    "SELECT s FROM Section s WHERE s.course.id = :courseId ORDER BY s.orderIndex",
                    Section.class
            );
            query.setParameter("courseId", courseId);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error getting sections: " + e.getMessage());
            return new ArrayList<>();
        }
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

    private Map<Long, Boolean> getLessonProgressMap(EntityManager em, Long enrollmentId) {
        Map<Long, Boolean> progressMap = new HashMap<>();
        try {
            TypedQuery<LessonProgress> query = em.createQuery(
                    "SELECT lp FROM LessonProgress lp " +
                            "WHERE lp.enrollment.id = :enrollmentId",
                    LessonProgress.class
            );
            query.setParameter("enrollmentId", enrollmentId);
            List<LessonProgress> lessonProgresses = query.getResultList();

            for (LessonProgress progress : lessonProgresses) {
                progressMap.put(progress.getLesson().getId(), progress.getIsCompleted());
            }
        } catch (Exception e) {
            System.err.println("Error getting lesson progress: " + e.getMessage());
        }
        return progressMap;
    }

    private LessonProgress getLessonProgress(EntityManager em, Long enrollmentId, Long lessonId) {
        try {
            TypedQuery<LessonProgress> query = em.createQuery(
                    "SELECT lp FROM LessonProgress lp " +
                            "WHERE lp.enrollment.id = :enrollmentId AND lp.lesson.id = :lessonId",
                    LessonProgress.class
            );
            query.setParameter("enrollmentId", enrollmentId);
            query.setParameter("lessonId", lessonId);

            List<LessonProgress> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            System.err.println("Error getting specific lesson progress: " + e.getMessage());
            return null;
        }
    }

    private Lesson getLessonById(EntityManager em, Long lessonId) {
        return em.find(Lesson.class, lessonId);
    }

    private Lesson getPreviousLesson(EntityManager em, Lesson currentLesson) {
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l " +
                            "WHERE l.section.id = :sectionId AND l.orderIndex < :currentOrder " +
                            "ORDER BY l.orderIndex DESC",
                    Lesson.class
            );
            query.setParameter("sectionId", currentLesson.getSection().getId());
            query.setParameter("currentOrder", currentLesson.getOrderIndex());
            query.setMaxResults(1);

            List<Lesson> results = query.getResultList();
            if (!results.isEmpty()) {
                return results.get(0);
            }
        } catch (Exception e) {
            System.err.println("Error getting previous lesson: " + e.getMessage());
        }
        return null;
    }

    private Lesson getNextLesson(EntityManager em, Lesson currentLesson) {
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l " +
                            "WHERE l.section.id = :sectionId AND l.orderIndex > :currentOrder " +
                            "ORDER BY l.orderIndex ASC",
                    Lesson.class
            );
            query.setParameter("sectionId", currentLesson.getSection().getId());
            query.setParameter("currentOrder", currentLesson.getOrderIndex());
            query.setMaxResults(1);

            List<Lesson> results = query.getResultList();
            if (!results.isEmpty()) {
                return results.get(0);
            }
        } catch (Exception e) {
            System.err.println("Error getting next lesson: " + e.getMessage());
        }
        return null;
    }

    private Lesson getFirstLesson(EntityManager em, Long courseId) {
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l " +
                            "JOIN l.section s " +
                            "WHERE s.course.id = :courseId " +
                            "ORDER BY s.orderIndex ASC, l.orderIndex ASC",
                    Lesson.class
            );
            query.setParameter("courseId", courseId);
            query.setMaxResults(1);

            List<Lesson> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            System.err.println("Error getting first lesson: " + e.getMessage());
            return null;
        }
    }

    private Long getCurrentStudentId(HttpServletRequest request) {
        HttpSession session = request.getSession();

        System.out.println("=== DEBUG SESSION ===");
        java.util.Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            System.out.println(name + ": " + session.getAttribute(name));
        }

        Object userObj = session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (userObj != null && "STUDENT".equalsIgnoreCase(role)) {
            // Votre objet User a un ID qui correspond à l'ID dans la table student
            if (userObj instanceof com.mycompany.elearning.entities.Utilisateurs.User) {
                Long userId = ((com.mycompany.elearning.entities.Utilisateurs.User) userObj).getId();
                System.out.println("User ID from session: " + userId);

                // Vérifier que cet ID existe dans la table student
                EntityManager em = null;
                try {
                    em = emf.createEntityManager();
                    Student student = em.find(Student.class, userId);
                    if (student != null) {
                        System.out.println("Student found with ID: " + userId);
                        return userId;
                    } else {
                        System.out.println("No student record found for user ID: " + userId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (em != null) em.close();
                }
            }
        }

        return null;
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed");
        }
    }
}