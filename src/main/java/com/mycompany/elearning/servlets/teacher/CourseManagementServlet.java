package com.mycompany.elearning.servlets.teacher;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.services.TeacherService;
import com.mycompany.elearning.utils.JPAUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/teacher/courses/*")
public class CourseManagementServlet extends HttpServlet {

    private CourseService courseService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        this.courseService = new CourseService(em);
        this.teacherService = new TeacherService(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Teacher teacher = getCurrentTeacher(request);
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getPathInfo();

        try {
            if (action == null || action.equals("/")) {
                response.sendRedirect("list");
                return;
            } else if (action.equals("/list")) {
                var courses = courseService.getCoursesByTeacher(teacher.getId());
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/teacher/course-list.jsp").forward(request, response);

            } else if (action.equals("/new")) {
                request.setAttribute("action", "new");
                request.getRequestDispatcher("/teacher/course-form.jsp").forward(request, response);

            } else if (action.startsWith("/edit/")) {
                Long courseId = Long.valueOf(action.substring(6));
                Course course = courseService.getCourseById(courseId);

                if (course != null && courseService.isCourseOwner(courseId, teacher.getId())) {
                    request.setAttribute("course", course);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/teacher/course-form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission to edit this course");
                }

            } else if (action.startsWith("/delete/")) {
                Long courseId = Long.valueOf(action.substring(8));
                if (courseService.isCourseOwner(courseId, teacher.getId())) {
                    courseService.deleteCourse(courseId);
                    request.getSession().setAttribute("successMessage", "Course deleted successfully");
                } else {
                    request.getSession().setAttribute("errorMessage", "You don't have permission to delete this course");
                }
                response.sendRedirect("list");

            } else if (action.startsWith("/detail/")) {
                Long courseId = Long.valueOf(action.substring(8));
                Course course = courseService.getCourseById(courseId);

                if (course != null && courseService.isCourseOwner(courseId, teacher.getId())) {
                    request.setAttribute("course", course);
                    request.getRequestDispatcher("/teacher/course-details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Teacher teacher = getCurrentTeacher(request);
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        HttpSession session = request.getSession();

        try {
            String courseIdParam = request.getParameter("courseId");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String level = request.getParameter("level");
            String thumbnail = request.getParameter("thumbnail");
            String isPublishedParam = request.getParameter("isPublished");

            if (title == null || title.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Course title is required");
                response.sendRedirect("new");
                return;
            }

            Course course;
            boolean isNew = (courseIdParam == null || courseIdParam.isEmpty());

            if (!isNew) {
                Long courseId = Long.valueOf(courseIdParam);
                if (!courseService.isCourseOwner(courseId, teacher.getId())) {
                    session.setAttribute("errorMessage", "You don't have permission to edit this course");
                    response.sendRedirect("list");
                    return;
                }
                course = courseService.getCourseById(courseId);
            } else {
                course = new Course();
                course.setTeacher(teacher);
                course.setDateCreated(LocalDateTime.now());
            }

            course.setTitle(title.trim());
            course.setDescription(description != null ? description.trim() : "");
            course.setLevel(level != null ? level : "Beginner");
            course.setThumbnail(thumbnail != null ? thumbnail.trim() : "");
            course.setIsPublished(Boolean.parseBoolean(isPublishedParam));

            if (isNew) {
                course = courseService.createCourse(course);
                session.setAttribute("successMessage", "Course created successfully!");
            } else {
                course = courseService.updateCourse(course);
                session.setAttribute("successMessage", "Course updated successfully!");
            }

            response.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error saving course: " + e.getMessage());
            response.sendRedirect("list");
        }
    }

    private Teacher getCurrentTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (userObj instanceof Teacher && "TEACHER".equalsIgnoreCase(role)) {
            return (Teacher) userObj;
        }
        return null;
    }
}