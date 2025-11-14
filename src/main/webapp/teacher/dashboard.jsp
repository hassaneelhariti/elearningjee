<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>
    <jsp:include page="/includes/teacher-sidebar.jsp">
        <jsp:param name="active" value="dashboard" />
    </jsp:include>
    <jsp:include page="/includes/teacher-header.jsp" />

    <div class="main-content">
    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Total Courses
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalCourses}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-book fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Total Students
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalStudents}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-users fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                Total Lessons
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalLessons}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-play-circle fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                Quick Actions
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">
                                <a href="courses/new" class="text-warning text-decoration-none">
                                    <i class="fas fa-plus me-1"></i>New Course
                                </a>
                            </div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-plus-circle fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Graphiques -->
    <div class="row mb-4">
        <div class="col-md-6 mb-4">
            <div class="card shadow">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">
                        <i class="fas fa-chart-pie me-2"></i>Statut des Cours
                    </h6>
                </div>
                <div class="card-body">
                    <div style="position: relative; height: 300px;">
                        <canvas id="coursesStatusChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 mb-4">
            <div class="card shadow">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">
                        <i class="fas fa-chart-bar me-2"></i>Étudiants par Cours
                    </h6>
                </div>
                <div class="card-body">
                    <div style="position: relative; height: 300px;">
                        <canvas id="studentsByCourseChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Recent Courses -->
    <div class="row">
        <div class="col-12">
            <div class="card shadow">
                <div class="card-header py-3 d-flex justify-content-between align-items-center">
                    <h6 class="m-0 font-weight-bold text-primary">My Recent Courses</h6>
                    <a href="courses/new" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus me-1"></i>New Course
                    </a>
                </div>
                <div class="card-body">
                    <c:if test="${not empty courses}">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Level</th>
                                    <th>Status</th>
                                    <th>Created Date</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="course" items="${courses}">
                                    <tr>
                                        <td>
                                            <strong>${course.title}</strong>
                                            <br>
                                            <small class="text-muted">${course.description.length() > 100 ? course.description.substring(0, 100) + '...' : course.description}</small>
                                        </td>
                                        <td>
                                            <span class="badge bg-secondary">${course.level}</span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${course.isPublished}">
                                                    <span class="badge bg-success">Published</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-warning">Draft</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${course.dateCreated}</td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="courses/edit/${course.id}" class="btn btn-outline-primary" title="Edit">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="sections/course/${course.id}" class="btn btn-outline-info" title="Manage Sections">
                                                    <i class="fas fa-folder"></i>
                                                </a>
                                                <a href="../course-view?courseId=${course.id}" class="btn btn-outline-success" target="_blank" title="Preview">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <a href="courses/delete/${course.id}" class="btn btn-outline-danger"
                                                   onclick="return confirm('Are you sure you want to delete this course?')" title="Delete">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                    <c:if test="${empty courses}">
                        <div class="text-center py-5">
                            <i class="fas fa-book fa-4x text-muted mb-3"></i>
                            <h4 class="text-muted">No Courses Yet</h4>
                            <p class="text-muted mb-4">Start by creating your first course to share your knowledge with students.</p>
                            <a href="courses/new" class="btn btn-primary btn-lg">
                                <i class="fas fa-plus me-2"></i>Create Your First Course
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
<script>
    // Données récupérées depuis la base de données
    const publishedCount = ${not empty publishedCount ? publishedCount : 0};
    const draftCount = ${not empty draftCount ? draftCount : 0};

    // Graphique en camembert - Statut des cours (données depuis DB)
    const statusCtx = document.getElementById('coursesStatusChart').getContext('2d');
    new Chart(statusCtx, {
        type: 'pie',
        data: {
            labels: ['Publiés', 'Brouillons'],
            datasets: [{
                data: [publishedCount, draftCount],
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
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const total = context.dataset.data.reduce((a, b) => a + b, 0);
                            const percentage = total > 0 ? ((context.parsed / total) * 100).toFixed(1) : 0;
                            return context.label + ': ' + context.parsed + ' (' + percentage + '%)';
                        }
                    }
                }
            }
        }
    });

    // Graphique en barres - Étudiants par cours (données depuis DB)
    const studentsCtx = document.getElementById('studentsByCourseChart').getContext('2d');
    <c:choose>
        <c:when test="${not empty courses}">
            const courseLabels = [];
            const studentCounts = [];
            <c:forEach var="course" items="${courses}">
                <c:set var="courseTitle" value="${course.title}" />
                <c:set var="shortTitle" value="${fn:length(courseTitle) > 15 ? fn:substring(courseTitle, 0, 15) : courseTitle}" />
                courseLabels.push('<c:out value="${fn:replace(shortTitle, \"'\", \"\\'\")}" escapeXml="true" />');
                <c:set var="studentCount" value="0" />
                <c:if test="${not empty course.enrollments}">
                    <c:set var="studentCount" value="${course.enrollments.size()}" />
                </c:if>
                studentCounts.push(${studentCount});
            </c:forEach>
            
            new Chart(studentsCtx, {
                type: 'bar',
                data: {
                    labels: courseLabels,
                    datasets: [{
                        label: 'Nombre d\'étudiants',
                        data: studentCounts,
                        backgroundColor: '#2196F3',
                        borderColor: '#1976D2',
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
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return 'Étudiants: ' + context.parsed.y;
                                }
                            }
                        }
                    }
                }
            });
        </c:when>
        <c:otherwise>
            new Chart(studentsCtx, {
                type: 'bar',
                data: {
                    labels: ['Aucun cours'],
                    datasets: [{
                        label: 'Nombre d\'étudiants',
                        data: [0],
                        backgroundColor: '#e0e0e0'
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        </c:otherwise>
    </c:choose>
           </script>
    </div>
</body>
</html>