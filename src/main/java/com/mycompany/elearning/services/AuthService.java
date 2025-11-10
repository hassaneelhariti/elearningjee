//package com.mycompany.elearning.services;
//
//import com.mycompany.elearning.dao.UserDAO;
//import com.mycompany.elearning.entities.Utilisateurs.User;
//import com.mycompany.elearning.utils.JWTUtil;
//import java.util.Optional;
//
//public class AuthService {
//
//    private final UserDAO userDAO;
//    private final JWTUtil jwtUtil;
//
//    public AuthService(UserDAO userDAO, JWTUtil jwtUtil) {
//        this.userDAO = userDAO;
//        this.jwtUtil = jwtUtil;
//    }
//
//    public AuthResult authenticate(String email, String password) {
//        try {
//            // 1. Trouver l'utilisateur par email
//            Optional<User> userOpt = userDAO.findByEmail(email);
//            if (userOpt.isEmpty()) {
//                return AuthResult.error("Utilisateur non trouvé");
//            }
//
//            User user = userOpt.get();
//
//            // 2. Vérifier le mot de passe
//            if (!user.getPassword().equals(password)) {
//                return AuthResult.error("Mot de passe incorrect");
//            }
//
//            // 3. Déterminer le rôle et générer le token
//            String role = determineUserRole(user);
//            String token = jwtUtil.generateToken(user.getUsername(), role, user.getId());
//
//            return AuthResult.success(token, user, role);
//
//        } catch (Exception e) {
//            return AuthResult.error("Erreur d'authentification: " + e.getMessage());
//        }
//    }
//
//    private String determineUserRole(User user) {
//        if (user instanceof com.mycompany.elearning.entities.Utilisateurs.Admin) {
//            return "ADMIN";
//        } else if (user instanceof com.mycompany.elearning.entities.Utilisateurs.Teacher) {
//            return "TEACHER";
//        } else if (user instanceof com.mycompany.elearning.entities.Utilisateurs.Student) {
//            return "STUDENT";
//        }
//        return "USER";
//    }
//
//    // Classe pour retourner le résultat de l'authentification
//    public static class AuthResult {
//        private final boolean success;
//        private final String token;
//        private final User user;
//        private final String role;
//        private final String error;
//
//        private AuthResult(boolean success, String token, User user, String role, String error) {
//            this.success = success;
//            this.token = token;
//            this.user = user;
//            this.role = role;
//            this.error = error;
//        }
//
//        public static AuthResult success(String token, User user, String role) {
//            return new AuthResult(true, token, user, role, null);
//        }
//
//        public static AuthResult error(String error) {
//            return new AuthResult(false, null, null, null, error);
//        }
//
//        // Getters
//        public boolean isSuccess() { return success; }
//        public String getToken() { return token; }
//        public User getUser() { return user; }
//        public String getRole() { return role; }
//        public String getError() { return error; }
//    }
//}