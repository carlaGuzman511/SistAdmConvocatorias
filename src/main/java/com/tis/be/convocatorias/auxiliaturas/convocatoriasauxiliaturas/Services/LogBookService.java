package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LogBook;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LogBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LogBookService {


    @Autowired
    private LogBookRepository logBookRepository;


    public List<LogBook> getAllLogBook() {
        return logBookRepository.findAll();
    }


    public LogBook getLogBook(long id) {
        return logBookRepository.FindById(id)
                .orElse(null);

    }

    public long addLogBook(LogBook logBook) {
        logBookRepository.save(logBook);
        return logBookRepository.selectByIdlogbook();
    }

    public void deleteLogBook(long id) {
        logBookRepository.DeleteByCi(id);
    }


    public List<LogBook> ListadoAnnouncement(long id) {

        return logBookRepository.findLogBookByLabel_Announcement_Idannouncement(id);
    }

    public List<LogBook> ListadoByAnnouncementIdandPostulantStatus(long id, boolean status) {

        return logBookRepository.findLogBookByLabel_Announcement_IdannouncementAndLabel_Postulantes_Status(id, status);
    }

    public List<LogBook> ListByIdUserAnnouncementT(long idannouncement, long areawork, long idwork, long iduser) {
        return logBookRepository.ListByIdUserAnnouncementT(idannouncement, areawork, idwork, iduser);
    }

    public List<LogBook> ListByIdUserAnnouncementA(long idannouncement, long areawork, long idwork, long iduser) {
        return logBookRepository.ListByIdUserAnnouncementA(idannouncement, areawork, idwork, iduser);
    }

    public List<LogBook> ListByIdUserAnnouncementAuxiliaryAproved(long idannouncement, long idauxiliary) {
        return logBookRepository.ListByIdUserAnnouncementAuxiliaryAproved(
                idannouncement,
                idauxiliary
        );
    }

    public List<LogBook> ListByThematicAnnouncementIsStatusTrue(long idannouncement, long idthematic) {
        return logBookRepository.ListPostulantesHabilitadosPorTematica(
                idannouncement,
                idthematic
        );
    }

    public boolean existsLogbookByIdLabel(long idlabel) {
        return logBookRepository.existsLogBookByLabel_Idlabel(idlabel);
    }

//    --------------------------------------------- Searchers ----------------------------------------------------------

    public List<LogBook> searcherLogbookByTextAnnouncement(String searchTerm, String searchTerm1, long idannouncement) {
        return logBookRepository.searcherPostulantsByTextAnnouncement(searchTerm, searchTerm1, idannouncement);
    }

    public List<LogBook> searcherLogbookByTextAnnounAndStatus(String searchTerm, String searchTerm1, long idannouncement, boolean status) {
        return logBookRepository.searcherPostulantsByTextAnnouncementStatus(searchTerm, searchTerm1, idannouncement, status);
    }

//    --------------------------------------------- Searchers ---------------------------------------------------------
}
