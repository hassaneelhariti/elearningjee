<%-- WEB-INF/views/student/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Étudiant - E-Learning</title>
</head>
<body>
    <h1>Tableau de Bord Étudiant</h1>
    
    <p>
        Bienvenue <strong>${student.firstName} ${student.lastName}</strong> | 
        <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    </p>
    
    <hr>
    
    <h2>Mes Cours</h2>
    
    <c:choose>
        <c:when test="${empty enrollments}">
            <p>Vous n'êtes inscrit à aucun cours pour le moment.</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Cours</th>
                        <th>Enseignant</th>
                        <th>Niveau</th>
                        <th>Progression</th>
                        <th>Date d'inscription</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="enrollment" items="${enrollments}">
                        <tr>
                            <td>${enrollment.course.title}</td>
                            <td>${enrollment.course.teacher.firstName} ${enrollment.course.teacher.lastName}</td>
                            <td>${enrollment.course.level}</td>
                            <td>${enrollment.progress}%</td>
                            <td>${enrollment.enrollmentDate}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/student/course?id=${enrollment.course.id}">
                                    Continuer
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
    
    <hr>
    
    <h2>Cours Disponibles</h2>
    
    <c:choose>
        <c:when test="${empty availableCourses}">
            <p>Aucun cours disponible pour le moment.</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Enseignant</th>
                        <th>Niveau</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${availableCourses}">
                        <tr>
                            <td>${course.title}</td>
                            <td>${course.teacher.firstName} ${course.teacher.lastName}</td>
                            <td>${course.level}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/student/enroll" method="post">
                                    <input type="hidden" name="courseId" value="${course.id}" />
                                    <input type="submit" value="S'inscrire" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>