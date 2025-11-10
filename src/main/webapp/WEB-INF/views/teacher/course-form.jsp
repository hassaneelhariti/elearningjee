<%-- WEB-INF/views/teacher/course-form.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty course}">Modifier le Cours</c:when>
            <c:otherwise>Créer un Cours</c:otherwise>
        </c:choose>
        - E-Learning
    </title>
</head>
<body>
    <h1>
        <c:choose>
            <c:when test="${not empty course}">Modifier le Cours</c:when>
            <c:otherwise>Créer un Nouveau Cours</c:otherwise>
        </c:choose>
    </h1>
    
    <p>
        <a href="${pageContext.request.contextPath}/teacher/dashboard">
            Retour au tableau de bord
        </a>
    </p>
    
    <form action="${pageContext.request.contextPath}/teacher/course" method="post">
        <c:choose>
            <c:when test="${not empty course}">
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="id" value="${course.id}" />
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="create" />
            </c:otherwise>
        </c:choose>
        
        <table>
            <tr>
                <td><label for="title">Titre du cours:</label></td>
                <td>
                    <input type="text" id="title" name="title" 
                           value="${course.title}" required size="50" />
                </td>
            </tr>
            <tr>
                <td><label for="description">Description:</label></td>
                <td>
                    <textarea id="description" name="description" 
                              rows="5" cols="50" required>${course.description}</textarea>
                </td>
            </tr>
            <tr>
                <td><label for="level">Niveau:</label></td>
                <td>
                    <select id="level" name="level" required>
                        <option value="Beginner" 
                            <c:if test="${course.level == 'Beginner'}">selected</c:if>>
                            Débutant
                        </option>
                        <option value="Intermediate" 
                            <c:if test="${course.level == 'Intermediate'}">selected</c:if>>
                            Intermédiaire
                        </option>
                        <option value="Advanced" 
                            <c:if test="${course.level == 'Advanced'}">selected</c:if>>
                            Avancé
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="thumbnail">URL de l'image:</label></td>
                <td>
                    <input type="text" id="thumbnail" name="thumbnail" 
                           value="${course.thumbnail}" size="50" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" 
                           value="<c:choose><c:when test='${not empty course}'>Mettre à jour</c:when><c:otherwise>Créer</c:otherwise></c:choose>" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>