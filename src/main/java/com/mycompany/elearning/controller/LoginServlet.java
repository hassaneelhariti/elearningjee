
package com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.entities.Utilisateurs.Admin;
import com.mycompany.elearning.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet pour gérer la connexion
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private UserService userService = new UserService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        
        HttpSession session = request.getSession();
        
        if (userType == null || userType.isEmpty()) {
            request.setAttribute("error", "Veuillez sélectionner un type d'utilisateur");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        switch (userType) {
            case "student":
                Student student = userService.loginStudent(email, password);
                if (student != null) {
                    session.setAttribute("user", student);
                    session.setAttribute("userType", "student");
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                } else {
                    request.setAttribute("error", "Email ou mot de passe incorrect");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                }
                break;
                
            case "teacher":
                Teacher teacher = userService.loginTeacher(email, password);
                if (teacher != null) {
                    session.setAttribute("user", teacher);
                    session.setAttribute("userType", "teacher");
                    response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
                } else {
                    request.setAttribute("error", "Email ou mot de passe incorrect");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                }
                break;
                
            case "admin":
                Admin admin = userService.loginAdmin(email, password);
                if (admin != null) {
                    session.setAttribute("user", admin);
                    session.setAttribute("userType", "admin");
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    request.setAttribute("error", "Email ou mot de passe incorrect");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                }
                break;
                
            default:
                request.setAttribute("error", "Type d'utilisateur invalide");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}