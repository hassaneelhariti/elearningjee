<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-dark sidebar collapse">
    <div class="position-sticky pt-3">
        <div class="text-center text-white mb-4">
            <h4>E-Learning Portal</h4>
            <p class="text-muted">Teacher Dashboard</p>
        </div>
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link text-white ${pageContext.request.requestURI.contains('dashboard') ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/teacher/dashboard">
                    <i class="fas fa-tachometer-alt me-2"></i>
                    Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white ${pageContext.request.requestURI.contains('course') && !pageContext.request.requestURI.contains('dashboard') ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/teacher/courses/list">
                    <i class="fas fa-book me-2"></i>
                    My Courses
                </a>
            </li>
        </ul>
        <div class="mt-4 p-3">
            <div class="text-white">
                <p class="mb-1">Welcome,</p>
                <h6>${teacher.firstName} ${teacher.lastName}</h6>
                <small class="text-muted">${teacher.email}</small>
            </div>
            <div class="mt-3">
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm w-100">
                    <i class="fas fa-sign-out-alt me-1"></i> Logout
                </a>
            </div>
        </div>
    </div>
</nav>