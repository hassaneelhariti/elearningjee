<%-- WEB-INF/views/courses/list.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Cours - E-Learning</title>
</head>
<body>
    <h1>Catalogue des Cours</h1>
    
    <p>
        <a href="${pageContext.request.contextPath}/login">Se connecter</a> |
        <a href="${pageContext.request.contextPath}/register">S'inscrire</a>
    </p>
    
    <h2>Rechercher un cours</h2>
    <form action="${pageContext.request.contextPath}/courses" method="get">
        <input type="hidden" name="action" value="search" />
        <input type="text" name="keyword" placeholder="Rechercher par titre" value="${keyword}" />
        <input type="submit" value="Rechercher" />
    </form>
    
    <h2>Filtrer par niveau</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/courses">Tous les cours</a></li>
        <li><a href="${pageContext.request.contextPath}/courses?level=Beginner">Débutant</a></li>
        <li><a href="${pageContext.request.contextPath}/courses?level=Intermediate">Intermédiaire</a></li>
        <li><a href="${pageContext.request.contextPath}/courses?level=Advanced">Avancé</a></li>
    </ul>
    
    <c:if test="${not empty selectedLevel}">
        <p>Niveau sélectionné: <strong>${selectedLevel}</strong></p>
    </c:if>
    
    <h2>Liste des cours disponibles</h2>
    
    <c:choose>
        <c:when test="${empty courses}">
            <p>Aucun cours disponible pour le moment.</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Enseignant</th>
                        <th>Niveau</th>
                        <th>Date de création</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td>${course.title}</td>
                            <td>${course.teacher.firstName} ${course.teacher.lastName}</td>
                            <td>${course.level}</td>
                            <td>${course.dateCreated}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/courses?action=view&id=${course.id}">
                                    Voir détails
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <p>Total: ${courses.size()} cours</p>
        </c:otherwise>
    </c:choose>
</body>
</html>