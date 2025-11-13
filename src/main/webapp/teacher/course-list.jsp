<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Courses - Teacher Portal</title>
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
    .course-header {
      display: flex;
      justify-content: space-between;
      align-items: start;
      margin-bottom: 15px;
    }
    .course-title {
      font-size: 20px;
      font-weight: 600;
      color: #1F2937;
      margin-bottom: 10px;
    }
    .course-description {
      color: #6B7280;
      font-size: 14px;
      line-height: 1.6;
      margin-bottom: 15px;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
    .course-meta {
      display: flex;
      gap: 10px;
      margin-bottom: 20px;
      flex-wrap: wrap;
    }
    .course-actions {
      display: flex;
      gap: 8px;
      flex-wrap: wrap;
    }
    .course-actions .btn {
      flex: 1;
      min-width: 80px;
    }
    .course-thumbnail {
      width: 80px;
      height: 80px;
      border-radius: 8px;
      object-fit: cover;
    }
    .course-thumbnail-placeholder {
      width: 80px;
      height: 80px;
      border-radius: 8px;
      background: #F3F4F6;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #9CA3AF;
    }
  </style>
</head>
<body>
  <jsp:include page="/includes/teacher-sidebar.jsp">
    <jsp:param name="active" value="courses"/>
  </jsp:include>
  <jsp:include page="/includes/teacher-header.jsp" />

  <div class="main-content">
    <div class="card">
      <div class="card-header-custom">
        <h3>My Courses</h3>
        <a href="${pageContext.request.contextPath}/teacher/courses/new" class="btn btn-primary">
          <i class="fas fa-plus me-2"></i>New Course
        </a>
      </div>
    </div>

    <c:if test="${not empty courses}">
      <div class="courses-grid">
        <c:forEach var="course" items="${courses}">
          <div class="course-card">
            <div class="course-header">
              <div style="flex: 1;">
                <div class="course-title">${course.title}</div>
                <div class="course-meta">
                  <span class="badge ${course.isPublished ? 'badge-success' : 'badge-warning'}">
                    ${course.isPublished ? 'Published' : 'Draft'}
                  </span>
                  <span class="badge badge-info">${course.level}</span>
                </div>
              </div>
              <c:choose>
                <c:when test="${not empty course.thumbnail}">
                  <img src="${course.thumbnail}" alt="${course.title}" class="course-thumbnail">
                </c:when>
                <c:otherwise>
                  <div class="course-thumbnail-placeholder">
                    <i class="fas fa-book fa-2x"></i>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
            
            <p class="course-description">
              ${fn:length(course.description) > 100 ? fn:substring(course.description, 0, 100) : course.description}${fn:length(course.description) > 100 ? '...' : ''}
            </p>
            
            <div class="course-actions">
              <a href="${pageContext.request.contextPath}/teacher/courses/edit/${course.id}" class="btn btn-outline">
                <i class="fas fa-edit"></i> Edit
              </a>
              <a href="${pageContext.request.contextPath}/teacher/sections/course/${course.id}" class="btn btn-outline">
                <i class="fas fa-folder"></i> Sections
              </a>
              <a href="${pageContext.request.contextPath}/course-view?courseId=${course.id}" class="btn btn-outline" target="_blank">
                <i class="fas fa-eye"></i> View
              </a>
              <a href="${pageContext.request.contextPath}/teacher/courses/delete/${course.id}" class="btn btn-outline" style="color: #EF4444; border-color: #EF4444;"
                 onclick="return confirm('Are you sure you want to delete this course?')">
                <i class="fas fa-trash"></i> Delete
              </a>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:if>

    <c:if test="${empty courses}">
      <div class="card" style="text-align: center; padding: 60px 20px;">
        <div style="font-size: 64px; margin-bottom: 20px; color: #9CA3AF;">
          <i class="fas fa-book"></i>
        </div>
        <h3 style="color: #1F2937; margin-bottom: 10px;">No Courses Found</h3>
        <p style="color: #6B7280; margin-bottom: 30px;">You haven't created any courses yet.</p>
        <a href="${pageContext.request.contextPath}/teacher/courses/new" class="btn btn-primary">
          <i class="fas fa-plus me-2"></i>Create Your First Course
        </a>
      </div>
    </c:if>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
