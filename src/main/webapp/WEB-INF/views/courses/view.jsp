<%-- WEB-INF/views/courses/view.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${course.title} - E-Learning</title>
</head>
<body>
    <h1>${course.title}</h1>
    
    <p><a href="${pageContext.request.contextPath}/courses">Retour à la liste</a></p>
    
    <h2>Informations sur le cours</h2>
    <table border="1">
        <tr>
            <td><strong>Enseignant:</strong></td>
            <td>${course.teacher.firstName} ${course.teacher.lastName}</td>
        </tr>
        <tr>
            <td><strong>Niveau:</strong></td>
            <td>${course.level}</td>
        </tr>
        <tr>
            <td><strong>Date de création:</strong></td>
            <td>${course.dateCreated}</td>
        </tr>
        <tr>
            <td><strong>Nombre d'inscrits:</strong></td>
            <td>${enrollmentCount}</td>
        </tr>
        <tr>
            <td><strong>Statut:</strong></td>
            <td>
                <c:choose>
                    <c:when test="${course.isPublished}">Publié</c:when>
                    <c:otherwise>Brouillon</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
    
    <h2>Description</h2>
    <p>${course.description}</p>
    
    <c:if test="${not empty course.thumbnail}">
        <h2>Image du cours</h2>
        <img src="${course.thumbnail}" alt="${course.title}" width="300" />
    </c:if>
    
    <h2>Contenu du cours</h2>
    <c:choose>
        <c:when test="${empty sections}">
            <p>Aucune section disponible pour ce cours.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="section" items="${sections}">
                <h3>${section.orderIndex}. ${section.title}</h3>
                <ul>
                    <c:forEach var="lesson" items="${section.lessons}">
                        <li>${lesson.title}</li>
                    </c:forEach>
                </ul>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    
    <p>
        <a href="${pageContext.request.contextPath}/login">Se connecter pour s'inscrire</a>
    </p>
</body>
</html>