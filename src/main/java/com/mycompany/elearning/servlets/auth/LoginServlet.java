package com.mycompany.elearning.servlets.auth;

import com.mycompany.elearning.dao.UserDAO;
import com.mycompany.elearning.services.AuthService;
import com.mycompany.elearning.utils.HibernateUtil;
import com.mycompany.elearning.utils.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {

        UserDAO userDAO = new UserDAO(HibernateUtil.getSessionFactory());
        JWTUtil jwtUtil = new JWTUtil();
        authService = new AuthService(userDAO, jwtUtil);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showLoginPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        AuthService.AuthResult result = authService.authenticate(email, password);

        if (result.isSuccess()) {
            handleSuccessfulLogin(request, response, result);
        } else {
            handleFailedLogin(request, response, result.getError());
        }
    }


    private void showLoginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("message", message);
        }
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    private void handleSuccessfulLogin(HttpServletRequest request, HttpServletResponse response,
                                       AuthService.AuthResult result) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("token", result.getToken());
        session.setAttribute("user", result.getUser());
        session.setAttribute("role", result.getRole());

        redirectToDashboard(response, result.getRole());
    }

    private void handleFailedLogin(HttpServletRequest request, HttpServletResponse response,
                                   String error) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    private void redirectToDashboard(HttpServletResponse response, String role) throws IOException {
        switch(role) {
            case "ADMIN": response.sendRedirect("admin-dashboard"); break;
            case "TEACHER": response.sendRedirect("teacher-dashboard"); break;
            case "STUDENT": response.sendRedirect("course"); break;
            default: response.sendRedirect("dashboard");
        }
    }

    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}