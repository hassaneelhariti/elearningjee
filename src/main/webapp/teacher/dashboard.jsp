<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="active" value="dashboard"/>
</jsp:include>

<div class="container-fluid mt-4">
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
</body>
</html>