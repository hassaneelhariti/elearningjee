package com.mycompany.elearning.servlets.auth;

import com.mycompany.elearning.dao.UserDAO;
import com.mycompany.elearning.entities.Utilisateurs.*;
import com.mycompany.elearning.utils.HibernateUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(HibernateUtil.getSessionFactory());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserRegistrationData data = extractRegistrationData(request);

        try {
            User user = createUserFromData(data);
            userDAO.save(user);

            response.sendRedirect("login?message=Inscription réussie !");

        } catch (Exception e) {
            request.setAttribute("error", "Erreur d'inscription: " + e.getMessage());
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }
    }

    // ✅ EXTRACTION DES DONNÉES
    private UserRegistrationData extractRegistrationData(HttpServletRequest request) {
        return new UserRegistrationData(
                request.getParameter("username"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("role")
        );
    }

    // ✅ CRÉATION DE L'UTILISATEUR
    private User createUserFromData(UserRegistrationData data) {
        return switch(data.role().toUpperCase()) {
            case "STUDENT" -> new Student(data.username(), data.email(), data.password(),
                    data.firstName(), data.lastName());
            case "TEACHER" -> new Teacher(data.username(), data.email(), data.password(),
                    data.firstName(), data.lastName(), "Bio par défaut");
            case "ADMIN" -> new Admin(data.username(), data.email(), data.password(),
                    data.firstName(), data.lastName());
            default -> throw new IllegalArgumentException("Rôle invalide: " + data.role());
        };
    }

    // ✅ RECORD POUR LES DONNÉES D'INSCRIPTION
    private record UserRegistrationData(
            String username, String email, String password,
            String firstName, String lastName, String role
    ) {}
}