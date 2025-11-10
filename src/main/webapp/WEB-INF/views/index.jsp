<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accueil - E-Learning Platform</title>
</head>
<body>
    <h1>Bienvenue sur la Plateforme E-Learning</h1>
    
    <h2>Menu Principal</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/courses">Voir tous les cours</a></li>
        <li><a href="${pageContext.request.contextPath}/login">Se connecter</a></li>
        <li><a href="${pageContext.request.contextPath}/register">Créer un compte étudiant</a></li>
    </ul>
    
    <h2>À propos</h2>
    <p>
        Cette plateforme offre des cours en ligne pour tous les niveaux.
        Inscrivez-vous pour commencer votre apprentissage!
    </p>
    
    <h2>Fonctionnalités</h2>
    <ul>
        <li>Catalogue de cours variés</li>
        <li>Suivi de progression</li>
        <li>Certificats de réussite</li>
        <li>Contenu vidéo et PDF</li>
    </ul>
</body>
</html>