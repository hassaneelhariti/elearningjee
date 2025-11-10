<%-- WEB-INF/views/register.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription - E-Learning</title>
</head>
<body>
    <h1>Inscription Étudiant</h1>
    
    <c:if test="${not empty error}">
        <p><strong>Erreur:</strong> ${error}</p>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/register" method="post">
        <table>
            <tr>
                <td><label for="username">Nom d'utilisateur:</label></td>
                <td><input type="text" id="username" name="username" required /></td>
            </tr>
            <tr>
                <td><label for="firstName">Prénom:</label></td>
                <td><input type="text" id="firstName" name="firstName" required /></td>
            </tr>
            <tr>
                <td><label for="lastName">Nom:</label></td>
                <td><input type="text" id="lastName" name="lastName" required /></td>
            </tr>
            <tr>
                <td><label for="email">Email:</label></td>
                <td><input type="email" id="email" name="email" required /></td>
            </tr>
            <tr>
                <td><label for="password">Mot de passe:</label></td>
                <td><input type="password" id="password" name="password" required /></td>
            </tr>
            <tr>
                <td><label for="confirmPassword">Confirmer le mot de passe:</label></td>
                <td><input type="password" id="confirmPassword" name="confirmPassword" required /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="S'inscrire" />
                </td>
            </tr>
        </table>
    </form>
    
    <p>
        <a href="${pageContext.request.contextPath}/login">Déjà inscrit? Se connecter</a>
    </p>
</body>
</html>