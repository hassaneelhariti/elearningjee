<%-- 
    Document   : edit-course
    Created on : Nov 8, 2025, 1:13:06 PM
    Author     : ousam713
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier le Cours - Enseignant</title>
</head>
<body>
    <h1>Modifier le Cours: ${requestScope.course.title}</h1>
    
    <div>
        <a href="<c:url value='/teacher/courses'/>">← Retour à mes cours</a>
    </div>

    <c:if test="${not empty requestScope.error}">
        <p style="color: red;">Erreur: ${requestScope.error}</p>
    </c:if>

    <c:if test="${not empty requestScope.success}">
        <p style="color: green;">${requestScope.success}</p>
    </c:if>

    <form action="<c:url value='/teacher/courses/update'/>" method="post">
        <input type="hidden" name="id" value="${requestScope.course.id}">
        
        <div>
            <label for="title">Titre du cours:</label>
            <input type="text" id="title" name="title" value="${requestScope.course.title}" required>
        </div>
        
        <div>
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" cols="50" required>${requestScope.course.description}</textarea>
        </div>
        
        <div>
            <label for="level">Niveau:</label>
            <select id="level" name="level" required>
                <option value="Débutant" <c:if test="${requestScope.course.level == 'Débutant'}">selected</c:if>>Débutant</option>
                <option value="Intermédiaire" <c:if test="${requestScope.course.level == 'Intermédiaire'}">selected</c:if>>Intermédiaire</option>
                <option value="Avancé" <c:if test="${requestScope.course.level == 'Avancé'}">selected</c:if>>Avancé</option>
            </select>
        </div>
        
        <div>
            <label for="thumbnail">URL de l'image:</label>
            <input type="url" id="thumbnail" name="thumbnail" value="${requestScope.course.thumbnail}">
        </div>
        
        <div>
            <label>
                <input type="checkbox" name="published" <c:if test="${requestScope.course.published}">checked</c:if>>
                Cours publié
            </label>
        </div>
        
        <div>
            <button type="submit">Mettre à jour</button>
            <a href="<c:url value='/teacher/courses'/>">Annuler</a>
        </div>
    </form>
</body>
</html>