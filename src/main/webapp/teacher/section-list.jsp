<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Sections - ${course.title}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <jsp:include page="../includes/teacher-sidebar.jsp" />

    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Manage Sections: ${course.title}</h1>
        <div>
          <a href="../courses/detail/${course.id}" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left me-1"></i> Back to Course
          </a>
        </div>
      </div>

      <!-- Success/Error Messages -->
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

      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
              <h5 class="card-title mb-0">Course Sections</h5>
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
                        <div class="d-flex align-items-center">
                          <a class="btn btn-sm btn-outline-success me-2"
                             href="${pageContext.request.contextPath}/teacher/lessons/section/${section.id}">
                            <i class="fas fa-play-circle"></i> Lessons
                          </a>
                          <button class="btn btn-sm btn-outline-primary me-1"
                                  onclick="editSection(${section.id}, '${section.title}', ${section.orderIndex})">
                            <i class="fas fa-edit"></i> Edit
                          </button>
                          <!-- Update form action to use proper context path -->
                          <form action="${pageContext.request.contextPath}/teacher/sections" method="post">                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="courseId" value="${course.id}">
                            <input type="hidden" name="sectionId" value="${section.id}">
                            <button type="submit" class="btn btn-sm btn-outline-danger"
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
                    <i class="fas fa-folder-open fa-3x text-muted mb-3"></i>
                    <p class="text-muted">No sections found for this course.</p>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addSectionModal">
                      <i class="fas fa-plus me-1"></i> Create Your First Section
                    </button>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>

<!-- Add Section Modal -->
<div class="modal fade" id="addSectionModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- Update form action to use proper context path -->
      <form action="${pageContext.request.contextPath}/teacher/sections" method="post">        <div class="modal-header">
          <h5 class="modal-title">Add New Section</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="action" value="add">
          <input type="hidden" name="courseId" value="${course.id}">

          <div class="mb-3">
            <label for="title" class="form-label">Section Title</label>
            <input type="text" class="form-control" id="title" name="title" required>
          </div>
          <div class="mb-3">
            <label for="orderIndex" class="form-label">Order Index</label>
            <input type="number" class="form-control" id="orderIndex" name="orderIndex" value="1" min="1" required>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
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
      <!-- Update form action to use proper context path -->
      <form action="${pageContext.request.contextPath}/teacher/sections" method="post">        <div class="modal-header">
          <h5 class="modal-title">Edit Section</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="action" value="edit">
          <input type="hidden" name="courseId" value="${course.id}">
          <input type="hidden" name="sectionId" id="editSectionId">

          <div class="mb-3">
            <label for="editTitle" class="form-label">Section Title</label>
            <input type="text" class="form-control" id="editTitle" name="title" required>
          </div>
          <div class="mb-3">
            <label for="editOrderIndex" class="form-label">Order Index</label>
            <input type="number" class="form-control" id="editOrderIndex" name="orderIndex" min="1" required>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
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