<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Courses - Teacher Portal</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp">
  <jsp:param name="active" value="courses"/>
</jsp:include>

<div class="container-fluid mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 text-gray-800">My Courses</h1>
    <a href="new" class="btn btn-primary">
      <i class="fas fa-plus me-1"></i>New Course
    </a>
  </div>

  <div class="row">
    <c:if test="${not empty courses}">
      <c:forEach var="course" items="${courses}">
        <div class="col-xl-4 col-md-6 mb-4">
          <div class="card border-left-primary shadow h-100">
            <div class="card-body">
              <div class="row no-gutters align-items-center">
                <div class="col mr-2">
                  <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                      ${course.level}
                  </div>
                  <div class="h5 font-weight-bold text-gray-800 mb-1">${course.title}</div>
                  <div class="text-muted small mb-2">
                      ${course.description.length() > 100 ? course.description.substring(0, 100) + '...' : course.description}
                  </div>
                  <div class="d-flex align-items-center mb-2">
                    <c:choose>
                      <c:when test="${course.isPublished}">
                        <span class="badge bg-success me-2">Published</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge bg-warning me-2">Draft</span>
                      </c:otherwise>
                    </c:choose>
                    <small class="text-muted">
                      <i class="fas fa-calendar me-1"></i>${course.dateCreated}
                    </small>
                  </div>
                </div>
                <div class="col-auto">
                  <c:if test="${not empty course.thumbnail}">
                    <img src="${course.thumbnail}" alt="${course.title}"
                         class="img-fluid rounded" style="width: 80px; height: 80px; object-fit: cover;">
                  </c:if>
                  <c:if test="${empty course.thumbnail}">
                    <div class="bg-light rounded d-flex align-items-center justify-content-center"
                         style="width: 80px; height: 80px;">
                      <i class="fas fa-book text-muted fa-2x"></i>
                    </div>
                  </c:if>
                </div>
              </div>
              <div class="mt-3">
                <div class="btn-group w-100">
                  <a href="edit/${course.id}" class="btn btn-sm btn-outline-primary">
                    <i class="fas fa-edit"></i>
                  </a>
                  <a href="../sections/course/${course.id}" class="btn btn-sm btn-outline-info">
                    <i class="fas fa-folder"></i> Sections
                  </a>
                  <a href="../course-view?courseId=${course.id}" class="btn btn-sm btn-outline-success" target="_blank">
                    <i class="fas fa-eye"></i>
                  </a>
                  <a href="delete/${course.id}" class="btn btn-sm btn-outline-danger"
                     onclick="return confirm('Are you sure you want to delete this course?')">
                    <i class="fas fa-trash"></i>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>
    </c:if>

    <c:if test="${empty courses}">
      <div class="col-12">
        <div class="card shadow">
          <div class="card-body text-center py-5">
            <i class="fas fa-book fa-4x text-muted mb-3"></i>
            <h4 class="text-muted">No Courses Found</h4>
            <p class="text-muted mb-4">You haven't created any courses yet.</p>
            <a href="new" class="btn btn-primary btn-lg">
              <i class="fas fa-plus me-2"></i>Create Your First Course
            </a>
          </div>
        </div>
      </div>
    </c:if>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>