/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.service;

import com.mycompany.elearning.dao.teacher.CourseDAO;
import com.mycompany.elearning.dao.teacher.LessonDAO;
import com.mycompany.elearning.dao.teacher.SectionDAO;
import com.mycompany.elearning.dao.teacher.VideoDAO;
import com.mycompany.elearning.entities.Contenu.Course;
import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.Contenu.Section;
import com.mycompany.elearning.entities.Contenu.Video;
import com.mycompany.elearning.entities.Utilisateurs.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author ousam713
 */


@ApplicationScoped
public class CourseService {
    
    @Inject
    private CourseDAO courseDAO;
    
    @Inject
    private SectionDAO sectionDAO;
    
    @Inject
    private LessonDAO lessonDAO;
    
    @Inject
    private VideoDAO videoDAO;
    
    // ==================== GESTION DES COURS ====================
    
    /**
     * Créer un nouveau cours
     */
    public Course createCourse(String title, String description, String level, Teacher teacher) {
        Course course = new Course(title, description, level, teacher);
        courseDAO.save(course);
        return course;
    }
    
    /**
     * Mettre à jour un cours
     */
    public void updateCourse(Course course) {
        courseDAO.update(course);
    }
    
    /**
     * Trouver un cours par ID
     */
    public Course findCourseById(Long id) {
        return courseDAO.findById(id);
    }
    
    /**
     * Récupérer tous les cours d'un enseignant
     * Note: Utilise la relation bidirectionnelle Teacher -> Courses
     */
    public List<Course> getTeacherCourses(Teacher teacher) {
        return teacher.getCourses();
    }
    
    /**
     * Publier un cours
     */
    public void publishCourse(Course course) {
        course.setIsPublished(true);
        courseDAO.update(course);
    }
    
    /**
     * Dépublier un cours
     */
    public void unpublishCourse(Course course) {
        course.setIsPublished(false);
        courseDAO.update(course);
    }
    
    /**
     * Supprimer un cours
     */
    public void deleteCourse(Course course) {
        courseDAO.delete(course);
    }
    
    /**
     * Supprimer un cours par ID
     */
    public void deleteCourseById(Long id) {
        courseDAO.deleteById(id);
    }
    
    // ==================== GESTION DES SECTIONS ====================
    
    /**
     * Ajouter une section à un cours
     */
    public Section addSectionToCourse(Course course, String title, Integer orderIndex) {
        Section section = new Section(title, orderIndex, course);
        sectionDAO.save(section);
        return section;
    }
    
    /**
     * Mettre à jour une section
     */
    public void updateSection(Section section) {
        sectionDAO.update(section);
    }
    
    /**
     * Trouver une section par ID
     */
    public Section findSectionById(Long id) {
        return sectionDAO.findById(id);
    }
    
    /**
     * Récupérer les sections d'un cours
     * Note: Utilise la relation Course -> Sections
     */
    public List<Section> getCourseSections(Course course) {
        return course.getSections();
    }
    
    /**
     * Supprimer une section
     */
    public void deleteSection(Section section) {
        sectionDAO.delete(section);
    }
    
    // ==================== GESTION DES LEÇONS ====================
    
    /**
     * Ajouter une leçon à une section
     */
    public Lesson addLessonToSection(Section section, String title, Integer orderIndex) {
        Lesson lesson = new Lesson(title, orderIndex, section);
        lessonDAO.save(lesson);
        return lesson;
    }
    
    /**
     * Mettre à jour une leçon
     */
    public void updateLesson(Lesson lesson) {
        lessonDAO.update(lesson);
    }
    
    /**
     * Trouver une leçon par ID
     */
    public Lesson findLessonById(Long id) {
        return lessonDAO.findById(id);
    }
    
    /**
     * Récupérer les leçons d'une section
     * Note: Utilise la relation Section -> Lessons
     */
    public List<Lesson> getSectionLessons(Section section) {
        return section.getLessons();
    }
    
    /**
     * Supprimer une leçon
     */
    public void deleteLesson(Lesson lesson) {
        lessonDAO.delete(lesson);
    }
    
    // ==================== GESTION DES VIDÉOS ====================
    
    /**
     * Ajouter une vidéo à une leçon
     */
    public Video addVideoToLesson(Lesson lesson, String url, Integer duration) {
        Video video = new Video(url, duration, lesson);
        lesson.setVideo(video);
        videoDAO.save(video);
        return video;
    }
    
    /**
     * Mettre à jour une vidéo
     */
    public void updateVideo(Video video) {
        videoDAO.update(video);
    }
    
    /**
     * Supprimer une vidéo
     */
    public void deleteVideo(Video video) {
        videoDAO.delete(video);
    }
}