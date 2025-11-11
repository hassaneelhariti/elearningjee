package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.LessonDao;
import com.mycompany.elearning.entities.Contenu.Lesson;
import com.mycompany.elearning.entities.Contenu.PDF;
import com.mycompany.elearning.entities.Contenu.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class LessonService {
    private final LessonDao lessonDao;
    private final EntityManager em;

    public LessonService(EntityManager em) {
        this.em = em;
        this.lessonDao = new LessonDao(em);
    }

    public Lesson getLessonById(Long id) {
        return lessonDao.findById(id);
    }

    public List<Lesson> getLessonsBySection(Long sectionId) {
        return lessonDao.findBySectionId(sectionId);
    }

    public Lesson createLesson(Lesson lesson, LessonContentData contentData) {
        return saveLesson(lesson, contentData);
    }

    public Lesson updateLesson(Lesson lesson, LessonContentData contentData) {
        return saveLesson(lesson, contentData);
    }

    public void deleteLesson(Long id) {
        EntityTransaction transaction = em.getTransaction();
        boolean startedByService = false;

        try {
            if (!transaction.isActive()) {
                transaction.begin();
                startedByService = true;
            }

            Lesson lesson = lessonDao.findById(id);
            if (lesson != null) {
                Video video = findVideoByLessonId(id);
                if (video != null) {
                    em.remove(video);
                }

                PDF pdf = findPdfByLessonId(id);
                if (pdf != null) {
                    em.remove(pdf);
                }

                em.remove(lesson);
            }

            if (startedByService) {
                transaction.commit();
            }

        } catch (Exception e) {
            if (startedByService && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete lesson", e);
        }
    }

    private Lesson saveLesson(Lesson lesson, LessonContentData contentData) {
        EntityTransaction transaction = em.getTransaction();
        boolean startedByService = false;

        try {
            if (!transaction.isActive()) {
                transaction.begin();
                startedByService = true;
            }

            Lesson managedLesson;
            if (lesson.getId() == null) {
                em.persist(lesson);
                em.flush(); // ensure ID is generated for associations
                managedLesson = lesson;
            } else {
                managedLesson = em.merge(lesson);
                em.flush();
            }

            applyContent(managedLesson, contentData);
            em.flush();

            if (startedByService) {
                transaction.commit();
            }

            return managedLesson;

        } catch (Exception e) {
            if (startedByService && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save lesson", e);
        }
    }

    private void applyContent(Lesson lesson, LessonContentData contentData) {
        Video existingVideo = findVideoByLessonId(lesson.getId());
        PDF existingPdf = findPdfByLessonId(lesson.getId());

        if (contentData == null || contentData.getNormalizedContentType() == null) {
            if (existingVideo != null) {
                em.remove(existingVideo);
            }
            if (existingPdf != null) {
                em.remove(existingPdf);
            }
            lesson.setVideo(null);
            lesson.setPdf(null);
            lesson.setContentType(null);
            return;
        }

        if (contentData.isVideo()) {
            if (existingPdf != null) {
                em.remove(existingPdf);
                lesson.setPdf(null);
                existingPdf = null;
            }

            Video video = existingVideo != null ? existingVideo : new Video();
            video.setUrl(trimToNull(contentData.getVideoUrl()));
            video.setDuration(contentData.getVideoDuration());
            video.setLesson(lesson);

            if (video.getId() == null) {
                em.persist(video);
            } else {
                em.merge(video);
            }

            lesson.setVideo(video);
            lesson.setContentType("VIDEO");
            lesson.setPdf(null);

        } else if (contentData.isPdf()) {
            if (existingVideo != null) {
                em.remove(existingVideo);
                lesson.setVideo(null);
                existingVideo = null;
            }

            PDF pdf = existingPdf != null ? existingPdf : new PDF();
            pdf.setUrl(trimToNull(contentData.getPdfUrl()));
            pdf.setFileName(trimToNull(contentData.getPdfFileName()));
            pdf.setFileSize(contentData.getPdfFileSize());
            pdf.setLesson(lesson);

            if (pdf.getId() == null) {
                em.persist(pdf);
            } else {
                em.merge(pdf);
            }

            lesson.setPdf(pdf);
            lesson.setContentType("PDF");
            lesson.setVideo(null);
        }
    }

    private Video findVideoByLessonId(Long lessonId) {
        List<Video> videos = em.createQuery(
                        "SELECT v FROM Video v WHERE v.lesson.id = :lessonId",
                        Video.class)
                .setParameter("lessonId", lessonId)
                .getResultList();
        return videos.isEmpty() ? null : videos.get(0);
    }

    private PDF findPdfByLessonId(Long lessonId) {
        List<PDF> pdfs = em.createQuery(
                        "SELECT p FROM PDF p WHERE p.lesson.id = :lessonId",
                        PDF.class)
                .setParameter("lessonId", lessonId)
                .getResultList();
        return pdfs.isEmpty() ? null : pdfs.get(0);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static class LessonContentData {
        private final String contentType;
        private final String videoUrl;
        private final Integer videoDuration;
        private final String pdfUrl;
        private final String pdfFileName;
        private final Long pdfFileSize;

        public LessonContentData(String contentType,
                                 String videoUrl,
                                 Integer videoDuration,
                                 String pdfUrl,
                                 String pdfFileName,
                                 Long pdfFileSize) {
            this.contentType = contentType;
            this.videoUrl = videoUrl;
            this.videoDuration = videoDuration;
            this.pdfUrl = pdfUrl;
            this.pdfFileName = pdfFileName;
            this.pdfFileSize = pdfFileSize;
        }

        public String getNormalizedContentType() {
            if (contentType == null) {
                return null;
            }
            String normalized = contentType.trim().toUpperCase();
            return normalized.isEmpty() ? null : normalized;
        }

        public boolean isVideo() {
            return "VIDEO".equals(getNormalizedContentType());
        }

        public boolean isPdf() {
            return "PDF".equals(getNormalizedContentType());
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public Integer getVideoDuration() {
            return videoDuration;
        }

        public String getPdfUrl() {
            return pdfUrl;
        }

        public String getPdfFileName() {
            return pdfFileName;
        }

        public Long getPdfFileSize() {
            return pdfFileSize;
        }
    }
}