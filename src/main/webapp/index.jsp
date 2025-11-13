<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Learning Platform - Start Your Learning Journey</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            overflow-x: hidden;
        }

        /* Hero Section */
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            position: relative;
            overflow: hidden;
        }

        .hero-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg width="100" height="100" xmlns="http://www.w3.org/2000/svg"><circle cx="50" cy="50" r="2" fill="rgba(255,255,255,0.1)"/></svg>');
            opacity: 0.3;
        }

        .hero-content {
            position: relative;
            z-index: 1;
            text-align: center;
            max-width: 800px;
            padding: 40px 20px;
        }

        .hero-content h1 {
            font-size: 56px;
            font-weight: 700;
            margin-bottom: 20px;
            line-height: 1.2;
        }

        .hero-content p {
            font-size: 20px;
            margin-bottom: 40px;
            opacity: 0.9;
            line-height: 1.6;
        }

        .cta-buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn-hero {
            padding: 16px 40px;
            font-size: 18px;
            font-weight: 600;
            border-radius: 50px;
            text-decoration: none;
            transition: all 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 10px;
        }

        .btn-primary-hero {
            background: white;
            color: #667eea;
            box-shadow: 0 4px 20px rgba(0,0,0,0.2);
        }

        .btn-primary-hero:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 30px rgba(0,0,0,0.3);
            color: #667eea;
        }

        .btn-outline-hero {
            background: transparent;
            color: white;
            border: 2px solid white;
        }

        .btn-outline-hero:hover {
            background: white;
            color: #667eea;
            transform: translateY(-3px);
        }

        /* Stats Section */
        .stats-section {
            background: white;
            padding: 80px 20px;
        }

        .stats-container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 40px;
            margin-top: 60px;
        }

        .stat-card {
            text-align: center;
            padding: 40px 20px;
            border-radius: 16px;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            transition: all 0.3s;
        }

        .stat-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
        }

        .stat-icon {
            font-size: 48px;
            margin-bottom: 20px;
            color: #667eea;
        }

        .stat-number {
            font-size: 48px;
            font-weight: 700;
            color: #1F2937;
            margin-bottom: 10px;
        }

        .stat-label {
            font-size: 18px;
            color: #6B7280;
            font-weight: 500;
        }

        /* Features Section */
        .features-section {
            background: #F9FAFB;
            padding: 80px 20px;
        }

        .features-container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .section-title {
            text-align: center;
            font-size: 42px;
            font-weight: 700;
            color: #1F2937;
            margin-bottom: 20px;
        }

        .section-subtitle {
            text-align: center;
            font-size: 18px;
            color: #6B7280;
            margin-bottom: 60px;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
        }

        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-top: 40px;
        }

        .feature-card {
            background: white;
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            transition: all 0.3s;
        }

        .feature-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        .feature-icon {
            font-size: 48px;
            color: #667eea;
            margin-bottom: 20px;
        }

        .feature-title {
            font-size: 24px;
            font-weight: 600;
            color: #1F2937;
            margin-bottom: 15px;
        }

        .feature-description {
            font-size: 16px;
            color: #6B7280;
            line-height: 1.6;
        }

        /* Footer */
        .footer {
            background: #1F2937;
            color: white;
            padding: 40px 20px;
            text-align: center;
        }

        .footer p {
            opacity: 0.8;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .hero-content h1 {
                font-size: 36px;
            }

            .hero-content p {
                font-size: 16px;
            }

            .cta-buttons {
                flex-direction: column;
                align-items: center;
            }

            .btn-hero {
                width: 100%;
                max-width: 300px;
            }

            .section-title {
                font-size: 32px;
            }
        }
    </style>
