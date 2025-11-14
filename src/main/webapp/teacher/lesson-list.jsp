<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${section.title} - Lessons</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <style>
        .list-group-item {
            border: 1px solid #E5E7EB;
            border-radius: 8px;
            margin-bottom: 10px;
            padding: 15px;
            transition: all 0.3s;
        }
        .list-group-item:hover {
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            transform: translateX(5px);
        }
    </style>
</head>
<body>
    <jsp:include page="/includes/teacher-sidebar.jsp">
        <jsp:param name="active" value="courses" />
    </jsp:include>
    <jsp:include page="/includes/teacher-header.jsp" />

    <div class="main-content">
        <div class="card">
            <div class="card-header-custom">
                <div>
                    <a href="${pageContext.request.contextPath}/teacher/sections/course/${course.id}" class="btn btn-outline mb-2">
                        <i class="fas fa-arrow-left me-1"></i> Back to Sections
                    </a>
                    <h3 class="mb-0">${section.title} - Lessons</h3>
                    <p class="text-muted mb-0" style="font-size: 14px;">Course: ${course.title}</p>
                </div>
                <a href="${pageContext.request.contextPath}/teacher/lessons/new/${section.id}" class="btn btn-primary">
                    <i class="fas fa-plus me-1"></i> Add Lesson
                </a>
            </div>
        </div>

        <!-- Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <i class="fas fa-check-circle me-2"></i>${successMessage}
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
            </div>
        </c:if>

        <div class="card">
            <div class="card-header-custom">
                <h5 class="mb-0">Lessons List</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty lessons}">
                        <div class="list-group">
                            <c:forEach var="lesson" items="${lessons}">
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    <div>
                                        <i class="fas fa-play-circle" style="color: #3B82F6; margin-right: 10px;"></i>
                                        <strong>${lesson.title}</strong>
                                        <span class="badge badge-info ms-2">Order: ${lesson.orderIndex}</span>
                                    </div>
                                    <div class="d-flex gap-2">
                                        <a href="${pageContext.request.contextPath}/teacher/lessons/edit/${lesson.id}" class="btn btn-sm btn-outline">
                                            <i class="fas fa-edit"></i> Edit
                                        </a>
                                        <button type="button" class="btn btn-sm btn-outline" style="color: #EF4444; border-color: #EF4444;"
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
                                                Are you sure you want to delete "<c:out value="${lesson.title}" />"?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-outline" data-bs-dismiss="modal">Cancel</button>
                                                <form action="${pageContext.request.contextPath}/teacher/lessons" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="lessonId" value="${lesson.id}">
                                                    <input type="hidden" name="sectionId" value="${section.id}">
                                                    <button type="submit" class="btn btn-primary" style="background: #EF4444; border-color: #EF4444;">Delete</button>
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
                            <i class="fas fa-play-circle" style="font-size: 64px; color: #9CA3AF; margin-bottom: 20px;"></i>
                            <h4 style="color: #1F2937; margin-bottom: 10px;">No Lessons Yet</h4>
                            <p style="color: #6B7280; margin-bottom: 30px;">Start by creating your first lesson for this section.</p>
                            <a href="${pageContext.request.contextPath}/teacher/lessons/new/${section.id}" class="btn btn-primary">
                                <i class="fas fa-plus me-2"></i>Create First Lesson
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
