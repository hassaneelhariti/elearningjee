<%-- 
    Document   : my-courses
    Created on : Nov 8, 2025, 1:12:49 PM
    Author     : ousam713
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Mes Cours - Enseignant</title>
</head>
<body>
    <h1>Mes Cours</h1>
    
    <div>
        <a href="<c:url value='/teacher/dashboard'/>">← Retour au tableau de bord</a>
        <a href="<c:url value='/teacher/courses/create'/>">➕ Créer un nouveau cours</a>
    </div>

    <c:choose>
        <c:when test="${empty requestScope.courses}">
            <p>Aucun cours créé pour le moment.</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Niveau</th>
                        <th>Date création</th>
                        <th>Statut</th>
                        <th>Étudiants inscrits</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${requestScope.courses}">
                        <tr>
                            <td>${course.title}</td>
                            <td>${course.level}</td>
                            <td>${course.dateCreated}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${course.published}">
                                        <span>✅ Publié</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>📝 Brouillon</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${course.enrollments.size()} étudiants</td>
                            <td>
                                <a href="<c:url value='/teacher/courses/edit?id=${course.id}'/>">Modifier</a>
                                <c:choose>
                                    <c:when test="${course.published}">
                                        <a href="<c:url value='/teacher/courses/unpublish?id=${course.id}'/>">Dépublier</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/teacher/courses/publish?id=${course.id}'/>">Publier</a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="<c:url value='/teacher/courses/content?id=${course.id}'/>">Gérer contenu</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
