package com.mycompany.elearning.servlets.teacher;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.services.SectionService;
import com.mycompany.elearning.services.TeacherService;
import com.mycompany.elearning.utils.JPAUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/sections/*")
public class SectionManagementServlet extends HttpServlet {

    @PersistenceUnit(unitName = "elearningPU")
    private EntityManagerFactory emf;

    private CourseService courseService;
    private SectionService sectionService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        // Utilisez JPAUtil au lieu de @PersistenceUnit
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        this.courseService = new CourseService(em);
        this.sectionService = new SectionService(em);
        this.teacherService = new TeacherService(em);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Teacher teacher = getCurrentTeacher(request);
        if (teacher == null) {
            response.sendRedirect("../../login");
            return;
        }

        String action = request.getPathInfo();

        try {
            if (action == null || action.equals("/")) {
                response.sendRedirect("../courses/list");
                return;
            }

            if (action.startsWith("/course/")) {
                // View sections for a specific course
                Long courseId = Long.valueOf(action.substring(8));
                Course course = courseService.getCourseById(courseId);

                if (course != null && courseService.isCourseOwner(courseId, teacher.getId())) {
                    List<Section> sections = sectionService.getSectionsByCourse(courseId);

                    request.setAttribute("course", course);
                    request.setAttribute("sections", sections);
                    request.getRequestDispatcher("/teacher/section-list.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading sections");
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
            String action = request.getParameter("action");
            String courseIdParam = request.getParameter("courseId");
            String sectionIdParam = request.getParameter("sectionId");

            if (courseIdParam == null || courseIdParam.isEmpty()) {
                session.setAttribute("errorMessage", "Course ID is required");
                response.sendRedirect(request.getContextPath() + "/teacher/courses/list");
                return;
            }

            Long courseId = Long.valueOf(courseIdParam);

            // Verify course ownership
            if (!courseService.isCourseOwner(courseId, teacher.getId())) {
                session.setAttribute("errorMessage", "You don't have permission to modify this course");
                response.sendRedirect(request.getContextPath() + "/teacher/courses/list");
                return;
            }

            Course course = courseService.getCourseById(courseId);

            if ("delete".equals(action)) {
                // Delete section
                if (sectionIdParam != null && !sectionIdParam.isEmpty()) {
                    Long sectionId = Long.valueOf(sectionIdParam);
                    sectionService.deleteSection(sectionId);
                    session.setAttribute("successMessage", "Section deleted successfully");
                }

            } else {
                // Create or update section
                String title = request.getParameter("title");
                String orderIndexParam = request.getParameter("orderIndex");

                if (title == null || title.trim().isEmpty()) {
                    session.setAttribute("errorMessage", "Section title is required");
                    response.sendRedirect(request.getContextPath() + "/teacher/sections/course/" + courseId);
                    return;
                }

                Section section;
                boolean isNew = (sectionIdParam == null || sectionIdParam.isEmpty());

                if (!isNew) {
                    // Update existing section
                    Long sectionId = Long.valueOf(sectionIdParam);
                    section = sectionService.getSectionById(sectionId);
                    if (section == null || !section.getCourse().getId().equals(courseId)) {
                        session.setAttribute("errorMessage", "Section not found");
                        response.sendRedirect(request.getContextPath() + "/teacher/sections/course/" + courseId);
                        return;
                    }
                } else {
                    // Create new section
                    section = new Section();
                    section.setCourse(course);
                }

                section.setTitle(title.trim());
                section.setOrderIndex(Integer.parseInt(orderIndexParam));

                if (isNew) {
                    sectionService.createSection(section);
                    session.setAttribute("successMessage", "Section created successfully!");
                } else {
                    sectionService.updateSection(section);
                    session.setAttribute("successMessage", "Section updated successfully!");
                }
            }

            // Redirect back to sections list for this course
            response.sendRedirect(request.getContextPath() + "/teacher/sections/course/" + courseId);

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid number format");
            response.sendRedirect(request.getContextPath() + "/teacher/courses/list");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error saving section: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/teacher/courses/list");
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