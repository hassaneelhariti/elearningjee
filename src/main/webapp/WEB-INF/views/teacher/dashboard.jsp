<%-- WEB-INF/views/teacher/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Enseignant - E-Learning</title>
</head>
<body>
    <h1>Tableau de Bord Enseignant</h1>
    
    <p>
        Bienvenue <strong>${teacher.firstName} ${teacher.lastName}</strong> | 
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </p>
    
    <hr>
    
    <h2>Mes Cours</h2>
    
    <p>
        <a href="${pageContext.request.contextPath}/teacher/course?action=create">
            Créer un nouveau cours
        </a>
    </p>
    
    <c:choose>
        <c:when test="${empty courses}">
            <p>Vous n'avez créé aucun cours pour le moment.</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Niveau</th>
                        <th>Statut</th>
                        <th>Date de création</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td>${course.title}</td>
                            <td>${course.level}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${course.isPublished}">
                                        <strong>Publié</strong>
                                    </c:when>
                                    <c:otherwise>
                                        Brouillon
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${course.dateCreated}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/teacher/course?action=edit&id=${course.id}">
                                    Modifier
                                </a>
                                |
                                <c:choose>
                                    <c:when test="${course.isPublished}">
                                        <form action="${pageContext.request.contextPath}/teacher/course" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="unpublish" />
                                            <input type="hidden" name="id" value="${course.id}" />
                                            <input type="submit" value="Dépublier" />
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${pageContext.request.contextPath}/teacher/course" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="publish" />
                                            <input type="hidden" name="id" value="${course.id}" />
                                            <input type="submit" value="Publier" />
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <p>Total: ${courses.size()} cours</p>
        </c:otherwise>
    </c:choose>
    
    <hr>
    
    <h2>Informations du Profil</h2>
    <table border="1">
        <tr>
            <td><strong>Nom d'utilisateur:</strong></td>
            <td>${teacher.username}</td>
        </tr>
        <tr>
            <td><strong>Email:</strong></td>
            <td>${teacher.email}</td>
        </tr>
        <tr>
            <td><strong>Biographie:</strong></td>
            <td>${teacher.bio}</td>
        </tr>
    </table>
</body>
</html>