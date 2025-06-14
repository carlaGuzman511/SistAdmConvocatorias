package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {


    @Autowired
    private AnnouncementRepository announcementRepository;


    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }


    public Announcement getAnnouncement(long id) {
        return announcementRepository.FindById(id)
                .orElse(null);

    }

    public long addAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
        return announcementRepository.selectByIdAnnouncement();
    }

    public void UpdateAnnouncement(long idannouncement, String title, String description, String appointment, boolean pack,
                                   boolean state, Area area, AcademicUnit academicUnit, Management management, Faculty faculty,
                                   List<Auxiliary> auxiliarys, List<Postulant> postulants) {
        Announcement announcement = announcementRepository.getOne(idannouncement);
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setAppointment(appointment);
        announcement.setPack(pack);
        announcement.setState(state);
        announcement.setManagement(management);
        announcement.setArea(area);
        announcement.setAcademicUnit(academicUnit);
        announcement.setFaculty(faculty);
        for (int i = 0; i < auxiliarys.size(); i++) {
            announcement.getAuxiliarys().add(auxiliarys.get(i));
        }
        for (int j = 0; j < postulants.size(); j++) {
            announcement.getPostulants().add(postulants.get(j));
        }

        announcementRepository.save(announcement);

    }

    public void updateAnnouncementPostulant(long idannouncement, Postulant postulant) {
        Announcement announcement = announcementRepository.getOne(idannouncement);
        announcement.getPostulants().add(postulant);

        announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(long id) {
        announcementRepository.DeleteByCi(id);
    }


    public List<Auxiliary> listAuxiliarysByIdAnnouncement(long id) {

        return announcementRepository.listAuxiliarysByIdAnnouncement(id);
    }

    public List<Auxiliary> listAuxiliarysByIdManagementAreaAcademicUnit(long idmanagement, long idarea, long idacademicunit) {

        return announcementRepository.listAuxiliarysByIdManagementAreaAcademicUnit(idmanagement, idarea, idacademicunit);
    }

    public List<Announcement> listByIdManagementAreaAcademicUnit(long idmanagement, long idarea, long idacademicunit) {
        return announcementRepository.findAnnouncementsByManagement_IdmanagementAndArea_IdareaAndAcademicUnit_Idacademicunit(idmanagement, idarea, idacademicunit);
    }

    public List<Announcement> listByManagementAreaAcademicUnitFaculty(long idmanagement, long idarea, long idacademicunit, long idfaculty) {

        return announcementRepository.listByManagementAreaAcademicUnitFaculty(idmanagement, idarea, idacademicunit, idfaculty);
    }
    
    public List<Announcement> listByManagementAcademicUnit(long idmanagement, long idacademicunit) {

        return announcementRepository.listByManagementAcademicUnit(idmanagement, idacademicunit);
    }

    public List<Auxiliary> listAuxiliarysByIdAreaAcademicUnit(long idarea, long idacademicunit) {

        return announcementRepository.listAuxiliarysByIdAreaAcademicUnit(idarea, idacademicunit);
    }

    public long findAreaNameByIdAnnouncement(long id) {
        return announcementRepository.findAreaNameByIdAnnouncement(id);
    }

    public long getlastIdAnnouncement() {

        return announcementRepository.selectByIdAnnouncement();
    }
}
