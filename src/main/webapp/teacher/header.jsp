<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">
            <i class="fas fa-chalkboard-teacher me-2"></i>
            Teacher Portal
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTeacher">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTeacher">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'dashboard' ? 'active' : ''}" href="dashboard">
                        <i class="fas fa-tachometer-alt me-1"></i>Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'courses' ? 'active' : ''}" href="courses/list">
                        <i class="fas fa-book me-1"></i>My Courses
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${param.active == 'new' ? 'active' : ''}" href="courses/new">
                        <i class="fas fa-plus-circle me-1"></i>New Course
                    </a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user me-1"></i>
                        ${teacher.firstName} ${teacher.lastName}
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#"><i class="fas fa-cog me-2"></i>Settings</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="../logout"><i class="fas fa-sign-out-alt me-2"></i>Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Messages Flash -->
<c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show m-3" role="alert">
        <i class="fas fa-check-circle me-2"></i>${successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show m-3" role="alert">
        <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="errorMessage" scope="session"/>
</c:if>