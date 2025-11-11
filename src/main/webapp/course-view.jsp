<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty course.title ? course.title : 'Course'} - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar {
            background-color: #f8f9fa;
            height: 100vh;
            position: fixed;
            overflow-y: auto;
        }
        .main-content {
            margin-left: 25%;
        }
        .progress-bar {
            background-color: #28a745;
        }
        .section-header {
            background-color: #e9ecef;
            cursor: pointer;
        }
        .lesson-item {
            border-left: 3px solid #007bff;
            margin-left: 15px;
        }
        .completed {
            color: #28a745;
        }
        .video-container {
            position: relative;
            padding-bottom: 56.25%;
            height: 0;
            overflow: hidden;
        }
        .video-container iframe {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
        .course-thumbnail {
            max-height: 200px;
            object-fit: cover;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 sidebar p-0">
            <div class="p-3 border-bottom">
                <h5 class="mb-0">${not empty course.title ? course.title : 'Course'}</h5>
                <div class="progress mt-2" style="height: 5px;">
                    <div class="progress-bar" style="width: ${not empty progressPercentage ? progressPercentage : 0}%"></div>
                </div>
                <small class="text-muted">${not empty progressPercentage ? progressPercentage : 0}% Complete</small>
            </div>

            <!-- Course Content -->
            <div class="p-3">
                <h6>Course Content</h6>
                <c:if test="${not empty sections}">
                    <c:forEach var="section" items="${sections}" varStatus="sectionStatus">
                        <div class="card mb-2">
                            <div class="card-header section-header"
                                 data-bs-toggle="collapse"
                                 data-bs-target="#section${section.id}">
                                <div class="d-flex justify-content-between align-items-center">
                                    <span>
                                        <i class="fas fa-chevron-down me-2"></i>
                                        Section ${sectionStatus.index + 1}: ${section.title}
                                    </span>
                                    <span class="badge bg-primary">
                                        <c:choose>
                                            <c:when test="${not empty section.lessons}">${section.lessons.size()}</c:when>
                                            <c:otherwise>0</c:otherwise>
                                        </c:choose> lessons
                                    </span>
                                </div>
                            </div>
                            <div class="collapse" id="section${section.id}">
                                <div class="card-body p-2">
                                    <c:if test="${not empty section.lessons}">
                                        <c:forEach var="lesson" items="${section.lessons}">
                                            <div class="lesson-item p-2 mb-1 ${lessonProgressMap[lesson.id] ? 'completed' : ''}">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <a href="course-view?courseId=${course.id}&lessonId=${lesson.id}"
                                                       class="text-decoration-none ${lessonProgressMap[lesson.id] ? 'text-success' : 'text-dark'}">
                                                        <i class="fas ${lessonProgressMap[lesson.id] ? 'fa-check-circle' : 'fa-play-circle'} me-2"></i>
                                                            ${lesson.title}
                                                    </a>
                                                    <small class="text-muted">
                                                        <!-- Video Icon with Error Handling -->
                                                        <c:catch var="videoError">
                                                            <c:if test="${not empty lesson.video}">
                                                                <i class="fas fa-video me-1"></i>
                                                            </c:if>
                                                        </c:catch>

                                                        <!-- PDF Icon with Error Handling -->
                                                        <c:catch var="pdfError">
                                                            <c:if test="${not empty lesson.pdf}">
                                                                <i class="fas fa-file-pdf ms-2"></i>
                                                            </c:if>
                                                        </c:catch>
                                                    </small>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <!-- Main Content -->
        <div class="col-md-9 main-content">
            <!-- Header -->
            <div class="p-3 border-bottom bg-light">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb mb-0">
                        <li class="breadcrumb-item"><a href="courses">Courses</a></li>
                        <li class="breadcrumb-item active">${not empty course.title ? course.title : 'Course'}</li>
                    </ol>
                </nav>
            </div>

            <!-- Lesson Content -->
            <c:choose>
                <c:when test="${not empty currentLesson}">
                    <div class="p-4">
                        <!-- Lesson Header -->
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <div>
                                <h3>${currentLesson.title}</h3>
                                <c:if test="${not empty currentLesson.section and not empty currentLesson.section.title}">
                                    <p class="text-muted mb-0">Section: ${currentLesson.section.title}</p>
                                </c:if>
                            </div>
                            <div>
                                <c:if test="${not lessonProgressMap[currentLesson.id]}">
                                    <button class="btn btn-success btn-sm" onclick="markAsCompleted(${currentLesson.id})">
                                        <i class="fas fa-check me-1"></i>Mark as Complete
                                    </button>
                                </c:if>
                                <c:if test="${lessonProgressMap[currentLesson.id]}">
                                    <span class="badge bg-success">
                                        <i class="fas fa-check me-1"></i>Completed
                                    </span>
                                </c:if>
                            </div>
                        </div>

                        <!-- Video Content with Error Handling -->
                        <c:catch var="videoError">
                            <c:if test="${not empty currentLesson.video and not empty currentLesson.video.url}">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <h5 class="mb-0">
                                            <i class="fas fa-video me-2"></i>Video Content
                                        </h5>
                                    </div>
                                    <div class="card-body p-0">
                                        <div class="video-container">
                                            <iframe src="${currentLesson.video.url}"
                                                    frameborder="0"
                                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                                    allowfullscreen>
                                            </iframe>
                                        </div>
                                        <c:if test="${not empty currentLesson.video.duration}">
                                            <div class="p-3">
                                                <small class="text-muted">
                                                    <i class="fas fa-clock me-1"></i>
                                                    Duration: ${currentLesson.video.duration} seconds
                                                </small>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </c:if>
                        </c:catch>

                        <!-- PDF Content with Error Handling -->
                        <c:catch var="pdfError">
                            <c:if test="${not empty currentLesson.pdf and not empty currentLesson.pdf.url}">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <h5 class="mb-0">
                                            <i class="fas fa-file-pdf text-danger me-2"></i>
                                                ${not empty currentLesson.pdf.fileName ? currentLesson.pdf.fileName : 'Download PDF'}
                                        </h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="alert alert-info">
                                            <i class="fas fa-info-circle me-2"></i>
                                            Click the download button below to access the PDF material.
                                        </div>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <c:if test="${not empty currentLesson.pdf.fileSize}">
                                                    <small class="text-muted">
                                                        File size: ${currentLesson.pdf.fileSize} bytes
                                                    </small>
                                                </c:if>
                                            </div>
                                            <a href="${currentLesson.pdf.url}"
                                               class="btn btn-outline-primary"
                                               target="_blank"
                                               download="${not empty currentLesson.pdf.fileName ? currentLesson.pdf.fileName : 'document.pdf'}">
                                                <i class="fas fa-download me-1"></i>Download PDF
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:catch>

                        <!-- No Content Message -->
                        <c:if test="${(empty currentLesson.video or empty currentLesson.video.url) and (empty currentLesson.pdf or empty currentLesson.pdf.url)}">
                            <div class="alert alert-warning">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                This lesson doesn't have any content yet. Please check back later.
                            </div>
                        </c:if>

                        <!-- Navigation -->
                        <div class="d-flex justify-content-between mt-4">
                            <div>
                                <c:if test="${not empty prevLesson}">
                                    <a href="course-view?courseId=${course.id}&lessonId=${prevLesson.id}" class="btn btn-outline-primary">
                                        <i class="fas fa-arrow-left me-1"></i>Previous Lesson
                                    </a>
                                </c:if>
                            </div>
                            <div>
                                <c:if test="${not empty nextLesson}">
                                    <a href="course-view?courseId=${course.id}&lessonId=${nextLesson.id}" class="btn btn-primary">
                                        Next Lesson <i class="fas fa-arrow-right ms-1"></i>
                                    </a>
                                </c:if>
                                <c:if test="${empty nextLesson and not empty currentLesson}">
                                    <c:if test="${not empty progressPercentage and progressPercentage >= 100}">
                                        <button class="btn btn-success" onclick="completeCourse()">
                                            <i class="fas fa-graduation-cap me-1"></i>Complete Course
                                        </button>
                                    </c:if>
                                    <c:if test="${empty progressPercentage or progressPercentage < 100}">
                                        <div class="alert alert-info">
                                            <i class="fas fa-info-circle me-2"></i>
                                            Complete all lessons to finish the course.
                                        </div>
                                    </c:if>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:when>

                <c:otherwise>
                    <!-- Course Overview -->
                    <div class="p-4">
                        <div class="row">
                            <div class="col-md-8">
                                <!-- Course Header -->
                                <div class="d-flex align-items-start mb-4">
                                    <c:if test="${not empty course.thumbnail}">
                                        <img src="${course.thumbnail}"
                                             alt="${not empty course.title ? course.title : 'Course'}"
                                             class="course-thumbnail rounded me-4" style="width: 200px;">
                                    </c:if>
                                    <div>
                                        <h2>${not empty course.title ? course.title : 'Course'}</h2>
                                        <p class="lead mb-2">${not empty course.description ? course.description : 'No description available.'}</p>
                                        <div class="d-flex flex-wrap gap-2">
                                            <span class="badge bg-secondary">
                                                <c:choose>
                                                    <c:when test="${not empty course.level}">${course.level}</c:when>
                                                    <c:otherwise>Not specified</c:otherwise>
                                                </c:choose>
                                            </span>
                                            <c:choose>
                                                <c:when test="${course.isPublished}">
                                                    <span class="badge bg-success">Published</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-warning">Draft</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <!-- Course Information -->
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <h5 class="mb-0">
                                            <i class="fas fa-info-circle me-2"></i>Course Information
                                        </h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p><strong><i class="fas fa-signal me-2"></i>Level:</strong>
                                                    <c:choose>
                                                        <c:when test="${not empty course.level}">${course.level}</c:when>
                                                        <c:otherwise>Not specified</c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p><strong><i class="fas fa-user-tie me-2"></i>Instructor:</strong>
                                                    <c:choose>
                                                        <c:when test="${not empty course.teacher and not empty course.teacher.firstName}">
                                                            ${course.teacher.firstName} ${course.teacher.lastName}
                                                        </c:when>
                                                        <c:otherwise>Unknown</c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                            <div class="col-md-6">
                                                <p><strong><i class="fas fa-calendar me-2"></i>Created:</strong>
                                                    <c:choose>
                                                        <c:when test="${not empty course.dateCreated}">${course.dateCreated}</c:when>
                                                        <c:otherwise>Unknown</c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p><strong><i class="fas fa-folder me-2"></i>Sections:</strong>
                                                    <c:choose>
                                                        <c:when test="${not empty sections}">${sections.size()}</c:when>
                                                        <c:otherwise>0</c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p><strong><i class="fas fa-play-circle me-2"></i>Total Lessons:</strong>
                                                    <c:choose>
                                                        <c:when test="${not empty totalLessons}">${totalLessons}</c:when>
                                                        <c:otherwise>0</c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Learning Objectives -->
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="mb-0">
                                            <i class="fas fa-bullseye me-2"></i>What you'll learn
                                        </h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <ul class="list-unstyled">
                                                    <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Career development skills</li>
                                                    <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Professional English communication</li>
                                                    <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Job search strategies</li>
                                                </ul>
                                            </div>
                                            <div class="col-md-6">
                                                <ul class="list-unstyled">
                                                    <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Interview preparation</li>
                                                    <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Global career paths</li>
                                                    <li class="mb-2"><i class="fas fa-check text-success me-2"></i>Professional networking</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Sidebar Actions -->
                            <div class="col-md-4">
                                <div class="card sticky-top" style="top: 20px;">
                                    <div class="card-body text-center">
                                        <c:if test="${empty course.thumbnail}">
                                            <div class="bg-light rounded p-5 mb-3">
                                                <i class="fas fa-book-open fa-3x text-muted"></i>
                                            </div>
                                        </c:if>
                                        <h5>Ready to start learning?</h5>

                                        <c:choose>
                                            <c:when test="${not empty firstLesson}">
                                                <a href="course-view?courseId=${course.id}&lessonId=${firstLesson.id}"
                                                   class="btn btn-primary btn-lg w-100 mb-3">
                                                    <i class="fas fa-play me-1"></i>
                                                    <c:choose>
                                                        <c:when test="${not empty progressPercentage and progressPercentage == 0}">
                                                            Start Course
                                                        </c:when>
                                                        <c:when test="${not empty progressPercentage and progressPercentage > 0 and progressPercentage < 100}">
                                                            Continue Learning
                                                        </c:when>
                                                        <c:when test="${not empty progressPercentage and progressPercentage >= 100}">
                                                            Review Course
                                                        </c:when>
                                                        <c:otherwise>
                                                            Start Course
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-secondary btn-lg w-100 mb-3" disabled>
                                                    <i class="fas fa-play me-1"></i>No Lessons Available
                                                </button>
                                            </c:otherwise>
                                        </c:choose>

                                        <!-- Progress Section -->
                                        <div class="border-top pt-3">
                                            <div class="d-flex justify-content-between align-items-center mb-2">
                                                <span class="text-muted">Your Progress:</span>
                                                <strong>${not empty progressPercentage ? progressPercentage : 0}%</strong>
                                            </div>
                                            <div class="progress mb-3" style="height: 8px;">
                                                <div class="progress-bar" style="width: ${not empty progressPercentage ? progressPercentage : 0}%"></div>
                                            </div>

                                            <!-- Course Stats -->
                                            <div class="row text-center">
                                                <div class="col-6">
                                                    <div class="border-end">
                                                        <h6 class="mb-1">${not empty totalLessons ? totalLessons : 0}</h6>
                                                        <small class="text-muted">Lessons</small>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <h6 class="mb-1">
                                                        <c:choose>
                                                            <c:when test="${not empty sections}">${sections.size()}</c:when>
                                                            <c:otherwise>0</c:otherwise>
                                                        </c:choose>
                                                    </h6>
                                                    <small class="text-muted">Sections</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function markAsCompleted(lessonId) {
        console.log('Marking lesson as completed:', lessonId);

        fetch('mark-lesson-complete?lessonId=' + lessonId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        }).then(response => {
            console.log('Response status:', response.status);
            if (response.ok) {
                console.log('Lesson marked as completed successfully');
                location.reload();
            } else {
                console.error('Server returned error status:', response.status);
                if (response.status === 401) {
                    alert('Please log in again');
                    window.location.href = 'login';
                } else if (response.status === 404) {
                    alert('Enrollment not found. Please enroll in the course first.');
                } else {
                    alert('Error marking lesson as completed. Status: ' + response.status);
                }
            }
        }).catch(error => {
            console.error('Network error:', error);
            alert('Network error marking lesson as completed: ' + error.message);
        });
    }

    function completeCourse() {
        if (confirm('Are you sure you want to mark this course as completed?')) {
            fetch('complete-course?courseId=${course.id}', {
                method: 'POST'
            }).then(response => {
                if (response.ok) {
                    alert('Course completed successfully!');
                    window.location.href = 'courses';
                } else {
                    alert('Error completing course');
                }
            }).catch(error => {
                console.error('Error:', error);
                alert('Error completing course');
            });
        }
    }

    // Auto-expand current lesson's section
    document.addEventListener('DOMContentLoaded', function() {
        const currentLessonId = ${not empty currentLesson ? currentLesson.id : 'null'};
        if (currentLessonId) {
            const currentSection = document.querySelector('.lesson-item a[href*="lessonId=' + currentLessonId + '"]');
            if (currentSection) {
                const sectionCard = currentSection.closest('.card');
                const collapseElement = sectionCard.querySelector('.collapse');
                if (collapseElement) {
                    new bootstrap.Collapse(collapseElement, { toggle: true });
                }
            }
        }
    });
</script>
</body>
</html>