</head>
<body>
    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content">
            <h1><i class="fas fa-graduation-cap me-3"></i>Welcome to E-Learning Platform</h1>
            <p>Transform your future with our comprehensive online learning platform. Access thousands of courses, learn from expert instructors, and achieve your goals at your own pace.</p>
            <div class="cta-buttons">
                <a href="${pageContext.request.contextPath}/register" class="btn-hero btn-primary-hero">
                    <i class="fas fa-rocket"></i>
                    Start Learning Now
                </a>
                <a href="${pageContext.request.contextPath}/login" class="btn-hero btn-outline-hero">
                    <i class="fas fa-sign-in-alt"></i>
                    Login
                </a>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="stats-section">
        <div class="stats-container">
            <h2 class="section-title">Our Platform in Numbers</h2>
            <p class="section-subtitle">Join thousands of learners and instructors on our platform</p>
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-number" id="totalStudents">${not empty totalStudents ? totalStudents : 1000}+</div>
                    <div class="stat-label">Active Students</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-book"></i>
                    </div>
                    <div class="stat-number" id="totalCourses">${not empty totalCourses ? totalCourses : 500}+</div>
                    <div class="stat-label">Available Courses</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-chalkboard-teacher"></i>
                    </div>
                    <div class="stat-number" id="totalTeachers">${not empty totalTeachers ? totalTeachers : 100}+</div>
                    <div class="stat-label">Expert Instructors</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <i class="fas fa-certificate"></i>
                    </div>
                    <div class="stat-number" id="totalEnrollments">${not empty totalEnrollments ? totalEnrollments : 5000}+</div>
                    <div class="stat-label">Course Enrollments</div>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features-section">
        <div class="features-container">
            <h2 class="section-title">Why Choose Our Platform?</h2>
            <p class="section-subtitle">Discover the features that make learning with us an exceptional experience</p>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-laptop"></i>
                    </div>
                    <h3 class="feature-title">Learn Anywhere, Anytime</h3>
                    <p class="feature-description">Access your courses from any device, at any time. Learn at your own pace and on your schedule.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-user-graduate"></i>
                    </div>
                    <h3 class="feature-title">Expert Instructors</h3>
                    <p class="feature-description">Learn from industry experts and experienced professionals who are passionate about teaching.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-certificate"></i>
                    </div>
                    <h3 class="feature-title">Certificates of Completion</h3>
                    <p class="feature-description">Earn certificates upon course completion to showcase your new skills and knowledge.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <h3 class="feature-title">Track Your Progress</h3>
                    <p class="feature-description">Monitor your learning journey with detailed progress tracking and personalized insights.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-comments"></i>
                    </div>
                    <h3 class="feature-title">Interactive Learning</h3>
                    <p class="feature-description">Engage with instructors and fellow students through forums and interactive discussions.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-mobile-alt"></i>
                    </div>
                    <h3 class="feature-title">Mobile Friendly</h3>
                    <p class="feature-description">Learn on the go with our fully responsive platform that works seamlessly on all devices.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 E-Learning Platform. All rights reserved.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Animate numbers on scroll
        function animateValue(element, start, end, duration) {
            let startTimestamp = null;
            const step = (timestamp) => {
                if (!startTimestamp) startTimestamp = timestamp;
                const progress = Math.min((timestamp - startTimestamp) / duration, 1);
                const value = Math.floor(progress * (end - start) + start);
                element.textContent = value + '+';
                if (progress < 1) {
                    window.requestAnimationFrame(step);
                }
            };
            window.requestAnimationFrame(step);
        }

        // Intersection Observer for animations
        const observerOptions = {
            threshold: 0.5,
            rootMargin: '0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const element = entry.target;
                    const text = element.textContent;
                    const finalValue = parseInt(text.replace(/[^0-9]/g, '')) || 0;
                    if (finalValue > 0) {
                        animateValue(element, 0, finalValue, 2000);
                        observer.unobserve(element);
                    }
                }
            });
        }, observerOptions);

        // Observe stat numbers
        document.querySelectorAll('.stat-number').forEach(stat => {
            observer.observe(stat);
        });
    </script>
</body>
</html>
