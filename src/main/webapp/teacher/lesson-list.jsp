<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${section.title} - Lessons</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/teacher-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">
                    <a href="../../sections/course/${course.id}" class="text-decoration-none me-2">
                        <i class="fas fa-arrow-left"></i>
                    </a>
                    ${section.title} - Lessons
                </h1>
                <a href="${pageContext.request.contextPath}/teacher/lessons/new/${section.id}" class="btn btn-primary">
                    <i class="fas fa-plus me-1"></i> Add Lesson
                </a>
            </div>

            <!-- Messages -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">
                        Course: ${course.title} | Section: ${section.title}
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty lessons}">
                            <div class="list-group">
                                <c:forEach var="lesson" items="${lessons}">
                                    <div class="list-group-item d-flex justify-content-between align-items-center">
                                        <div>
                                            <i class="fas fa-play-circle text-primary me-2"></i>
                                            <strong>${lesson.title}</strong>
                                            <span class="badge bg-secondary ms-2">Order: ${lesson.orderIndex}</span>
                                        </div>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/teacher/lessons/edit/${lesson.id}" class="btn btn-sm btn-outline-secondary me-1">
                                                <i class="fas fa-edit"></i> Edit
                                            </a>
                                            <button type="button" class="btn btn-sm btn-outline-danger"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#deleteLessonModal${lesson.id}">
                                                <i class="fas fa-trash"></i> Delete
                                            </button>
                                        </div>
                                    </div>

                                    <!-- Delete Modal -->
                                    <div class="modal fade" id="deleteLessonModal${lesson.id}" tabindex="-1">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Confirm Delete</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Are you sure you want to delete "${lesson.title}"?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                    <form action="${pageContext.request.contextPath}/teacher/lessons" method="post">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="lessonId" value="${lesson.id}">
                                                        <input type="hidden" name="sectionId" value="${section.id}">
                                                        <button type="submit" class="btn btn-danger">Delete</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <i class="fas fa-play-circle fa-4x text-muted mb-3"></i>
                                <h4 class="text-muted">No Lessons Yet</h4>
                                <p class="text-muted">Start by creating your first lesson for this section.</p>
                                <a href="${pageContext.request.contextPath}/teacher/lessons/new/${section.id}" class="btn btn-primary btn-lg">
                                    <i class="fas fa-plus me-2"></i>Create First Lesson
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>