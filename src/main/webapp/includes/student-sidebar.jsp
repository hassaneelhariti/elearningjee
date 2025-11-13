<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar">
    <div class="sidebar-logo">Smart</div>
    <ul class="nav-menu">
        <li><a href="${pageContext.request.contextPath}/student-dashboard" class="${param.active == 'dashboard' ? 'active' : ''}">
            <i class="fas fa-home"></i> Dashboard
        </a></li>
        <li><a href="${pageContext.request.contextPath}/my-courses" class="${param.active == 'courses' ? 'active' : ''}">
            <i class="fas fa-book"></i> Lessons
        </a></li>
        <li><a href="${pageContext.request.contextPath}/course" class="${param.active == 'available' ? 'active' : ''}">
            <i class="fas fa-list"></i> Available Courses
        </a></li>
        <li><a href="#" class="${param.active == 'schedule' ? 'active' : ''}">
            <i class="fas fa-calendar"></i> Schedule
        </a></li>
        <li><a href="#" class="${param.active == 'materials' ? 'active' : ''}">
            <i class="fas fa-folder"></i> Materials
        </a></li>
        <li><a href="#" class="${param.active == 'forum' ? 'active' : ''}">
            <i class="fas fa-comments"></i> Forum
        </a></li>
        <li><a href="#" class="${param.active == 'assessments' ? 'active' : ''}">
            <i class="fas fa-clipboard-check"></i> Assessments
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

