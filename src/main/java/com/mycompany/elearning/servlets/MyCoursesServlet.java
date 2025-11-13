package com.mycompany.elearning.servlets;

import com.mycompany.elearning.dao.EnrollmentDao;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/my-courses")
public class MyCoursesServlet extends HttpServlet {
    private EnrollmentDao enrollmentDao;
    private EntityManager em;

    @Override
    public void init() throws ServletException {
        super.init();
        em = Persistence.createEntityManagerFactory("elearningPU").createEntityManager();
        enrollmentDao = new EnrollmentDao(em);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Vérifier si l'utilisateur est connecté et est un étudiant
        User user = (User) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (user == null || !"STUDENT".equals(role)) {
            session.setAttribute("error", "Vous devez être connecté en tant qu'étudiant pour accéder à vos cours.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // Convertir User en Student
            Student student = convertUserToStudent(user);

            // Récupérer toutes les inscriptions de l'étudiant
            List<Enrollment> enrollments = enrollmentDao.findByStudentId(student.getId());

            // Créer une liste de DTO pour afficher les cours avec leurs informations de progression
            List<EnrolledCourseInfo> enrolledCourses = new ArrayList<>();
            for (Enrollment enrollment : enrollments) {
                Course course = enrollment.getCourse();
                EnrolledCourseInfo courseInfo = new EnrolledCourseInfo();
                courseInfo.setCourseId(course.getId());
                courseInfo.setCourseTitle(course.getTitle());
                courseInfo.setCourseDescription(course.getDescription());
                courseInfo.setLevel(course.getLevel());
                courseInfo.setTeacherName(course.getTeacher() != null ? 
                    course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName() : "Non assigné");
                courseInfo.setProgress(enrollment.getProgress() != null ? enrollment.getProgress() : 0.0f);
                // Convertir LocalDateTime en Date pour le formatage JSP
                LocalDateTime enrollmentDate = enrollment.getEnrollmentDate();
                Date enrollmentDateAsDate = enrollmentDate != null ? 
                    Date.from(enrollmentDate.atZone(ZoneId.systemDefault()).toInstant()) : null;
                courseInfo.setEnrollmentDate(enrollmentDateAsDate);
                courseInfo.setIsCompleted(enrollment.getIsCompleted() != null ? enrollment.getIsCompleted() : false);
                enrolledCourses.add(courseInfo);
            }

            req.setAttribute("enrolledCourses", enrolledCourses);
            req.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
            req.setAttribute("userRole", role);

            req.getRequestDispatcher("/my-courses.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Erreur lors de la récupération de vos cours: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/course");
        }
    }

    private Student convertUserToStudent(User user) {
        if (user instanceof Student) {
            return (Student) user;
        }

        // Si ce n'est pas un Student, créez-en un avec les mêmes données
        Student student = new Student();
        student.setId(user.getId());
        student.setFirstName(user.getFirstName());
        student.setLastName(user.getLastName());
        student.setEmail(user.getEmail());

        return student;
    }

    // Classe interne pour transporter les informations de cours inscrits
    public static class EnrolledCourseInfo {
        private Long courseId;
        private String courseTitle;
        private String courseDescription;
        private String level;
        private String teacherName;
        private Float progress;
        private Date enrollmentDate;
        private Boolean isCompleted;

        // Getters et Setters
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }

        public String getCourseTitle() { return courseTitle; }
        public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

        public String getCourseDescription() { return courseDescription; }
        public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public String getTeacherName() { return teacherName; }
        public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

        public Float getProgress() { return progress; }
        public void setProgress(Float progress) { this.progress = progress; }

        public Date getEnrollmentDate() { return enrollmentDate; }
        public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate = enrollmentDate; }

        public Boolean getIsCompleted() { return isCompleted; }
        public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
    }
}

