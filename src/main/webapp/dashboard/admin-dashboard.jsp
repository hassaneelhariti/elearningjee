<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 11/8/2025
  Time: 10:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Tableau de Bord Administrateur</title>
  <style>
    body { font-family: Arial; margin: 40px; background: #fff8f0; }
    .container { max-width: 800px; margin: 0 auto; }
    .header { background: #FF9800; color: white; padding: 20px; border-radius: 8px; }
    .menu { margin: 20px 0; }
    .menu a { display: block; padding: 10px; margin: 5px 0; background: white;
      text-decoration: none; border-radius: 4px; }
  </style>
</head>
<body>
<div class="container">
  <div class="header">
    <h1>⚙️ Tableau de Bord Administrateur</h1>
    <p>Bienvenue dans votre espace administration</p>
  </div>

  <div class="menu">
    <a href="gestion-utilisateurs">👥 Gérer les Utilisateurs</a>
    <a href="gestion-cours">📚 Gérer les Cours</a>
    <a href="statistiques">📊 Voir les Statistiques</a>
    <a href="parametres">⚙️ Paramètres Système</a>
    <a href="auth/logout-confirm.jsp" style="background: #ff4444; color: white;">🚪 Déconnexion</a>
  </div>
</div>
</body>
</html>
