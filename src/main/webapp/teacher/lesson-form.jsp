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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <style>
        .breadcrumb {
            background: transparent;
            padding: 0;
        }
        .breadcrumb-item a {
            color: #3B82F6;
            text-decoration: none;
        }
        .breadcrumb-item a:hover {
            text-decoration: underline;
        }
        .breadcrumb-item.active {
            color: #6B7280;
        }
    </style>
</head>
<body>
    <jsp:include page="/includes/teacher-sidebar.jsp">
        <jsp:param name="active" value="courses" />
    </jsp:include>
    <jsp:include page="/includes/teacher-header.jsp" />

    <div class="main-content">
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
        
        <!-- Breadcrumb and Back Button -->
        <div class="card mb-3">
            <div class="card-body">
                <nav aria-label="breadcrumb" style="margin-bottom: 15px;">
                    <ol class="breadcrumb mb-0" style="background: transparent; padding: 0;">
                        <li class="breadcrumb-item"><a href="${ctx}/teacher/dashboard">Dashboard</a></li>
                        <li class="breadcrumb-item"><a href="${ctx}/teacher/courses/list">My Courses</a></li>
                        <li class="breadcrumb-item"><a href="${ctx}/teacher/sections/course/${course.id}">Sections</a></li>
                        <li class="breadcrumb-item"><a href="${ctx}/teacher/lessons/section/${section.id}">Lessons</a></li>
                        <li class="breadcrumb-item active">${action == 'new' ? 'Create' : 'Edit'} Lesson</li>
                    </ol>
                </nav>
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h3 class="mb-1">${action == 'new' ? 'Create New' : 'Edit'} Lesson</h3>
                        <p class="text-muted mb-0" style="font-size: 14px;">
                            <i class="fas fa-book me-1"></i>Course: ${course.title} | 
                            <i class="fas fa-folder me-1"></i>Section: ${section.title}
                        </p>
                    </div>
                    <a href="${ctx}/teacher/lessons/section/${section.id}" class="btn btn-outline">
                        <i class="fas fa-arrow-left me-2"></i>Back to Lessons
                    </a>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <form method="post">
                    <input type="hidden" name="sectionId" value="${section.id}">
                    <c:if test="${action == 'edit'}">
                        <input type="hidden" name="lessonId" value="${lesson.id}">
                    </c:if>

                    <div class="form-group">
                        <label for="title" class="form-label">Lesson Title *</label>
                        <input type="text" class="form-control" id="title" name="title"
                               value="${lesson.title}" required maxlength="255">
                    </div>

                    <div class="form-group">
                        <label for="orderIndex" class="form-label">Order Index</label>
                        <input type="number" class="form-control" id="orderIndex" name="orderIndex"
                               value="${lesson.orderIndex != null ? lesson.orderIndex : 1}" min="1" required>
                        <small class="form-text" style="color: #6B7280; font-size: 12px;">Determines the order of lessons in this section.</small>
                    </div>

                    <c:set var="resolvedContentType"
                           value="${fn:toUpperCase(
                                   not empty param.contentType
                                       ? param.contentType
                                       : (not empty selectedContentType
                                           ? selectedContentType
                                           : (not empty lesson.contentType ? lesson.contentType : 'VIDEO'))
                                   )}"/>

                    <div class="form-group">
                        <label for="contentType" class="form-label">Content Type</label>
                        <select class="form-control" id="contentType" name="contentType">
                            <option value="VIDEO" ${resolvedContentType == 'VIDEO' ? 'selected' : ''}>Video</option>
                            <option value="PDF" ${resolvedContentType == 'PDF' ? 'selected' : ''}>PDF</option>
                        </select>
                    </div>

                    <div id="videoFields" class="content-fields" style="display:none;">
                        <h6 style="color: #1F2937; margin-bottom: 15px; margin-top: 20px;">Video Details</h6>
                        <div class="form-group">
                            <label for="videoUrl" class="form-label">Video URL</label>
                            <input type="url"
                                   class="form-control"
                                   id="videoUrl"
                                   name="videoUrl"
                                   placeholder="https://..."
                                   value="${not empty param.videoUrl ? param.videoUrl : (lesson.video != null ? lesson.video.url : '')}">
                            <small class="form-text" style="color: #6B7280; font-size: 12px;">Provide a streaming/video URL (YouTube, Vimeo, etc.).</small>
                        </div>
                        <div class="form-group">
                            <label for="videoDuration" class="form-label">Duration (seconds)</label>
                            <input type="number"
                                   class="form-control"
                                   id="videoDuration"
                                   name="videoDuration"
                                   min="0"
                                   value="${not empty param.videoDuration ? param.videoDuration : (lesson.video != null ? lesson.video.duration : '')}">
                            <small class="form-text" style="color: #6B7280; font-size: 12px;">Optional. Enter the length of the video in seconds.</small>
                        </div>
                    </div>

                    <div id="pdfFields" class="content-fields" style="display:none;">
                        <h6 style="color: #1F2937; margin-bottom: 15px; margin-top: 20px;">PDF Details</h6>
                        <div class="form-group">
                            <label for="pdfUrl" class="form-label">PDF URL</label>
                            <input type="url"
                                   class="form-control"
                                   id="pdfUrl"
                                   name="pdfUrl"
                                   placeholder="https://..."
                                   value="${not empty param.pdfUrl ? param.pdfUrl : (lesson.pdf != null ? lesson.pdf.url : '')}">
                            <small class="form-text" style="color: #6B7280; font-size: 12px;">Provide a link to the PDF file.</small>
                        </div>
                        <div class="form-group">
                            <label for="pdfFileName" class="form-label">PDF File Name</label>
                            <input type="text"
                                   class="form-control"
                                   id="pdfFileName"
                                   name="pdfFileName"
                                   value="${not empty param.pdfFileName ? param.pdfFileName : (lesson.pdf != null ? lesson.pdf.fileName : '')}">
                            <small class="form-text" style="color: #6B7280; font-size: 12px;">Displayed name for the PDF (e.g., "Chapter 1 Notes").</small>
                        </div>
                        <div class="form-group">
                            <label for="pdfFileSize" class="form-label">PDF Size (KB)</label>
                            <input type="number"
                                   class="form-control"
                                   id="pdfFileSize"
                                   name="pdfFileSize"
                                   min="0"
                                   value="${not empty param.pdfFileSize ? param.pdfFileSize : (lesson.pdf != null && lesson.pdf.fileSize != null ? lesson.pdf.fileSize : '')}">
                            <small class="form-text" style="color: #6B7280; font-size: 12px;">Optional. Approximate file size in kilobytes.</small>
                        </div>
                    </div>

                    <div class="d-flex gap-2 justify-content-between" style="margin-top: 30px; padding-top: 20px; border-top: 1px solid #E5E7EB;">
                        <a href="${ctx}/teacher/lessons/section/${section.id}" class="btn btn-outline">
                            <i class="fas fa-arrow-left me-2"></i>Cancel & Return
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>
                            ${action == 'new' ? 'Create Lesson' : 'Update Lesson'}
                        </button>
                    </div>
                </form>
            </div>
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
