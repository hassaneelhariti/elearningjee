package com.mycompany.elearning.servlets;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.dao.EnrollmentDao;
import com.mycompany.elearning.dao.TeacherDao;
import com.mycompany.elearning.dto.CourseDto;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.EnrollementProgression.Enrollment;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.User;
import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.services.EnrollmentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private EntityManager em;

    public CourseServlet(){}

    @Override
    public void init() throws ServletException {
        super.init();
        em = Persistence.createEntityManagerFactory("elearningPU").createEntityManager();

        CourseDao courseDao = new CourseDao(em);
        TeacherDao teacherDao = new TeacherDao(em);
        EnrollmentDao enrollmentDao = new EnrollmentDao(em);

        courseService = new CourseService(courseDao, teacherDao);

        // CORRECTION ICI : Passer courseDao au EnrollmentService
        enrollmentService = new EnrollmentService(enrollmentDao, courseDao);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Vérifier si l'utilisateur est connecté et est un étudiant
        User user = (User) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (user == null || !"STUDENT".equals(role)) {
            session.setAttribute("error", "Vous devez être connecté en tant qu'étudiant pour accéder aux cours.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<CourseDto> availableCourses = courseService.getAllCourses();
        req.setAttribute("courses", availableCourses);

        // Ajouter les informations utilisateur à la requête
        req.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
        req.setAttribute("userRole", role);

        req.getRequestDispatcher("/course.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Récupérer l'utilisateur connecté depuis la session
        User user = (User) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (user == null || !"STUDENT".equals(role)) {
            session.setAttribute("error", "Vous devez être connecté en tant qu'étudiant pour vous inscrire à un cours.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");

        if ("enroll".equals(action)) {
            enrollStudentInCourse(req, resp, user);
        } else {
            resp.sendRedirect(req.getContextPath() + "/course");
        }
    }

    private void enrollStudentInCourse(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HttpSession session = req.getSession();

        try {
            String courseName = req.getParameter("courseName");

            System.out.println("DEBUG - Nom du cours reçu: " + courseName);

            // Récupérer le cours par son nom
            Course selectedCourse = courseService.findCourseByName(courseName);

            if (selectedCourse != null) {
                System.out.println("DEBUG - Cours trouvé: " + selectedCourse.getTitle() + " (ID: " + selectedCourse.getId() + ")");

                // Convertir User en Student
                Student student = convertUserToStudent(user);

                System.out.println("DEBUG - Étudiant: " + student.getFirstName() + " " + student.getLastName() + " (ID: " + student.getId() + ")");

                // Vérifier si l'étudiant est déjà inscrit à ce cours
                boolean isAlreadyEnrolled = enrollmentService.isStudentEnrolled(student.getId(), selectedCourse.getId());

                if (isAlreadyEnrolled) {
                    session.setAttribute("error", "Vous êtes déjà inscrit au cours: " + courseName);
                    System.out.println("DEBUG - Étudiant déjà inscrit");
                } else {
                    // Créer une nouvelle inscription
                    Enrollment enrollment = enrollmentService.enrollStudentInCourse(student, selectedCourse.getId());

                    if (enrollment != null) {
                        session.setAttribute("success", "Félicitations ! Vous êtes maintenant inscrit au cours: " + courseName);
                        System.out.println("DEBUG - Inscription réussie");
                    } else {
                        session.setAttribute("error", "Erreur lors de l'inscription au cours.");
                        System.out.println("DEBUG - Erreur lors de l'inscription");
                    }
                }
            } else {
                session.setAttribute("error", "Cours non trouvé: " + courseName);
                System.out.println("DEBUG - Cours non trouvé: " + courseName);

                // DEBUG: Lister tous les cours disponibles pour vérifier
                List<CourseDto> allCourses = courseService.getAllCourses();
                System.out.println("DEBUG - Cours disponibles dans la base:");
                for (CourseDto course : allCourses) {
                    System.out.println("  - " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                }
            }

        } catch (Exception e) {
            session.setAttribute("error", "Erreur technique: " + e.getMessage());
            System.out.println("DEBUG - Exception: " + e.getMessage());
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/course");
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
}