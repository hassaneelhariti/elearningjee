<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Courses - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <style>
        .enrolled-courses-container {
            padding: 20px;
        }
        
        .page-header {
            margin-bottom: 30px;
        }
        
        .page-header h1 {
            color: #333;
            margin-bottom: 10px;
        }
        
        .page-header p {
            color: #666;
            font-size: 16px;
        }
        
        .courses-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            margin-top: 20px;
        }
        
        .enrolled-course-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: 1px solid #e0e0e0;
        }
        
        .enrolled-course-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
        }
        
        .course-header {
            margin-bottom: 15px;
        }
        
        .course-header h3 {
            color: #2c3e50;
            margin: 0 0 10px 0;
            font-size: 22px;
        }
        
        .course-description {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 15px;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        
        .course-meta {
            margin-bottom: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 8px;
        }
        
        .course-meta-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            font-size: 14px;
        }
        
        .course-meta-item:last-child {
            margin-bottom: 0;
        }
        
        .meta-label {
            color: #666;
            font-weight: 500;
        }
        
        .meta-value {
            color: #333;
            font-weight: 600;
        }
        
        .progress-section {
            margin-bottom: 20px;
        }
        
        .progress-label {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            font-size: 14px;
            color: #666;
        }
        
        .progress-bar-container {
            width: 100%;
            height: 10px;
            background: #e0e0e0;
            border-radius: 5px;
            overflow: hidden;
        }
        
        .progress-bar {
            height: 100%;
            background: linear-gradient(90deg, #4CAF50, #45a049);
            border-radius: 5px;
            transition: width 0.3s ease;
        }
        
        .progress-percentage {
            font-weight: 600;
            color: #4CAF50;
        }
        
        .course-actions {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        
        .btn-continue {
            flex: 1;
            padding: 12px 20px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            transition: background 0.3s ease;
        }
        
        .btn-continue:hover {
            background: #45a049;
        }
        
        .btn-continue:active {
            transform: scale(0.98);
        }
        
        .completed-badge {
            display: inline-block;
            padding: 5px 12px;
            background: #4CAF50;
            color: white;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            margin-left: 10px;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        
        .empty-state-icon {
            font-size: 64px;
            margin-bottom: 20px;
        }
        
        .empty-state h2 {
            color: #333;
            margin-bottom: 10px;
        }
        
        .empty-state p {
            color: #666;
            margin-bottom: 30px;
        }
        
        .btn-browse {
            display: inline-block;
            padding: 12px 30px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            transition: background 0.3s ease;
        }
        
        .btn-browse:hover {
            background: #45a049;
        }
        
        .level-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .level-beginner {
            background: #e3f2fd;
            color: #1976d2;
        }
        
        .level-intermediate {
            background: #fff3e0;
            color: #f57c00;
        }
        
        .level-advanced {
            background: #fce4ec;
            color: #c2185b;
        }
    </style>
</head>
<body>
    <jsp:include page="/includes/student-sidebar.jsp">
        <jsp:param name="active" value="courses" />
    </jsp:include>
    <jsp:include page="/includes/student-header.jsp" />

    <div class="main-content">
        <div class="card">
            <h1 style="margin-bottom: 10px;">📚 My Enrolled Courses</h1>
            <p style="color: #6B7280;">Continue your learning and track your progress</p>
        </div>

            <!-- Messages d'alerte -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    ${success}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ${error}
                </div>
            </c:if>

        <!-- Liste des cours inscrits -->
        <c:if test="${empty enrolledCourses}">
            <div class="card" style="text-align: center; padding: 60px 20px;">
                <div style="font-size: 64px; margin-bottom: 20px;">📖</div>
                <h2>No enrolled courses</h2>
                <p style="color: #6B7280; margin-bottom: 30px;">You are currently not enrolled in any courses. Browse available courses and start learning!</p>
                <a href="${pageContext.request.contextPath}/course" class="btn btn-primary">Browse Courses</a>
            </div>
        </c:if>

        <c:if test="${not empty enrolledCourses}">
            <div class="courses-list">
                    <c:forEach var="course" items="${enrolledCourses}">
                        <div class="enrolled-course-card">
                            <div class="course-header">
                                <h3>
                                    ${course.courseTitle}
                                    <c:if test="${course.isCompleted}">
                                        <span class="completed-badge">✓ Terminé</span>
                                    </c:if>
                                </h3>
                            </div>
                            
                            <p class="course-description">${course.courseDescription}</p>
                            
                            <div class="course-meta">
                                <div class="course-meta-item">
                                    <span class="meta-label">Professeur:</span>
                                    <span class="meta-value">${course.teacherName}</span>
                                </div>
                                <div class="course-meta-item">
                                    <span class="meta-label">Niveau:</span>
                                    <span class="meta-value">
                                        <span class="level-badge level-${course.level.toLowerCase()}">${course.level}</span>
                                    </span>
                                </div>
                                <div class="course-meta-item">
                                    <span class="meta-label">Inscrit le:</span>
                                    <span class="meta-value">
                                        <c:choose>
                                            <c:when test="${not empty course.enrollmentDate}">
                                                <fmt:formatDate value="${course.enrollmentDate}" pattern="dd/MM/yyyy" />
                                            </c:when>
                                            <c:otherwise>
                                                N/A
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>
                            
                            <div class="progress-section">
                                <div class="progress-label">
                                    <span>Progression</span>
                                    <span class="progress-percentage"><fmt:formatNumber value="${course.progress}" maxFractionDigits="0" />%</span>
                                </div>
                                <div class="progress-bar-container">
                                    <div class="progress-bar" style="width: ${course.progress}%"></div>
                                </div>
                            </div>
                            
                            <div class="course-actions">
                                <a href="${pageContext.request.contextPath}/course-view?courseId=${course.courseId}" class="btn-continue">
                                    <c:choose>
                                        <c:when test="${course.progress == 0}">
                                            🎯 Commencer l'apprentissage
                                        </c:when>
                                        <c:when test="${course.isCompleted}">
                                            📖 Revoir le cours
                                        </c:when>
                                        <c:otherwise>
                                            ▶️ Continuer l'apprentissage
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Supprimer les messages d'alerte après 5 secondes
        setTimeout(() => {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                alert.style.transition = 'opacity 0.5s ease';
                alert.style.opacity = '0';
                setTimeout(() => {
                    if (alert.parentNode) {
                        alert.parentNode.removeChild(alert);
                    }
                }, 500);
            });
        }, 5000);
    });
</script>
</body>
</html>

