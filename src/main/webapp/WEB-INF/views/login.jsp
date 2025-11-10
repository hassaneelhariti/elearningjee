<%-- WEB-INF/views/login.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion - E-Learning</title>
</head>
<body>
    <h1>Connexion</h1>
    
    <c:if test="${not empty error}">
        <p><strong>Erreur:</strong> ${error}</p>
    </c:if>
    
    <c:if test="${not empty success}">
        <p><strong>Succès:</strong> ${success}</p>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/login" method="post">
        <table>
            <tr>
                <td><label for="email">Email:</label></td>
                <td><input type="email" id="email" name="email" required /></td>
            </tr>
            <tr>
                <td><label for="password">Mot de passe:</label></td>
                <td><input type="password" id="password" name="password" required /></td>
            </tr>
            <tr>
                <td><label for="userType">Type d'utilisateur:</label></td>
                <td>
                    <select id="userType" name="userType" required>
                        <option value="">-- Sélectionner --</option>
                        <option value="student">Étudiant</option>
                        <option value="teacher">Enseignant</option>
                        <option value="admin">Administrateur</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Se connecter" />
                </td>
            </tr>
        </table>
    </form>
    
    <p>
        <a href="${pageContext.request.contextPath}/register">Créer un compte étudiant</a> |
        <a href="${pageContext.request.contextPath}/courses">Voir les cours</a>
    </p>
</body>
</html>