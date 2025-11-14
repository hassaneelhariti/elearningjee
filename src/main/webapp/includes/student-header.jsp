<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="main-header">
    <div class="search-box">
        <input type="text" placeholder="Search...">
        <i class="fas fa-search"></i>
    </div>
    <div class="header-actions">
        <select class="dropdown-custom">
            <option>ENG</option>
            <option>FR</option>
        </select>
        <div class="header-icon">
            <i class="fas fa-envelope"></i>
            <span class="badge">3</span>
        </div>
        <div class="header-icon">
            <i class="fas fa-bell"></i>
            <span class="badge">5</span>
        </div>
        <div class="user-profile">
            <div class="user-avatar">
                ${not empty userFirstName ? fn:substring(userFirstName, 0, 1) : (not empty userName ? fn:substring(userName, 0, 1) : 'U')}
            </div>
            <div>
                <div style="font-weight: 600; color: #1F2937;">${not empty userName ? userName : 'Student'}</div>
                <div style="font-size: 12px; color: #6B7280;">Student</div>
            </div>
        </div>
    </div>
</div>

