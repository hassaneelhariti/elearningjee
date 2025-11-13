<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar">
    <div class="sidebar-logo">Smart</div>
    <ul class="nav-menu">
        <li><a href="${pageContext.request.contextPath}/teacher/dashboard" class="${param.active == 'dashboard' ? 'active' : ''}">
            <i class="fas fa-home"></i> Dashboard
        </a></li>
        <li><a href="${pageContext.request.contextPath}/teacher/courses/list" class="${param.active == 'courses' ? 'active' : ''}">
            <i class="fas fa-book"></i> My Courses
        </a></li>
        <li><a href="${pageContext.request.contextPath}/teacher/courses/new" class="${param.active == 'new-course' ? 'active' : ''}">
            <i class="fas fa-plus-circle"></i> Create Course
        </a></li>
        <li><a href="#" class="${param.active == 'students' ? 'active' : ''}">
            <i class="fas fa-users"></i> Students
        </a></li>
        <li><a href="#" class="${param.active == 'analytics' ? 'active' : ''}">
            <i class="fas fa-chart-bar"></i> Analytics
        </a></li>
        <li><a href="#" class="${param.active == 'messages' ? 'active' : ''}">
            <i class="fas fa-envelope"></i> Messages
        </a></li>
        <li><a href="#" class="${param.active == 'settings' ? 'active' : ''}">
            <i class="fas fa-cog"></i> Settings
        </a></li>
    </ul>
    <div class="logout-btn">
        <a href="${pageContext.request.contextPath}/logout">
            <i class="fas fa-sign-out-alt"></i> Log Out
        </a>
    </div>
</div>
