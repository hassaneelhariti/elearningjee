<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard for student - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
    <style>

        /* Welcome Section */
        .welcome-card {
            background: white;
            border-radius: 16px;
            padding: 30px;
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .welcome-text h2 {
            font-size: 28px;
            color: #1F2937;
            margin-bottom: 10px;
        }

        .welcome-text p {
            color: #6B7280;
            font-size: 16px;
            margin-bottom: 15px;
        }

        .welcome-text a {
            color: #3B82F6;
            text-decoration: none;
            font-weight: 500;
        }

        .welcome-illustration {
            width: 200px;
            height: 200px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 80px;
        }

        /* Performance Section */
        .performance-card {
            background: white;
            border-radius: 16px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .card-header-custom {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .card-header-custom h3 {
            font-size: 20px;
            color: #1F2937;
            font-weight: 600;
        }

        .best-lesson {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 12px;
            padding: 20px;
            color: white;
            margin-bottom: 25px;
        }

        .best-lesson-score {
            font-size: 48px;
            font-weight: 700;
            margin-bottom: 5px;
        }

        .best-lesson-name {
            font-size: 16px;
            opacity: 0.9;
        }

        .performance-chart {
            height: 250px;
            margin-top: 20px;
        }

        /* My Visit Section */
        .visit-card {
            background: white;
            border-radius: 16px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .visit-items {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-top: 20px;
        }

        .visit-item {
            text-align: center;
        }

        .visit-item-name {
            font-size: 12px;
            color: #6B7280;
            margin-top: 10px;
            font-weight: 500;
        }

        .circular-progress {
            position: relative;
            width: 100px;
            height: 100px;
            margin: 0 auto;
        }

        .circular-progress svg {
            transform: rotate(-90deg);
        }

        .circular-progress .progress-ring {
            fill: none;
            stroke-width: 8;
        }

        .circular-progress .progress-ring-bg {
            stroke: #E5E7EB;
        }

        .circular-progress .progress-ring-fill {
            stroke: #3B82F6;
            stroke-linecap: round;
            transition: stroke-dashoffset 0.5s;
        }

        .progress-percentage {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 18px;
            font-weight: 700;
            color: #1F2937;
        }

        /* Calendar Section */
        .calendar-card {
            background: white;
            border-radius: 16px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .timeline {
            margin-top: 20px;
        }

        .timeline-item {
            display: flex;
            gap: 15px;
            padding: 15px;
            border-left: 3px solid #3B82F6;
            background: #F9FAFB;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        .timeline-time {
            font-weight: 600;
            color: #1F2937;
            min-width: 60px;
        }

        .timeline-content h5 {
            font-size: 14px;
            color: #1F2937;
            margin-bottom: 5px;
        }

        .timeline-content p {
            font-size: 12px;
            color: #6B7280;
        }

        /* Linked Teachers */
        .teachers-card {
            background: white;
            border-radius: 16px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .teacher-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 15px;
            border-radius: 12px;
            margin-bottom: 15px;
            background: #F9FAFB;
            transition: all 0.3s;
        }

        .teacher-item:hover {
            background: #F3F4F6;
            transform: translateX(5px);
        }

        .teacher-avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 600;
            font-size: 18px;
        }

        .teacher-info {
            flex: 1;
        }

        .teacher-name {
            font-weight: 600;
            color: #1F2937;
            margin-bottom: 3px;
        }

        .teacher-subject {
            font-size: 12px;
            color: #6B7280;
        }

        .teacher-actions {
            display: flex;
            gap: 10px;
        }

        .teacher-action-btn {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            border: 1px solid #E5E7EB;
            background: white;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            color: #6B7280;
            transition: all 0.3s;
        }

        .teacher-action-btn:hover {
            background: #3B82F6;
            color: white;
            border-color: #3B82F6;
        }

        /* Upcoming Events */
        .events-card {
            background: white;
            border-radius: 16px;
            padding: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .event-item {
            padding: 15px;
            border-left: 3px solid #10B981;
            background: #F0FDF4;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        .event-item h5 {
            font-size: 14px;
            color: #1F2937;
            margin-bottom: 8px;
        }

        .event-date {
            font-size: 12px;
            color: #6B7280;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        /* Grid Layout */
        .dashboard-grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 30px;
            margin-bottom: 30px;
        }

        .dashboard-grid-2 {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
        }

        .see-all-link {
            color: #3B82F6;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
        }

        .see-all-link:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>
    <jsp:include page="/includes/student-sidebar.jsp">
        <jsp:param name="active" value="dashboard" />
    </jsp:include>
    <jsp:include page="/includes/student-header.jsp" />

    <div class="main-content">

        <!-- Welcome Section -->
        <div class="welcome-card">
            <div class="welcome-text">
                <h2>Hello ${not empty userFirstName ? userFirstName : 'Student'}!</h2>
                <p>You have ${not empty inProgressCourses ? inProgressCourses : 0} courses in progress. It is a lot of work for today! So let's start!</p>
                <a href="${pageContext.request.contextPath}/my-courses">review it →</a>
            </div>
            <div class="welcome-illustration">
                <i class="fas fa-user-graduate"></i>
            </div>
        </div>

        <!-- Dashboard Grid -->
        <div class="dashboard-grid">
            <!-- Left Column -->
            <div>
                <!-- Performance Section -->
                <div class="performance-card">
                    <div class="card-header-custom">
                        <h3>The best lessons:</h3>
                        <select class="dropdown-custom">
                            <option>December</option>
                            <option>November</option>
                        </select>
                    </div>
                    <c:choose>
                        <c:when test="${not empty coursePerformances and coursePerformances.size() > 0}">
                            <div class="best-lesson">
                                <div class="best-lesson-score"><fmt:formatNumber value="${coursePerformances[0].progress}" maxFractionDigits="1" minFractionDigits="1" /></div>
                                <div class="best-lesson-name">${coursePerformances[0].courseName}</div>
                            </div>
                            <div class="performance-chart">
                                <canvas id="performanceChart"></canvas>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="best-lesson">
                                <div class="best-lesson-score">0.0</div>
                                <div class="best-lesson-name">No courses yet</div>
                            </div>
                            <div class="performance-chart">
                                <canvas id="performanceChart"></canvas>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Linked Teachers -->
                <div class="teachers-card">
                    <div class="card-header-custom">
                        <h3>Linked Teachers</h3>
                        <a href="#" class="see-all-link">See all</a>
                    </div>
                    <c:choose>
                        <c:when test="${not empty linkedTeachers}">
                            <c:forEach var="teacher" items="${linkedTeachers}" varStatus="status">
                                <c:if test="${status.index < 2}">
                                    <div class="teacher-item">
                                        <div class="teacher-avatar">
                                            ${fn:substring(teacher.firstName, 0, 1)}${fn:substring(teacher.lastName, 0, 1)}
                                        </div>
                                        <div class="teacher-info">
                                            <div class="teacher-name">
                                                ${teacher.firstName} ${teacher.lastName}
                                                <c:if test="${status.index == 0}"> (mentor)</c:if>
                                            </div>
                                            <div class="teacher-subject">
                                                <c:choose>
                                                    <c:when test="${status.index == 0}">Science</c:when>
                                                    <c:otherwise>Foreign language</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="teacher-actions">
                                            <div class="teacher-action-btn">
                                                <i class="fas fa-phone"></i>
                                            </div>
                                            <div class="teacher-action-btn">
                                                <i class="fas fa-comment"></i>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="color: #6B7280; text-align: center; padding: 20px;">No teachers linked yet</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Right Column -->
            <div>
                <!-- My Visit Section -->
                <div class="visit-card">
                    <div class="card-header-custom">
                        <h3>My visit</h3>
                        <select class="dropdown-custom">
                            <option>December</option>
                            <option>November</option>
                        </select>
                    </div>
                    <div class="visit-items">
                        <c:choose>
                            <c:when test="${not empty enrollments}">
                                <c:forEach var="enrollment" items="${enrollments}" varStatus="status">
                                    <c:if test="${status.index < 6}">
                                        <div class="visit-item">
                                            <div class="circular-progress" data-progress="${enrollment.progress != null ? enrollment.progress : 0}">
                                                <svg width="100" height="100">
                                                    <circle class="progress-ring progress-ring-bg" cx="50" cy="50" r="42"></circle>
                                                    <circle class="progress-ring progress-ring-fill" cx="50" cy="50" r="42" 
                                                            stroke-dasharray="${2 * 3.14159 * 42}" 
                                                            stroke-dashoffset="${2 * 3.14159 * 42 * (1 - (enrollment.progress != null ? enrollment.progress : 0) / 100)}"></circle>
                                                </svg>
                                                <div class="progress-percentage"><fmt:formatNumber value="${enrollment.progress != null ? enrollment.progress : 0}" maxFractionDigits="0" />%</div>
                                            </div>
                                            <div class="visit-item-name">
                                                ${fn:length(enrollment.course.title) > 15 ? fn:substring(enrollment.course.title, 0, 15) : enrollment.course.title}...
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p style="color: #6B7280; text-align: center; grid-column: 1/-1; padding: 20px;">No courses enrolled</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Calendar Section -->
                <div class="calendar-card">
                    <div class="card-header-custom">
                        <h3>Calendar</h3>
                        <select class="dropdown-custom">
                            <option>Today</option>
                            <option>This Week</option>
                        </select>
                    </div>
                    <div class="timeline">
                        <c:choose>
                            <c:when test="${not empty upcomingLessons}">
                                <c:forEach var="lesson" items="${upcomingLessons}" varStatus="status">
                                    <c:if test="${status.index < 4}">
                                        <div class="timeline-item">
                                            <div class="timeline-time">${lesson.startTime}</div>
                                            <div class="timeline-content">
                                                <h5>${lesson.lessonTitle}</h5>
                                                <p>${lesson.startTime} - ${lesson.endTime}, ${status.index + 20} students</p>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p style="color: #6B7280; text-align: center; padding: 20px;">No upcoming lessons</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Upcoming Events -->
                <div class="events-card">
                    <div class="card-header-custom">
                        <h3>Upcoming events</h3>
                        <a href="#" class="see-all-link">See all</a>
                    </div>
                    <div class="event-item">
                        <h5>The main event in your life 'Robot Fest' will coming soon in.....</h5>
                        <div class="event-date">
                            <i class="fas fa-calendar"></i>
                            <span>December 14, 2023, 12:00 PM</span>
                        </div>
                    </div>
                    <div class="event-item">
                        <h5>Webinar of new tools in Minecraft</h5>
                        <div class="event-date">
                            <i class="fas fa-calendar"></i>
                            <span>December 21, 2023, 11:00 PM</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Performance Chart
        const perfCtx = document.getElementById('performanceChart').getContext('2d');
        <c:choose>
            <c:when test="${not empty coursePerformances}">
                const lessonLabels = [];
                const lessonScores = [];
                <c:forEach var="perf" items="${coursePerformances}">
                    lessonLabels.push('<c:out value="${fn:length(perf.courseName) > 20 ? fn:substring(perf.courseName, 0, 20) : perf.courseName}" escapeXml="true" />');
                    lessonScores.push(${perf.progress});
                </c:forEach>
                
                new Chart(perfCtx, {
                    type: 'bar',
                    data: {
                        labels: lessonLabels,
                        datasets: [{
                            label: 'Score',
                            data: lessonScores,
                            backgroundColor: '#3B82F6',
                            borderRadius: 8,
                            borderSkipped: false
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(context) {
                                        return 'Score: ' + context.parsed.y.toFixed(1);
                                    }
                                }
                            }
                        },
                        scales: {
                            y: {
                                beginAtZero: true,
                                max: 100,
                                ticks: {
                                    callback: function(value) {
                                        return value.toFixed(1);
                                    }
                                },
                                grid: {
                                    color: '#F3F4F6'
                                }
                            },
                            x: {
                                grid: {
                                    display: false
                                }
                            }
                        }
                    }
                });
            </c:when>
            <c:otherwise>
                new Chart(perfCtx, {
                    type: 'bar',
                    data: {
                        labels: ['No data'],
                        datasets: [{
                            label: 'Score',
                            data: [0],
                            backgroundColor: '#E5E7EB'
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: { display: false }
                        }
                    }
                });
            </c:otherwise>
        </c:choose>
    </script>
</body>
</html>
