<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="top-bar">
    <div>Dashboard for ${not empty role ? role.toLowerCase() : 'user'}</div>
    <div class="mode-toggle">
        <span>Light mode</span>
        <i class="fas fa-toggle-on" style="color: #3B82F6;"></i>
    </div>
</div>

