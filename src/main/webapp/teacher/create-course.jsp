<%-- 
    Document   : create-course
    Created on : Nov 8, 2025, 1:12:59 PM
    Author     : ousam713
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Créer un Cours - Enseignant</title>
</head>
<body>
    <h1>Créer un Nouveau Cours</h1>
    
    <div>
        <a href="<c:url value='/teacher/courses'/>">← Retour à mes cours</a>
    </div>

    <c:if test="${not empty requestScope.error}">
        <p style="color: red;">Erreur: ${requestScope.error}</p>
    </c:if>

    <form action="<c:url value='/teacher/courses/create'/>" method="post">
        <div>
            <label for="title">Titre du cours:</label>
            <input type="text" id="title" name="title" required>
        </div>
        
        <div>
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" cols="50" required></textarea>
        </div>
        
        <div>
            <label for="level">Niveau:</label>
            <select id="level" name="level" required>
                <option value="">Sélectionner un niveau</option>
                <option value="Débutant">Débutant</option>
                <option value="Intermédiaire">Intermédiaire</option>
                <option value="Avancé">Avancé</option>
            </select>
        </div>
        
        <div>
            <label for="thumbnail">URL de l'image (optionnel):</label>
            <input type="url" id="thumbnail" name="thumbnail">
        </div>
        
        <div>
            <button type="submit">Créer le cours</button>
            <button type="reset">Effacer</button>
        </div>
    </form>
</body>
</html>