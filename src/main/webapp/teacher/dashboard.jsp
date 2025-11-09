<%-- 
    Document   : dashboard
    Created on : Nov 8, 2025, 1:12:31 PM
    Author     : ousam713
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord - Enseignant</title>
</head>
<body>
    <h1>Tableau de Bord Enseignant</h1>
    
    <div>
        <h2>Bienvenue, ${sessionScope.user.firstName} ${sessionScope.user.lastName}</h2>
        <p>Email: ${sessionScope.user.email}</p>
        <c:if test="${not empty sessionScope.user.bio}">
            <p>Bio: ${sessionScope.user.bio}</p>
        </c:if>
    </div>

    <nav>
        <h3>Navigation Rapide</h3>
        <ul>
            <li><a href="<c:url value='/teacher/courses'/>">Mes Cours</a></li>
            <li><a href="<c:url value='/teacher/courses/create'/>">Créer un Nouveau Cours</a></li>
            <li><a href="<c:url value='/auth/logout'/>">Déconnexion</a></li>
        </ul>
    </nav>

    <section>
        <h3>Statistiques Rapides</h3>
        <p>Nombre total de cours: ${requestScope.totalCourses}</p>
        <p>Cours publiés: ${requestScope.publishedCourses}</p>
        <p>Cours en brouillon: ${requestScope.draftCourses}</p>
    </section>
</body>
</html>