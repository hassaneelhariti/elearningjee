<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Récupérer le rôle de l'utilisateur depuis la session
    session = request.getSession(false);
    String role = "STUDENT"; // valeur par défaut
    if (session != null && session.getAttribute("role") != null) {
        role = (String) session.getAttribute("role");
    }

    // Déterminer le bon lien de dashboard selon le rôle
    String dashboardLink = "student-dashboard";
    if ("TEACHER".equals(role)) {
        dashboardLink = "teacher-dashboard";
    } else if ("ADMIN".equals(role)) {
        dashboardLink = "admin-dashboard";
    }

    // Ajouter le contexte de l'application
    String contextPath = request.getContextPath();
    String fullDashboardLink = contextPath + "/" + dashboardLink;
%>
<html>
<head>
    <title>Confirmation de déconnexion</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 40px;
            background: #f5f5f5;
            text-align: center;
        }
        .confirmation-box {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            max-width: 400px;
            margin: 0 auto;
        }
        .btn {
            padding: 12px 24px;
            margin: 10px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-logout {
            background: #f44336;
            color: white;
        }
        .btn-cancel {
            background: #757575;
            color: white;
        }
    </style>
</head>
<body>
<div class="confirmation-box">
    <h2>🚪 Déconnexion</h2>
    <p>Êtes-vous sûr de vouloir vous déconnecter ?</p>

    <div>
        <a href="<%= contextPath %>/logout" class="btn btn-logout">✅ Oui, me déconnecter</a>
        <a href="<%= fullDashboardLink %>" class="btn btn-cancel">❌ Annuler</a>
    </div>
</div>
</body>
</html>