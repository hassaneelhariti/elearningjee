<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${action == 'new' ? 'Create' : 'Edit'} Lesson - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/teacher-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <c:set var="ctx" value="${pageContext.request.contextPath}" />
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">${action == 'new' ? 'Create New' : 'Edit'} Lesson</h1>
                <a href="${ctx}/teacher/lessons/section/${section.id}" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-1"></i> Back to Lessons
                </a>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title mb-0">
                                Course: ${course.title} | Section: ${section.title}
                            </h5>
                        </div>
                        <div class="card-body">
                            <form method="post">
                                <input type="hidden" name="sectionId" value="${section.id}">
                                <c:if test="${action == 'edit'}">
                                    <input type="hidden" name="lessonId" value="${lesson.id}">
                                </c:if>

                                <div class="mb-3">
                                    <label for="title" class="form-label">Lesson Title *</label>
                                    <input type="text" class="form-control" id="title" name="title"
                                           value="${lesson.title}" required maxlength="255">
                                </div>

                                <div class="mb-3">
                                    <label for="orderIndex" class="form-label">Order Index</label>
                                    <input type="number" class="form-control" id="orderIndex" name="orderIndex"
                                           value="${lesson.orderIndex != null ? lesson.orderIndex : 1}" min="1" required>
                                    <div class="form-text">Determines the order of lessons in this section.</div>
                                </div>

                                <c:set var="resolvedContentType"
                                       value="${fn:toUpperCase(
                                               not empty param.contentType
                                                   ? param.contentType
                                                   : (not empty selectedContentType
                                                       ? selectedContentType
                                                       : (not empty lesson.contentType ? lesson.contentType : 'VIDEO'))
                                               )}"/>

                                <div class="mb-3">
                                    <label for="contentType" class="form-label">Content Type</label>
                                    <select class="form-select" id="contentType" name="contentType">
                                        <option value="VIDEO" ${resolvedContentType == 'VIDEO' ? 'selected' : ''}>Video</option>
                                        <option value="PDF" ${resolvedContentType == 'PDF' ? 'selected' : ''}>PDF</option>
                                    </select>
                                </div>

                                <div id="videoFields" class="content-fields mb-3" style="display:none;">
                                    <h6>Video Details</h6>
                                    <div class="mb-3">
                                        <label for="videoUrl" class="form-label">Video URL</label>
                                        <input type="url"
                                               class="form-control"
                                               id="videoUrl"
                                               name="videoUrl"
                                               placeholder="https://..."
                                               value="${not empty param.videoUrl ? param.videoUrl : (lesson.video != null ? lesson.video.url : '')}">
                                        <div class="form-text">Provide a streaming/video URL (YouTube, Vimeo, etc.).</div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="videoDuration" class="form-label">Duration (seconds)</label>
                                        <input type="number"
                                               class="form-control"
                                               id="videoDuration"
                                               name="videoDuration"
                                               min="0"
                                               value="${not empty param.videoDuration ? param.videoDuration : (lesson.video != null ? lesson.video.duration : '')}">
                                        <div class="form-text">Optional. Enter the length of the video in seconds.</div>
                                    </div>
                                </div>

                                <div id="pdfFields" class="content-fields mb-3" style="display:none;">
                                    <h6>PDF Details</h6>
                                    <div class="mb-3">
                                        <label for="pdfUrl" class="form-label">PDF URL</label>
                                        <input type="url"
                                               class="form-control"
                                               id="pdfUrl"
                                               name="pdfUrl"
                                               placeholder="https://..."
                                               value="${not empty param.pdfUrl ? param.pdfUrl : (lesson.pdf != null ? lesson.pdf.url : '')}">
                                        <div class="form-text">Provide a link to the PDF file.</div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="pdfFileName" class="form-label">PDF File Name</label>
                                        <input type="text"
                                               class="form-control"
                                               id="pdfFileName"
                                               name="pdfFileName"
                                               value="${not empty param.pdfFileName ? param.pdfFileName : (lesson.pdf != null ? lesson.pdf.fileName : '')}">
                                        <div class="form-text">Displayed name for the PDF (e.g., "Chapter 1 Notes").</div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="pdfFileSize" class="form-label">PDF Size (KB)</label>
                                        <input type="number"
                                               class="form-control"
                                               id="pdfFileSize"
                                               name="pdfFileSize"
                                               min="0"
                                               value="${not empty param.pdfFileSize ? param.pdfFileSize : (lesson.pdf != null && lesson.pdf.fileSize != null ? lesson.pdf.fileSize : '')}">
                                        <div class="form-text">Optional. Approximate file size in kilobytes.</div>
                                    </div>
                                </div>

                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="${ctx}/teacher/lessons/section/${section.id}" class="btn btn-secondary me-md-2">Cancel</a>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save me-1"></i>
                                        ${action == 'new' ? 'Create Lesson' : 'Update Lesson'}
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (function () {
        const contentTypeSelect = document.getElementById('contentType');
        const videoFields = document.getElementById('videoFields');
        const pdfFields = document.getElementById('pdfFields');

        function toggleFields() {
            const value = (contentTypeSelect.value || '').toUpperCase();
            if (value === 'PDF') {
                pdfFields.style.display = 'block';
                videoFields.style.display = 'none';
            } else {
                videoFields.style.display = 'block';
                pdfFields.style.display = 'none';
            }
        }

        contentTypeSelect.addEventListener('change', toggleFields);
        toggleFields();
    })();
</script>
</body>
</html>