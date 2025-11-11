<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Academia - Cours Disponibles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/course.css">
</head>
<body>
<div class="container">
    <!-- Sidebar -->
    <div class="sidebar">
        <div class="logo">
            <h1>Academia</h1>
        </div>

        <ul class="nav-menu">
            <li><a href="#" class="active">Home</a></li>
            <li><a href="#">Bookmark</a></li>
            <li><a href="course-view?courseId=5">Courses</a></li>
            <li><a href="#">Tutorials</a></li>
            <li><a href="#">Workshop</a></li>
            <li><a href="#">Certifications</a></li>
            <li><a href="#">Resources</a></li>
            <li><a href="#">Events</a></li>
            <li><a href="#">Community</a></li>
        </ul>

        <hr class="sidebar-divider">

        <div class="user-section">
            <c:if test="${not empty userName}">
                <div class="user-name">${userName}</div>
                <div class="user-role">${userRole.toLowerCase()}</div>
            </c:if>
            <c:if test="${empty userName}">
                <a href="${pageContext.request.contextPath}/login" class="login-link">Se connecter</a>
            </c:if>
        </div>

        <div class="help-section">
            <h3>Help Center</h3>
            <ul class="help-links">
                <li><a href="#">My Account</a></li>
                <li><a href="#">Figma Application</a></li>
                <li><a href="#">Webflow Basic</a></li>
                <li><a href="#">Adobe Photoshop</a></li>
                <li><a href="#">Sketch Application</a></li>
            </ul>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">
            <h1>Cours Disponibles</h1>
            <c:if test="${not empty userName}">
                <div class="user-info">
                    Connecté en tant que <strong>${userName}</strong>
                </div>
            </c:if>
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

        <!-- Welcome Section -->
        <div class="welcome-section">
            <h2>Bienvenue, ${not empty userName ? userName : 'Étudiant'}!</h2>
            <p>Découvrez tous nos cours disponibles et développez vos compétences.</p>
        </div>

        <!-- Courses List -->
        <c:if test="${empty courses}">
            <div class="empty-state">
                <h2>Aucun cours disponible</h2>
                <p>Il n'y a actuellement aucun cours disponible. Revenez plus tard!</p>
            </div>
        </c:if>

        <c:if test="${not empty courses}">
            <div class="courses-grid">
                <c:forEach var="course" items="${courses}" varStatus="loop">
                    <!-- SUPPRIMER l'utilisation de course.id -->
                    <div class="course-card fade-in" id="course-${loop.index}">
                        <div class="course-header">
                            <h3>${course.courseName}</h3>
                        </div>
                        <p class="course-description">${course.description}</p>
                        <div class="course-meta">
                            <div class="course-teacher">
                                <strong>Professeur:</strong> ${course.teacher}
                            </div>
                            <div class="course-level">
                                <strong>Niveau:</strong>
                                <span class="level-badge level-${course.level.toLowerCase()}">${course.level}</span>
                            </div>
                        </div>
                        <div class="course-footer">
                            <span class="course-price">Gratuit</span>

                            <!-- Formulaire temporaire sans ID -->
                            <form action="${pageContext.request.contextPath}/course" method="post" class="enroll-form">
                                <input type="hidden" name="action" value="enroll">
                                <input type="hidden" name="courseName" value="${course.courseName}">
                                <button type="submit" class="enroll-btn" id="enroll-btn-${loop.index}">
                                    S'inscrire
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <!-- Popular Topics -->
        <h2 class="section-title">Sujets Populaires</h2>
        <p class="section-subtitle">Basé sur votre activité d'apprentissage, nous avons sélectionné des sujets personnalisés pour vous.</p>

        <div class="popular-topics">
            <div class="topic-tag">Développement Web</div>
            <div class="topic-tag">Design UX/UI</div>
            <div class="topic-tag">Data Science</div>
            <div class="topic-tag">Marketing Digital</div>
            <div class="topic-tag">Photographie</div>
            <div class="topic-tag">Langues</div>
        </div>
    </div>
</div>

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