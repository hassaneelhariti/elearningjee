package com.mycompany.elearning.servlets.teacher;

import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import com.mycompany.elearning.services.CourseService;
import com.mycompany.elearning.services.LessonService;
import com.mycompany.elearning.services.LessonService.LessonContentData;
import com.mycompany.elearning.services.SectionService;
import com.mycompany.elearning.services.TeacherService;
import com.mycompany.elearning.utils.JPAUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/lessons/*")
public class LessonManagementServlet extends HttpServlet {

    private CourseService courseService;
    private SectionService sectionService;
    private LessonService lessonService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        this.courseService = new CourseService(em);
        this.sectionService = new SectionService(em);
        this.lessonService = new LessonService(em);
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

            if (action.startsWith("/section/")) {
                // View lessons for a specific section
                Long sectionId = Long.valueOf(action.substring(9));
                Section section = sectionService.getSectionById(sectionId);

                if (section != null && courseService.isCourseOwner(section.getCourse().getId(), teacher.getId())) {
                    List<Lesson> lessons = lessonService.getLessonsBySection(sectionId);

                    request.setAttribute("section", section);
                    request.setAttribute("lessons", lessons);
                    request.setAttribute("course", section.getCourse());
                    request.getRequestDispatcher("/teacher/lesson-list.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }

            } else if (action.startsWith("/new/")) {
                // Show new lesson form
                Long sectionId = Long.valueOf(action.substring(5));
                Section section = sectionService.getSectionById(sectionId);

                if (section != null && courseService.isCourseOwner(section.getCourse().getId(), teacher.getId())) {
                    request.setAttribute("section", section);
                    request.setAttribute("course", section.getCourse());
                    request.setAttribute("action", "new");
                    request.setAttribute("selectedContentType", "VIDEO");
                    request.getRequestDispatcher("/teacher/lesson-form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }

            } else if (action.startsWith("/edit/")) {
                // Show edit lesson form
                Long lessonId = Long.valueOf(action.substring(6));
                Lesson lesson = lessonService.getLessonById(lessonId);

                if (lesson != null && courseService.isCourseOwner(lesson.getSection().getCourse().getId(), teacher.getId())) {
                    request.setAttribute("lesson", lesson);
                    request.setAttribute("section", lesson.getSection());
                    request.setAttribute("course", lesson.getSection().getCourse());
                    request.setAttribute("action", "edit");
                    request.setAttribute("selectedContentType",
                            lesson.getContentType() != null ? lesson.getContentType() : "VIDEO");
                    request.getRequestDispatcher("/teacher/lesson-form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading lessons");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Teacher teacher = getCurrentTeacher(request);
        if (teacher == null) {
            response.sendRedirect("../../login");
            return;
        }

        HttpSession session = request.getSession();

        try {
            String action = request.getParameter("action");
            String sectionIdParam = request.getParameter("sectionId");
            String lessonIdParam = request.getParameter("lessonId");

            if (sectionIdParam == null || sectionIdParam.isEmpty()) {
                session.setAttribute("errorMessage", "Section ID is required");
                response.sendRedirect("../courses/list");
                return;
            }

            Long sectionId = Long.valueOf(sectionIdParam);
            Section section = sectionService.getSectionById(sectionId);

            // Verify course ownership
            if (section == null || !courseService.isCourseOwner(section.getCourse().getId(), teacher.getId())) {
                session.setAttribute("errorMessage", "You don't have permission to modify this course");
                response.sendRedirect("../courses/list");
                return;
            }

            if ("delete".equals(action)) {
                // Delete lesson
                if (lessonIdParam != null && !lessonIdParam.isEmpty()) {
                    Long lessonId = Long.valueOf(lessonIdParam);
                    lessonService.deleteLesson(lessonId);
                    session.setAttribute("successMessage", "Lesson deleted successfully");
                }

            } else {
                // Create or update lesson
                String title = request.getParameter("title");
                String orderIndexParam = request.getParameter("orderIndex");

                if (title == null || title.trim().isEmpty()) {
                    session.setAttribute("errorMessage", "Lesson title is required");
                    response.sendRedirect("section/" + sectionId);
                    return;
                }

                Lesson lesson;
                boolean isNew = (lessonIdParam == null || lessonIdParam.isEmpty());

                if (!isNew) {
                    // Update existing lesson
                    Long lessonId = Long.valueOf(lessonIdParam);
                    lesson = lessonService.getLessonById(lessonId);
                } else {
                    // Create new lesson
                    lesson = new Lesson();
                    lesson.setSection(section);
                }

                lesson.setTitle(title.trim());
                lesson.setOrderIndex(Integer.parseInt(orderIndexParam));
                LessonContentData contentData = extractContentData(request, response, session, sectionId, isNew ? null : lessonIdParam);
                if (contentData == null) {
                    return;
                }

                if (isNew) {
                    lessonService.createLesson(lesson, contentData);
                    session.setAttribute("successMessage", "Lesson created successfully!");
                } else {
                    lessonService.updateLesson(lesson, contentData);
                    session.setAttribute("successMessage", "Lesson updated successfully!");
                }
            }

            response.sendRedirect(request.getContextPath() + "/teacher/lessons/section/" + sectionId);

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid number format");
            response.sendRedirect(request.getContextPath() + "/teacher/courses/list");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error saving lesson: " + e.getMessage());
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

    private LessonContentData extractContentData(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 HttpSession session,
                                                 Long sectionId,
                                                 String lessonIdParam) throws IOException {
        String contentTypeParam = request.getParameter("contentType");
        String normalizedType = (contentTypeParam == null || contentTypeParam.isBlank())
                ? "VIDEO"
                : contentTypeParam.trim().toUpperCase();

        String videoUrl = request.getParameter("videoUrl");
        String videoDurationParam = request.getParameter("videoDuration");
        Integer videoDuration = null;
        if (videoDurationParam != null && !videoDurationParam.isBlank()) {
            try {
                videoDuration = Integer.valueOf(videoDurationParam.trim());
            } catch (NumberFormatException ex) {
                session.setAttribute("errorMessage", "Video duration must be a valid number.");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
            if (videoDuration < 0) {
                session.setAttribute("errorMessage", "Video duration must be zero or positive.");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
        }

        String pdfUrl = request.getParameter("pdfUrl");
        String pdfFileName = request.getParameter("pdfFileName");
        String pdfFileSizeParam = request.getParameter("pdfFileSize");
        Long pdfFileSize = null;
        if (pdfFileSizeParam != null && !pdfFileSizeParam.isBlank()) {
            try {
                pdfFileSize = Long.valueOf(pdfFileSizeParam.trim());
            } catch (NumberFormatException ex) {
                session.setAttribute("errorMessage", "PDF file size must be a number (in KB).");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
            if (pdfFileSize < 0) {
                session.setAttribute("errorMessage", "PDF file size cannot be negative.");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
        }

        if ("VIDEO".equals(normalizedType)) {
            if (videoUrl == null || videoUrl.trim().isEmpty()) {
                session.setAttribute("errorMessage", "A video URL is required for video lessons.");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
        } else if ("PDF".equals(normalizedType)) {
            if (pdfUrl == null || pdfUrl.trim().isEmpty()) {
                session.setAttribute("errorMessage", "A PDF URL is required for PDF lessons.");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
            if (pdfFileName == null || pdfFileName.trim().isEmpty()) {
                session.setAttribute("errorMessage", "A PDF file name is required for PDF lessons.");
                redirectBack(request, response, session, sectionId, lessonIdParam);
                return null;
            }
        } else {
            normalizedType = "VIDEO";
        }

        return new LessonContentData(
                normalizedType,
                videoUrl,
                videoDuration,
                pdfUrl,
                pdfFileName,
                pdfFileSize
        );
    }

    private void redirectBack(HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session,
                              Long sectionId,
                              String lessonIdParam) throws IOException {
        session.setAttribute("selectedContentType", request.getParameter("contentType"));
        String base = request.getContextPath() + "/teacher/lessons/";
        if (lessonIdParam == null || lessonIdParam.isEmpty()) {
            response.sendRedirect(base + "new/" + sectionId);
        } else {
            response.sendRedirect(base + "edit/" + lessonIdParam);
        }
    }
}