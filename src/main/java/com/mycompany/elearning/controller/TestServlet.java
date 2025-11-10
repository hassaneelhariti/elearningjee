package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Admin;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.services.UserService;
import com.mycompany.elearning.services.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet de test pour vérifier Hibernate
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    
    private UserService userService = new UserService();
    private CourseService courseService = new CourseService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Test Hibernate</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Test de la Configuration Hibernate</h1>");
            
            // Test 1: Créer un admin
            out.println("<h2>Test 1: Création d'un administrateur</h2>");
            try {
                Admin admin = new Admin("admin", "admin@elearning.com", "admin123", "Admin", "System");
                admin = userService.registerAdmin(admin);
                out.println("<p>✓ Admin créé avec succès! ID: " + admin.getId() + "</p>");
            } catch (Exception e) {
                out.println("<p>✗ Erreur: " + e.getMessage() + "</p>");
                e.printStackTrace(out);
            }
            
            // Test 2: Créer un enseignant
            out.println("<h2>Test 2: Création d'un enseignant</h2>");
            try {
                Teacher teacher = new Teacher("prof_ali", "ali@elearning.com", "pass123", "Ali", "Bennani", "Expert en Java");
                teacher = userService.registerTeacher(teacher);
                out.println("<p>✓ Enseignant créé avec succès! ID: " + teacher.getId() + "</p>");
                
                // Test 3: Créer un cours
                out.println("<h2>Test 3: Création d'un cours</h2>");
                Course course = new Course("Introduction à Java", 
                    "Apprendre les bases de Java", "Beginner", teacher);
                course = courseService.createCourse(course);
                out.println("<p>✓ Cours créé avec succès! ID: " + course.getId() + "</p>");
                
            } catch (Exception e) {
                out.println("<p>✗ Erreur: " + e.getMessage() + "</p>");
                e.printStackTrace(out);
            }
            
            // Test 4: Créer un étudiant
            out.println("<h2>Test 4: Création d'un étudiant</h2>");
            try {
                Student student = new Student("student1", "student@elearning.com", "pass123", "Ahmed", "Alami");
                student = userService.registerStudent(student);
                out.println("<p>✓ Étudiant créé avec succès! ID: " + student.getId() + "</p>");
            } catch (Exception e) {
                out.println("<p>✗ Erreur: " + e.getMessage() + "</p>");
                e.printStackTrace(out);
            }
            
            // Test 5: Lister tous les cours
            out.println("<h2>Test 5: Liste des cours</h2>");
            try {
                var courses = courseService.getAllCourses();
                out.println("<p>Nombre de cours: " + courses.size() + "</p>");
                out.println("<ul>");
                for (Course c : courses) {
                    out.println("<li>" + c.getTitle() + " - " + c.getLevel() + "</li>");
                }
                out.println("</ul>");
            } catch (Exception e) {
                out.println("<p>✗ Erreur: " + e.getMessage() + "</p>");
                e.printStackTrace(out);
            }
            
            out.println("<hr>");
            out.println("<p><a href='" + request.getContextPath() + "/'>Retour à l'accueil</a></p>");
            out.println("</body>");
            out.println("</html>");
            
        } finally {
            out.close();
        }
    }
}