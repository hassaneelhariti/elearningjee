<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${course.title} - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/teacher-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">${course.title}</h1>
                <div>
                    <a href="sections/course/${course.id}" class="btn btn-info me-2">
                        <i class="fas fa-list me-1"></i> Manage Sections
                    </a>
                    <a href="edit/${course.id}" class="btn btn-secondary me-2">
                        <i class="fas fa-edit me-1"></i> Edit Course
                    </a>
                    <a href="list" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-1"></i> Back
                    </a>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-8">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Course Overview</h5>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty course.thumbnail}">
                                <img src="${course.thumbnail}" class="img-fluid rounded mb-3" alt="${course.title}" style="max-height: 300px; width: 100%; object-fit: cover;">
                            </c:if>

                            <h6>Description</h6>
                            <p class="card-text">${course.description}</p>

                            <div class="row mt-4">
                                <div class="col-md-6">
                                    <h6>Course Details</h6>
                                    <ul class="list-unstyled">
                                        <li><strong>Level:</strong>
                                            <span class="badge bg-${course.level == 'Beginner' ? 'success' : course.level == 'Intermediate' ? 'warning' : 'danger'}">
                                                ${course.level}
                                            </span>
                                        </li>
                                        <li><strong>Status:</strong>
                                            <span class="badge bg-${course.isPublished ? 'success' : 'secondary'}">
                                                ${course.isPublished ? 'Published' : 'Draft'}
                                            </span>
                                        </li>
                                        <li><strong>Created:</strong> ${course.dateCreated}</li>
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <h6>Quick Actions</h6>
                                    <div class="d-grid gap-2">
                                        <!-- Change this in your course-detail.jsp -->
                                        <a href="../sections/course/${course.id}" class="btn btn-outline-primary">
                                            <i class="fas fa-plus me-1"></i> Add Section
                                        </a>


                                        <a href="edit/${course.id}" class="btn btn-outline-secondary">
                                            <i class="fas fa-cog me-1"></i> Course Settings
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card">
                        <div class="card-header">
                            <h6 class="card-title mb-0">Course Statistics</h6>
                        </div>
                        <div class="card-body">
                            <div class="list-group list-group-flush">
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    Sections
                                    <span class="badge bg-primary rounded-pill">${course.sections != null ? course.sections.size() : 0}</span>
                                </div>
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    Total Lessons
                                    <span class="badge bg-info rounded-pill">
                                            <c:set var="totalLessons" value="0" />
                                            <c:if test="${not empty course.sections}">
                                                <c:forEach var="section" items="${course.sections}">
                                                    <c:set var="totalLessons" value="${totalLessons + (section.lessons != null ? section.lessons.size() : 0)}" />
                                                </c:forEach>
                                            </c:if>
                                            ${totalLessons}
                                        </span>
                                </div>
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    Enrollment
                                    <span class="badge bg-success rounded-pill">0</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>