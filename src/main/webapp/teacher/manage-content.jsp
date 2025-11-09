<%-- 
    Document   : manage-content
    Created on : Nov 8, 2025, 1:13:14 PM
    Author     : ousam713
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gérer le Contenu - ${requestScope.course.title}</title>
</head>
<body>
    <h1>Gérer le Contenu: ${requestScope.course.title}</h1>
    
    <div>
        <a href="<c:url value='/teacher/courses'/>">← Retour à mes cours</a>
    </div>

    <h2>Sections du Cours</h2>
    
    <c:if test="${empty requestScope.course.sections}">
        <p>Aucune section créée pour ce cours.</p>
    </c:if>

    <c:forEach var="section" items="${requestScope.course.sections}">
        <div style="border: 1px solid #ccc; margin: 10px 0; padding: 10px;">
            <h3>Section: ${section.title} (Ordre: ${section.orderIndex})</h3>
            
            <h4>Leçons dans cette section:</h4>
            <c:choose>
                <c:when test="${empty section.lessons}">
                    <p>Aucune leçon dans cette section.</p>
                </c:when>
                <c:otherwise>
                    <ul>
                        <c:forEach var="lesson" items="${section.lessons}">
                            <li>
                                <strong>${lesson.title}</strong> (Ordre: ${lesson.orderIndex})
                                <c:if test="${not empty lesson.video}">
                                    - 📹 Vidéo disponible
                                </c:if>
                                <a href="<c:url value='/teacher/lessons/edit?id=${lesson.id}'/>">Modifier</a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
            
            <div>
                <a href="<c:url value='/teacher/lessons/create?sectionId=${section.id}'/>">➕ Ajouter une leçon</a>
                <a href="<c:url value='/teacher/sections/edit?id=${section.id}'/>">✏️ Modifier la section</a>
            </div>
        </div>
    </c:forEach>

    <div>
        <h3>Ajouter une Nouvelle Section</h3>
        <form action="<c:url value='/teacher/sections/create'/>" method="post">
            <input type="hidden" name="courseId" value="${requestScope.course.id}">
            <div>
                <label for="sectionTitle">Titre de la section:</label>
                <input type="text" id="sectionTitle" name="title" required>
            </div>
            <div>
                <label for="orderIndex">Ordre d'affichage:</label>
                <input type="number" id="orderIndex" name="orderIndex" min="1" required>
            </div>
            <button type="submit">Créer la section</button>
        </form>
    </div>
</body>
</html>