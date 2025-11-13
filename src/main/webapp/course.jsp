<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Courses - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <style>
        .courses-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            margin-top: 20px;
        }
        .course-card {
            background: white;
            border-radius: 16px;
            padding: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            transition: all 0.3s;
            border: 1px solid #E5E7EB;
        }
        .course-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }
        .course-header h3 {
            color: #1F2937;
            margin-bottom: 15px;
            font-size: 22px;
        }
        .course-description {
            color: #6B7280;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        .course-meta {
            margin-bottom: 20px;
            padding: 15px;
            background: #F9FAFB;
            border-radius: 8px;
        }
        .course-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .enroll-btn {
            padding: 10px 20px;
            background: #3B82F6;
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s;
        }
        .enroll-btn:hover {
            background: #2563EB;
        }
        .level-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        .level-beginner { background: #DBEAFE; color: #1E40AF; }
        .level-intermediate { background: #FEF3C7; color: #92400E; }
        .level-advanced { background: #FCE4EC; color: #C2185B; }
        .popular-topics {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 20px;
        }
        .topic-tag {
            padding: 8px 16px;
            background: #F3F4F6;
            border-radius: 20px;
            font-size: 14px;
            color: #4B5563;
        }
    </style>
</head>
<body>
    <jsp:include page="/includes/top-bar.jsp">
        <jsp:param name="role" value="STUDENT" />
    </jsp:include>
    <jsp:include page="/includes/student-sidebar.jsp">
        <jsp:param name="active" value="available" />
    </jsp:include>
    <jsp:include page="/includes/student-header.jsp" />

    <div class="main-content">

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

        <!-- Welcome Section -->
        <div class="card">
            <h2 style="margin-bottom: 10px;">Welcome, ${not empty userName ? userName : 'Student'}!</h2>
            <p style="color: #6B7280;">Discover all available courses and develop your skills.</p>
        </div>

        <!-- Courses List -->
        <c:if test="${empty courses}">
            <div class="card" style="text-align: center; padding: 60px 20px;">
                <h2>No courses available</h2>
                <p style="color: #6B7280;">There are currently no courses available. Please come back later!</p>
            </div>
        </c:if>

        <c:if test="${not empty courses}">
            <div class="courses-grid">
                <c:forEach var="course" items="${courses}" varStatus="loop">
                    <div class="course-card">
                        <div class="course-header">
                            <h3>${course.courseName}</h3>
                        </div>
                        <p class="course-description">${course.description}</p>
                        <div class="course-meta">
                            <div style="margin-bottom: 8px;">
                                <strong>Teacher:</strong> ${course.teacher}
                            </div>
                            <div>
                                <strong>Level:</strong>
                                <span class="level-badge level-${fn:toLowerCase(course.level)}">${course.level}</span>
                            </div>
                        </div>
                        <div class="course-footer">
                            <span style="color: #10B981; font-weight: 600;">Free</span>
                            <form action="${pageContext.request.contextPath}/course" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="enroll">
                                <input type="hidden" name="courseName" value="${course.courseName}">
                                <button type="submit" class="enroll-btn">
                                    Enroll
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <!-- Popular Topics -->
        <div class="card">
            <h3 style="margin-bottom: 10px;">Popular Topics</h3>
            <p style="color: #6B7280; margin-bottom: 20px;">Based on your learning activity, we've selected personalized topics for you.</p>
            <div class="popular-topics">
                <div class="topic-tag">Web Development</div>
                <div class="topic-tag">UX/UI Design</div>
                <div class="topic-tag">Data Science</div>
                <div class="topic-tag">Digital Marketing</div>
                <div class="topic-tag">Photography</div>
                <div class="topic-tag">Languages</div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Animation d'entrée pour les cartes de cours
        const courseCards = document.querySelectorAll('.course-card');

        courseCards.forEach((card, index) => {
            setTimeout(() => {
                card.classList.add('visible');
            }, index * 100);
        });

        // Gestion du formulaire d'inscription avec confirmation
        const enrollForms = document.querySelectorAll('.enroll-form');

        enrollForms.forEach(form => {
            form.addEventListener('submit', function(e) {
                e.preventDefault();

                const button = this.querySelector('.enroll-btn');
                const courseName = this.querySelector('input[name="courseName"]').value;

                // Confirmation avant envoi
                if (confirm(`Êtes-vous sûr de vouloir vous inscrire au cours "${courseName}" ?`)) {
                    // Animation de chargement
                    const originalText = button.textContent;
                    button.innerHTML = '<div class="loading"></div>';
                    button.disabled = true;

                    // Soumission du formulaire
                    setTimeout(() => {
                        this.submit();
                    }, 1000);
                }
            });
        });

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