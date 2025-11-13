package com.mycompany.elearning.servlets.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Si confirmation demandée, afficher la page de confirmation
        if ("confirm".equals(action)) {
            request.getRequestDispatcher("/logout-confirm.jsp").forward(request, response);
            return;
        }

        // Sinon, procéder à la déconnexion directe
        performLogout(request, response);
    }

    private void performLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        String username = "Utilisateur";

        if (session != null) {
            // Récupérer le nom d'utilisateur pour le message
            Object user = session.getAttribute("user");
            if (user instanceof com.mycompany.elearning.entities.Utilisateurs.User) {
                username = ((com.mycompany.elearning.entities.Utilisateurs.User) user).getFirstName();
            }

            // Invalider la session
            session.invalidate();
            System.out.println("✅ Déconnexion de: " + username);
        }

        // Rediriger vers login avec message personnalisé
        response.sendRedirect("login?message=Au revoir " + username + " ! Vous êtes déconnecté.");
    }
}