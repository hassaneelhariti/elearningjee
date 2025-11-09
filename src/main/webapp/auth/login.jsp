<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion - E-Learning</title>
</head>
<body>
<h2>Connexion</h2>

<% if (request.getAttribute("message") != null) { %>
<div style="color: green;">${message}</div>
<% } %>

<% if (request.getAttribute("error") != null) { %>
<div style="color: red;">${error}</div>
<% } %>

<form action="login" method="post">
    <div>
        <label>Email:</label>
        <input type="email" name="email" required>
    </div>
    <div>
        <label>Mot de passe:</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Se connecter</button>
</form>

<p>Pas de compte ? <a href="register">S'inscrire</a></p>
</body>
</html>