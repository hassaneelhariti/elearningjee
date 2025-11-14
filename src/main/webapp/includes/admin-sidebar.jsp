<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar">
    <div class="sidebar-logo">Smart</div>
    <ul class="nav-menu">
        <li><a href="${pageContext.request.contextPath}/admin-dashboard" class="${param.active == 'dashboard' ? 'active' : ''}">
            <i class="fas fa-home"></i> Dashboard
        </a></li>
        <li><a href="#" class="${param.active == 'users' ? 'active' : ''}">
            <i class="fas fa-users"></i> Users
        </a></li>
        <li><a href="#" class="${param.active == 'courses' ? 'active' : ''}">
            <i class="fas fa-book"></i> All Courses
        </a></li>
        <li><a href="#" class="${param.active == 'teachers' ? 'active' : ''}">
            <i class="fas fa-chalkboard-teacher"></i> Teachers
        </a></li>
        <li><a href="#" class="${param.active == 'students' ? 'active' : ''}">
            <i class="fas fa-user-graduate"></i> Students
        </a></li>
        <li><a href="#" class="${param.active == 'reports' ? 'active' : ''}">
            <i class="fas fa-chart-line"></i> Reports
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

