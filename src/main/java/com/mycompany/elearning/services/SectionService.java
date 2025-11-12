package com.mycompany.elearning.services;

import com.mycompany.elearning.dao.SectionDao;
import com.mycompany.elearning.entities.Contenu.Section;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SectionService {
    private SectionDao sectionDao;
    private EntityManager em;

    public SectionService(EntityManager em) {
        this.em = em;
        this.sectionDao = new SectionDao(em);
    }

    public Section getSectionById(Long id) {
        return sectionDao.findById(id);
    }

    public List<Section> getSectionsByCourse(Long courseId) {
        return sectionDao.findByCourseId(courseId);
    }

    public Section createSection(Section section) {
        return sectionDao.save(section);
    }

    public Section updateSection(Section section) {
        return sectionDao.save(section);
    }

    public void deleteSection(Long id) {
        sectionDao.delete(id);
    }
}