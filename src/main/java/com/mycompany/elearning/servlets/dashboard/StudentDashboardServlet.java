package com.mycompany.elearning.servlets.dashboard;

import com.mycompany.elearning.dao.EnrollmentDao;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/student-dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private EntityManager em;

    @Override
    public void init() throws ServletException {
        em = Persistence.createEntityManagerFactory("elearningPU").createEntityManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Vérifier la session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (!"STUDENT".equals(role)) {
            response.sendRedirect("login");
            return;
        }

        try {
            // Convertir User en Student
            Student student = convertUserToStudent(user);
            EnrollmentDao enrollmentDao = new EnrollmentDao(em);

            // Récupérer toutes les inscriptions
            List<Enrollment> enrollments = enrollmentDao.findByStudentId(student.getId());

            // Calculer les statistiques
            int totalCourses = enrollments.size();
            int completedCourses = 0;
            int inProgressCourses = 0;
            float averageProgress = 0.0f;

            // Récupérer les données de performance par cours/leçon
            List<CoursePerformance> coursePerformances = new ArrayList<>();
            Set<Teacher> linkedTeachers = new HashSet<>();
            List<LessonSchedule> upcomingLessons = new ArrayList<>();

            for (Enrollment enrollment : enrollments) {
                Course course = enrollment.getCourse();
                
                if (enrollment.getIsCompleted() != null && enrollment.getIsCompleted()) {
                    completedCourses++;
                } else {
                    inProgressCourses++;
                }
                if (enrollment.getProgress() != null) {
                    averageProgress += enrollment.getProgress();
                }

                // Calculer la performance par cours
                float courseProgress = enrollment.getProgress() != null ? enrollment.getProgress() : 0.0f;
                coursePerformances.add(new CoursePerformance(
                    course.getTitle(),
                    courseProgress
                ));

                // Ajouter les enseignants
                if (course.getTeacher() != null) {
                    linkedTeachers.add(course.getTeacher());
                }

                // Récupérer les leçons pour le calendrier
                if (course.getSections() != null) {
                    for (Section section : course.getSections()) {
                        if (section.getLessons() != null) {
                            for (Lesson lesson : section.getLessons()) {
                                // Simuler des heures pour les leçons (à adapter selon vos besoins)
                                upcomingLessons.add(new LessonSchedule(
                                    lesson.getTitle(),
                                    course.getTitle(),
                                    "10:00",
                                    "11:00",
                                    section.getTitle()
                                ));
                            }
                        }
                    }
                }
            }

            // Trier les performances et prendre les meilleures
            coursePerformances.sort((a, b) -> Float.compare(b.progress, a.progress));
            List<CoursePerformance> topPerformances = coursePerformances.stream()
                .limit(6)
                .collect(Collectors.toList());

            if (totalCourses > 0) {
                averageProgress = averageProgress / totalCourses;
            }

            // Préparer les données pour les graphiques
            request.setAttribute("totalCourses", totalCourses);
            request.setAttribute("completedCourses", completedCourses);
            request.setAttribute("inProgressCourses", inProgressCourses);
            request.setAttribute("averageProgress", Math.round(averageProgress));
            request.setAttribute("enrollments", enrollments);
            request.setAttribute("coursePerformances", topPerformances);
            request.setAttribute("linkedTeachers", new ArrayList<>(linkedTeachers));
            request.setAttribute("upcomingLessons", upcomingLessons.stream().limit(4).collect(Collectors.toList()));
            request.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
            request.setAttribute("userFirstName", user.getFirstName());

            request.getRequestDispatcher("/dashboard/student-dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des statistiques: " + e.getMessage());
            request.getRequestDispatcher("/dashboard/student-dashboard.jsp").forward(request, response);
        }
    }

    private Student convertUserToStudent(User user) {
        if (user instanceof Student) {
            return (Student) user;
        }
        Student student = new Student();
        student.setId(user.getId());
        student.setFirstName(user.getFirstName());
        student.setLastName(user.getLastName());
        student.setEmail(user.getEmail());
        return student;
    }

    @Override
    public void destroy() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    // Classes internes pour transporter les données
    public static class CoursePerformance {
        private String courseName;
        private float progress;

        public CoursePerformance(String courseName, float progress) {
            this.courseName = courseName;
            this.progress = progress;
        }

        public String getCourseName() { return courseName; }
        public float getProgress() { return progress; }
    }

    public static class LessonSchedule {
        private String lessonTitle;
        private String courseTitle;
        private String startTime;
        private String endTime;
        private String sectionTitle;

        public LessonSchedule(String lessonTitle, String courseTitle, String startTime, String endTime, String sectionTitle) {
            this.lessonTitle = lessonTitle;
            this.courseTitle = courseTitle;
            this.startTime = startTime;
            this.endTime = endTime;
            this.sectionTitle = sectionTitle;
        }

        public String getLessonTitle() { return lessonTitle; }
        public String getCourseTitle() { return courseTitle; }
        public String getStartTime() { return startTime; }
        public String getEndTime() { return endTime; }
        public String getSectionTitle() { return sectionTitle; }
    }
}
