package com.mycompany.elearning.servlets.dashboard;

import com.mycompany.elearning.dao.CourseDao;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Admin;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.User;
import com.mycompany.elearning.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private EntityManager em;
    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        em = Persistence.createEntityManagerFactory("elearningPU").createEntityManager();
        sessionFactory = HibernateUtil.getSessionFactory();
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

        if (!"ADMIN".equals(role)) {
            response.sendRedirect("login");
            return;
        }

        try {
            // Statistiques globales
            Long totalUsers = countUsers();
            Long totalStudents = countUsersByType(Student.class);
            Long totalTeachers = countUsersByType(Teacher.class);
            Long totalAdmins = countUsersByType(Admin.class);
            
            CourseDao courseDao = new CourseDao(em);
            List<Course> allCourses = courseDao.findAll();
            Long totalCourses = (long) allCourses.size();
            
            Long publishedCourses = allCourses.stream()
                    .filter(Course::getIsPublished)
                    .count();
            Long draftCourses = totalCourses - publishedCourses;
            
            Long totalEnrollments = countTotalEnrollments();
            
            // Répartition par niveau
            Map<String, Long> coursesByLevel = getCoursesByLevel(allCourses);
            
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("totalTeachers", totalTeachers);
            request.setAttribute("totalAdmins", totalAdmins);
            request.setAttribute("totalCourses", totalCourses);
            request.setAttribute("publishedCourses", publishedCourses);
            request.setAttribute("draftCourses", draftCourses);
            request.setAttribute("totalEnrollments", totalEnrollments);
            request.setAttribute("coursesByLevel", coursesByLevel);
            request.setAttribute("userName", user.getFirstName() + " " + user.getLastName());

            request.getRequestDispatcher("/dashboard/admin-dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des statistiques: " + e.getMessage());
            request.getRequestDispatcher("/dashboard/admin-dashboard.jsp").forward(request, response);
        }
    }

    private Long countUsers() {
        Session session = sessionFactory.openSession();
        try {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM User", Long.class);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    private Long countUsersByType(Class<? extends User> userType) {
        Session session = sessionFactory.openSession();
        try {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(*) FROM " + userType.getSimpleName(), Long.class);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    private Long countTotalEnrollments() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Enrollment e", Long.class);
        return query.getSingleResult();
    }

    private Map<String, Long> getCoursesByLevel(List<Course> courses) {
        Map<String, Long> levelCount = new HashMap<>();
        levelCount.put("Beginner", 0L);
        levelCount.put("Intermediate", 0L);
        levelCount.put("Advanced", 0L);
        levelCount.put("Autre", 0L);
        
        for (Course course : courses) {
            String level = course.getLevel();
            if (level == null || level.isEmpty()) {
                levelCount.put("Autre", levelCount.get("Autre") + 1);
            } else {
                String normalizedLevel = level.trim();
                if (levelCount.containsKey(normalizedLevel)) {
                    levelCount.put(normalizedLevel, levelCount.get(normalizedLevel) + 1);
                } else {
                    levelCount.put("Autre", levelCount.get("Autre") + 1);
                }
            }
        }
        return levelCount;
    }

    @Override
    public void destroy() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
