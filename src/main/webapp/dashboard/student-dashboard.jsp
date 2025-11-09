<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 11/8/2025
  Time: 10:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tableau de Bord Étudiant</title>
    <style>
        body { font-family: Arial; margin: 40px; background: #f0f8f0; }
        .container { max-width: 800px; margin: 0 auto; }
        .header { background: #4CAF50; color: white; padding: 20px; border-radius: 8px; }
        .menu { margin: 20px 0; }
        .menu a { display: block; padding: 10px; margin: 5px 0; background: white;
            text-decoration: none; border-radius: 4px; }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>🎓 Tableau de Bord Étudiant</h1>
        <p>Bienvenue dans votre espace étudiant</p>
    </div>

    <div class="menu">
        <a href="mes-cours">📚 Mes Cours</a>
        <a href="mes-devoirs">📝 Mes Devoirs</a>
        <a href="progression">📈 Ma Progression</a>
        <a href="profil">👤 Mon Profil</a>
        <a href="auth/logout-confirm.jsp" style="background: #ff4444; color: white;">🚪 Déconnexion</a>
    </div>
</div>
</body>
</html>
