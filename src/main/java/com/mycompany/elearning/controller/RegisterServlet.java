package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour l'inscription des étudiants
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    private UserService userService = new UserService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        // Validation
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        // Vérifier si l'email existe déjà
        if (userService.getStudentByEmail(email) != null) {
            request.setAttribute("error", "Cet email est déjà utilisé");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        // Créer le nouvel étudiant
        Student student = new Student(username, email, password, firstName, lastName);
        userService.registerStudent(student);
        
        request.setAttribute("success", "Inscription réussie! Vous pouvez maintenant vous connecter");
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}