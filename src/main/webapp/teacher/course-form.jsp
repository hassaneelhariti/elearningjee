<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${action == 'edit' ? 'Edit Course' : 'Create New Course'} - Teacher Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="active" value="${action == 'edit' ? 'courses' : 'new'}"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row justify-content-center">
        <div class="col-xl-8 col-lg-10">
            <div class="card shadow">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">
                        <i class="fas ${action == 'edit' ? 'fa-edit' : 'fa-plus'} me-2"></i>
                        ${action == 'edit' ? 'Edit Course' : 'Create New Course'}
                    </h6>
                </div>
                <div class="card-body">
                    <form action="${action == 'edit' ? '' : 'new'}" method="post">
                        <c:if test="${action == 'edit'}">
                            <input type="hidden" name="courseId" value="${course.id}">
                        </c:if>

                        <div class="row">
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label for="title" class="form-label">Course Title *</label>
                                    <input type="text" class="form-control" id="title" name="title"
                                           value="${course.title}" required
                                           placeholder="Enter course title (e.g., 'English for Beginners')">
                                    <div class="form-text">Make it descriptive and engaging for students.</div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="level" class="form-label">Level *</label>
                                    <select class="form-select" id="level" name="level" required>
                                        <option value="">Select Level</option>
                                        <option value="Beginner" ${course.level == 'Beginner' ? 'selected' : ''}>Beginner</option>
                                        <option value="Intermediate" ${course.level == 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                                        <option value="Advanced" ${course.level == 'Advanced' ? 'selected' : ''}>Advanced</option>
                                        <option value="All Levels" ${course.level == 'All Levels' ? 'selected' : ''}>All Levels</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Course Description *</label>
                            <textarea class="form-control" id="description" name="description"
                                      rows="5" required
                                      placeholder="Describe what students will learn in this course...">${course.description}</textarea>
                            <div class="form-text">Be detailed about the learning outcomes and course content.</div>
                        </div>

                        <div class="mb-3">
                            <label for="thumbnail" class="form-label">Course Thumbnail URL</label>
                            <input type="url" class="form-control" id="thumbnail" name="thumbnail"
                                   value="${course.thumbnail}"
                                   placeholder="https://example.com/course-image.jpg">
                            <div class="form-text">Optional. Use a high-quality image that represents your course.</div>
                        </div>

                        <div class="mb-4">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="isPublished" name="isPublished"
                                ${course.isPublished ? 'checked' : ''}>
                                <label class="form-check-label fw-bold" for="isPublished">
                                    Publish this course
                                </label>
                            </div>
                            <div class="form-text">
                                Published courses are visible to students. Keep it unpublished while you're still working on the content.
                            </div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center">
                            <a href="list" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-1"></i>Back to Courses
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save me-1"></i>
                                ${action == 'edit' ? 'Update Course' : 'Create Course'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <c:if test="${action == 'edit'}">
                <div class="card shadow mt-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-warning">
                            <i class="fas fa-exclamation-triangle me-2"></i>Course Management
                        </h6>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <a href="../sections/course/${course.id}" class="btn btn-outline-info w-100 mb-2">
                                    <i class="fas fa-folder me-1"></i>Manage Sections
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="../course-view?courseId=${course.id}" class="btn btn-outline-success w-100 mb-2" target="_blank">
                                    <i class="fas fa-eye me-1"></i>Preview Course
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>