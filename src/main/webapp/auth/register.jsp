<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription - E-Learning</title>
</head>
<body>
<h2>Inscription</h2>

<% if (request.getAttribute("error") != null) { %>
<div style="color: red;">${error}</div>
<% } %>

<form action="register" method="post">
    <div>
        <label>Nom d'utilisateur:</label>
        <input type="text" name="username" required>
    </div>
    <div>
        <label>Email:</label>
        <input type="email" name="email" required>
    </div>
    <div>
        <label>Mot de passe:</label>
        <input type="password" name="password" required>
    </div>
    <div>
        <label>Prénom:</label>
        <input type="text" name="firstName" required>
    </div>
    <div>
        <label>Nom:</label>
        <input type="text" name="lastName" required>
    </div>
    <div>
        <label>Rôle:</label>
        <select name="role" required>
            <option value="STUDENT">Étudiant</option>
            <option value="TEACHER">Enseignant</option>
            <option value="ADMIN">Administrateur</option>
        </select>
    </div>
    <button type="submit">S'inscrire</button>
</form>

<p>Déjà inscrit ? <a href="login">Se connecter</a></p>
</body>
</html>