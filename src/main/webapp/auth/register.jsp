<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - E-Learning</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .register-container {
            background: white;
            border-radius: 16px;
            padding: 40px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            max-width: 500px;
            width: 100%;
            max-height: 90vh;
            overflow-y: auto;
        }
        .register-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .register-header h1 {
            color: #1F2937;
            font-size: 28px;
            margin-bottom: 10px;
        }
        .register-header p {
            color: #6B7280;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #1F2937;
        }
        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #E5E7EB;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s;
        }
        .form-control:focus {
            outline: none;
            border-color: #3B82F6;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }
        .btn-register {
            width: 100%;
            padding: 12px;
            background: #3B82F6;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        .btn-register:hover {
            background: #2563EB;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
            color: #6B7280;
        }
        .login-link a {
            color: #3B82F6;
            text-decoration: none;
            font-weight: 500;
        }
        .login-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="register-header">
            <h1><i class="fas fa-user-plus me-2"></i>Create Account</h1>
            <p>Join us and start your learning journey!</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle me-2"></i>${error}
            </div>
        </c:if>

        <form action="register" method="post">
            <div class="form-group">
                <label class="form-label" for="username">
                    <i class="fas fa-user me-2"></i>Username
                </label>
                <input type="text" id="username" name="username" class="form-control" required placeholder="Enter your username">
            </div>
            <div class="form-group">
                <label class="form-label" for="email">
                    <i class="fas fa-envelope me-2"></i>Email
                </label>
                <input type="email" id="email" name="email" class="form-control" required placeholder="Enter your email">
            </div>
            <div class="form-group">
                <label class="form-label" for="password">
                    <i class="fas fa-lock me-2"></i>Password
                </label>
                <input type="password" id="password" name="password" class="form-control" required placeholder="Enter your password">
            </div>
            <div class="form-group">
                <label class="form-label" for="firstName">
                    <i class="fas fa-id-card me-2"></i>First Name
                </label>
                <input type="text" id="firstName" name="firstName" class="form-control" required placeholder="Enter your first name">
            </div>
            <div class="form-group">
                <label class="form-label" for="lastName">
                    <i class="fas fa-id-card me-2"></i>Last Name
                </label>
                <input type="text" id="lastName" name="lastName" class="form-control" required placeholder="Enter your last name">
            </div>
            <div class="form-group">
                <label class="form-label" for="role">
                    <i class="fas fa-user-tag me-2"></i>Role
                </label>
                <select id="role" name="role" class="form-control" required>
                    <option value="STUDENT">Student</option>
                    <option value="TEACHER">Teacher</option>
                    <option value="ADMIN">Administrator</option>
                </select>
            </div>
            <button type="submit" class="btn-register">
                <i class="fas fa-user-plus me-2"></i>Register
            </button>
        </form>

        <div class="login-link">
            Already have an account? <a href="login">Login</a>
        </div>
    </div>
</body>
</html>
