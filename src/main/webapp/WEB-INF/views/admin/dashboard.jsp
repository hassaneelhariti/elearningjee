<%-- WEB-INF/views/admin/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin - E-Learning</title>
</head>
<body>
    <h1>Tableau de Bord Administrateur</h1>
    
    <p>
        Bienvenue <strong>${admin.firstName} ${admin.lastName}</strong> | 
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </p>
    
    <hr>
    
    <h2>Gestion de la Plateforme</h2>
    
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/users">Gérer les utilisateurs</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/courses">Gérer les cours</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/reports">Rapports et statistiques</a></li>
    </ul>
    
    <h2>Statistiques Rapides</h2>
    
    <table border="1">
        <tr>
            <td><strong>Nombre total d'utilisateurs:</strong></td>
            <td>${totalUsers}</td>
        </tr>
        <tr>
            <td><strong>Nombre d'étudiants:</strong></td>
            <td>${totalStudents}</td>
        </tr>
        <tr>
            <td><strong>Nombre d'enseignants:</strong></td>
            <td>${totalTeachers}</td>
        </tr>
        <tr>
            <td><strong>Nombre de cours:</strong></td>
            <td>${totalCourses}</td>
        </tr>
        <tr>
            <td><strong>Cours publiés:</strong></td>
            <td>${publishedCourses}</td>
        </tr>
    </table>
</body>
</html>