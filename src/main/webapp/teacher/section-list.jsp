<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Sections - ${course.title}</title>
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
        <h3>Manage Sections: ${course.title}</h3>
        <a href="${pageContext.request.contextPath}/teacher/courses/list" class="btn btn-outline">
          <i class="fas fa-arrow-left me-1"></i> Back to Courses
        </a>
      </div>
    </div>

    <!-- Success/Error Messages -->
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
        <h5 class="mb-0">Course Sections</h5>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addSectionModal">
          <i class="fas fa-plus me-1"></i> Add New Section
        </button>
      </div>
      <div class="card-body">
        <c:choose>
          <c:when test="${not empty sections}">
            <div class="list-group">
              <c:forEach var="section" items="${sections}">
                <div class="list-group-item d-flex justify-content-between align-items-center">
                  <div>
                    <h6 class="mb-1">${section.title}</h6>
                    <small class="text-muted">Order: ${section.orderIndex}</small>
                  </div>
                  <div class="d-flex align-items-center gap-2">
                    <a class="btn btn-sm btn-success"
                       href="${pageContext.request.contextPath}/teacher/lessons/section/${section.id}">
                      <i class="fas fa-play-circle"></i> Lessons
                    </a>
                    <button class="btn btn-sm btn-outline"
                            onclick="editSection(${section.id}, '<c:out value="${fn:replace(section.title, \"'\", \"\\'\")}" escapeXml="true" />', ${section.orderIndex})">
                      <i class="fas fa-edit"></i> Edit
                    </button>
                    <form action="${pageContext.request.contextPath}/teacher/sections" method="post" style="display: inline;">
                      <input type="hidden" name="action" value="delete">
                      <input type="hidden" name="courseId" value="${course.id}">
                      <input type="hidden" name="sectionId" value="${section.id}">
                      <button type="submit" class="btn btn-sm btn-outline" style="color: #EF4444; border-color: #EF4444;"
                              onclick="return confirm('Are you sure you want to delete this section?')">
                        <i class="fas fa-trash"></i> Delete
                      </button>
                    </form>
                  </div>
                </div>
              </c:forEach>
            </div>
          </c:when>
          <c:otherwise>
            <div class="text-center py-4">
              <i class="fas fa-folder-open fa-3x" style="color: #9CA3AF; margin-bottom: 20px;"></i>
              <p style="color: #6B7280;">No sections found for this course.</p>
              <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addSectionModal">
                <i class="fas fa-plus me-1"></i> Create Your First Section
              </button>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>

<!-- Add Section Modal -->
<div class="modal fade" id="addSectionModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/teacher/sections" method="post">
        <div class="modal-header">
          <h5 class="modal-title">Add New Section</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="action" value="add">
          <input type="hidden" name="courseId" value="${course.id}">

          <div class="form-group">
            <label for="title" class="form-label">Section Title</label>
            <input type="text" class="form-control" id="title" name="title" required>
          </div>
          <div class="form-group">
            <label for="orderIndex" class="form-label">Order Index</label>
            <input type="number" class="form-control" id="orderIndex" name="orderIndex" value="1" min="1" required>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-outline" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Add Section</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Edit Section Modal -->
<div class="modal fade" id="editSectionModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/teacher/sections" method="post">
        <div class="modal-header">
          <h5 class="modal-title">Edit Section</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="action" value="edit">
          <input type="hidden" name="courseId" value="${course.id}">
          <input type="hidden" name="sectionId" id="editSectionId">

          <div class="form-group">
            <label for="editTitle" class="form-label">Section Title</label>
            <input type="text" class="form-control" id="editTitle" name="title" required>
          </div>
          <div class="form-group">
            <label for="editOrderIndex" class="form-label">Order Index</label>
            <input type="number" class="form-control" id="editOrderIndex" name="orderIndex" min="1" required>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-outline" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary">Update Section</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  function editSection(sectionId, title, orderIndex) {
    document.getElementById('editSectionId').value = sectionId;
    document.getElementById('editTitle').value = title;
    document.getElementById('editOrderIndex').value = orderIndex;

    var editModal = new bootstrap.Modal(document.getElementById('editSectionModal'));
    editModal.show();
  }
</script>
</body>
</html>
