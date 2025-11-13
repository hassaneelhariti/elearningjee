<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Administrateur</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
    <style>
        body { background: #fff8f0; }
        .stat-card {
            border-left: 4px solid;
            transition: transform 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-card.primary { border-left-color: #FF9800; }
        .stat-card.success { border-left-color: #4CAF50; }
        .stat-card.info { border-left-color: #2196F3; }
        .stat-card.warning { border-left-color: #9C27B0; }
        .chart-container {
            position: relative;
            height: 300px;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="container-fluid py-4">
    <div class="row mb-4">
        <div class="col-12">
            <div class="card shadow">
                <div class="card-header bg-warning text-white">
                    <h2 class="mb-0"><i class="fas fa-cog me-2"></i>Tableau de Bord Administrateur</h2>
                    <p class="mb-0">Bienvenue, ${not empty userName ? userName : 'Administrateur'} !</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Statistiques -->
    <div class="row mb-4">
        <div class="col-md-3 mb-3">
            <div class="card stat-card primary shadow h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted mb-1">Total Utilisateurs</h6>
                            <h3 class="mb-0">${not empty totalUsers ? totalUsers : 0}</h3>
                        </div>
                        <i class="fas fa-users fa-3x text-warning opacity-50"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card stat-card success shadow h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted mb-1">Total Cours</h6>
                            <h3 class="mb-0">${not empty totalCourses ? totalCourses : 0}</h3>
                        </div>
                        <i class="fas fa-book fa-3x text-success opacity-50"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card stat-card info shadow h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted mb-1">Total Inscriptions</h6>
                            <h3 class="mb-0">${not empty totalEnrollments ? totalEnrollments : 0}</h3>
                        </div>
                        <i class="fas fa-user-graduate fa-3x text-primary opacity-50"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card stat-card warning shadow h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted mb-1">Cours Publiés</h6>
                            <h3 class="mb-0">${not empty publishedCourses ? publishedCourses : 0}</h3>
                        </div>
                        <i class="fas fa-check-circle fa-3x text-purple opacity-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Graphiques -->
    <div class="row mb-4">
        <div class="col-md-6 mb-4">
            <div class="card shadow">
                <div class="card-header bg-white">
                    <h5 class="mb-0"><i class="fas fa-chart-pie me-2"></i>Répartition des Utilisateurs</h5>
                </div>
                <div class="card-body">
                    <div class="chart-container">
                        <canvas id="usersPieChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 mb-4">
            <div class="card shadow">
                <div class="card-header bg-white">
                    <h5 class="mb-0"><i class="fas fa-chart-bar me-2"></i>Cours par Niveau</h5>
                </div>
                <div class="card-body">
                    <div class="chart-container">
                        <canvas id="coursesByLevelChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row mb-4">
        <div class="col-md-6 mb-4">
            <div class="card shadow">
                <div class="card-header bg-white">
                    <h5 class="mb-0"><i class="fas fa-chart-pie me-2"></i>Statut des Cours</h5>
                </div>
                <div class="card-body">
                    <div class="chart-container">
                        <canvas id="coursesStatusChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Menu de navigation -->
    <div class="row">
        <div class="col-12">
            <div class="card shadow">
                <div class="card-header bg-white">
                    <h5 class="mb-0"><i class="fas fa-bars me-2"></i>Navigation</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3 mb-2">
                            <a href="#" class="btn btn-outline-primary w-100">
                                <i class="fas fa-users me-2"></i>Gérer les Utilisateurs
                            </a>
                        </div>
                        <div class="col-md-3 mb-2">
                            <a href="#" class="btn btn-outline-success w-100">
                                <i class="fas fa-book me-2"></i>Gérer les Cours
                            </a>
                        </div>
                        <div class="col-md-3 mb-2">
                            <a href="#" class="btn btn-outline-info w-100">
                                <i class="fas fa-cog me-2"></i>Paramètres Système
                            </a>
                        </div>
                        <div class="col-md-3 mb-2">
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger w-100">
                                <i class="fas fa-sign-out-alt me-2"></i>Déconnexion
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Graphique en camembert - Répartition des utilisateurs
    const usersCtx = document.getElementById('usersPieChart').getContext('2d');
    new Chart(usersCtx, {
        type: 'pie',
        data: {
            labels: ['Étudiants', 'Enseignants', 'Administrateurs'],
            datasets: [{
                data: [
                    ${not empty totalStudents ? totalStudents : 0},
                    ${not empty totalTeachers ? totalTeachers : 0},
                    ${not empty totalAdmins ? totalAdmins : 0}
                ],
                backgroundColor: ['#4CAF50', '#2196F3', '#FF9800'],
                borderWidth: 2,
                borderColor: '#fff'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });

    // Graphique en barres - Cours par niveau
    const levelCtx = document.getElementById('coursesByLevelChart').getContext('2d');
    <c:if test="${not empty coursesByLevel}">
    const levelLabels = [];
    const levelData = [];
    <c:forEach var="entry" items="${coursesByLevel}">
        <c:if test="${entry.value > 0}">
            levelLabels.push('${entry.key}');
            levelData.push(${entry.value});
        </c:if>
    </c:forEach>
    
    new Chart(levelCtx, {
        type: 'bar',
        data: {
            labels: levelLabels,
            datasets: [{
                label: 'Nombre de cours',
                data: levelData,
                backgroundColor: ['#FF9800', '#2196F3', '#4CAF50', '#9C27B0'],
                borderColor: '#fff',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
    </c:if>

    // Graphique en camembert - Statut des cours
    const statusCtx = document.getElementById('coursesStatusChart').getContext('2d');
    new Chart(statusCtx, {
        type: 'pie',
        data: {
            labels: ['Publiés', 'Brouillons'],
            datasets: [{
                data: [
                    ${not empty publishedCourses ? publishedCourses : 0},
                    ${not empty draftCourses ? draftCourses : 0}
                ],
                backgroundColor: ['#28a745', '#ffc107'],
                borderWidth: 2,
                borderColor: '#fff'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
</script>
</body>
</html>
