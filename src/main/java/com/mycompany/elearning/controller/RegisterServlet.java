package com.mycompany.elearning.controller;

import com.mycompany.elearning.services.UserService;
import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userType = request.getParameter("userType");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        if (userService.isEmailExists(email)) {
            request.setAttribute("error", "Cet email existe déjà");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        if (userService.isUsernameExists(username)) {
            request.setAttribute("error", "Ce nom d'utilisateur existe déjà");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        if ("student".equals(userType)) {
            Student student = new Student(username, email, password, firstName, lastName);
            userService.registerStudent(student);
        } else if ("teacher".equals(userType)) {
            String bio = request.getParameter("bio");
            Teacher teacher = new Teacher(username, email, password, firstName, lastName, bio);
            userService.registerTeacher(teacher);
        }
        
        response.sendRedirect(request.getContextPath() + "/login?registered=true");
    }
}