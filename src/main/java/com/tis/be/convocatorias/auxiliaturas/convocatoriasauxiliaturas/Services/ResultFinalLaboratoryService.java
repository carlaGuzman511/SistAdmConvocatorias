package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Auxiliary;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Postulant;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultFinalLaboratory;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultFinalLaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultFinalLaboratoryService {

    @Autowired
    private ResultFinalLaboratoryRepository resultFinalLaboratoryRepository;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;


    public List<ResultFinalLaboratory> getAllResultFinalLaboratory() {
        return resultFinalLaboratoryRepository.findAll();
    }


    public ResultFinalLaboratory getResultFinalLaboratory(long id) {
        return resultFinalLaboratoryRepository.FindById(id)
                .orElse(null);

    }

    public long addResultFinalLaboratory(ResultFinalLaboratory resultFinalLaboratory) {
        resultFinalLaboratoryRepository.save(resultFinalLaboratory);
        return resultFinalLaboratoryRepository.selectByIdresultfinallaboratory();
    }

    public void addNewResultFinalLaboratory(long idpostulant, long idannouncement, long idauxiliary, double scoreLaboratory, int percentage, int percentageKnowledge) {
        Postulant postulant = postulantRepository.getById(idpostulant);
        Announcement announcement = announcementRepository.getById(idannouncement);
        Auxiliary auxiliary = auxiliaryRepository.getById(idauxiliary);
        ResultFinalLaboratory resultFinalLaboratory = new ResultFinalLaboratory();
        resultFinalLaboratory.setIdresultfinallaboratory(999999);
        resultFinalLaboratory.setScoreLaboratory(Math.round((scoreLaboratory * percentage / 100) * percentageKnowledge) / 100d);
        resultFinalLaboratory.setScoreTotal(Math.round((resultFinalLaboratory.getScoreLaboratory() + resultFinalLaboratory.getScoreMerits()) * 100) / 100d);
        resultFinalLaboratory.setPostulantes(postulant);
        resultFinalLaboratory.setAnnouncement(announcement);
        resultFinalLaboratory.setAuxiliary(auxiliary);

        resultFinalLaboratoryRepository.save(resultFinalLaboratory);
    }

    public void updateResultFinalLaboratory(long id, double scoreLaboratory, int percentage, int percentageKnowledge) {
        double aux;
        ResultFinalLaboratory resultFinalLaboratory = resultFinalLaboratoryRepository.getOne(id);
        aux = (scoreLaboratory * percentage / 100) + (resultFinalLaboratory.getScoreLaboratory() * 100 / percentageKnowledge);
        resultFinalLaboratory.setScoreLaboratory(Math.round(aux  * percentageKnowledge) / 100d);
        resultFinalLaboratory.setScoreTotal(Math.round((resultFinalLaboratory.getScoreLaboratory() + resultFinalLaboratory.getScoreMerits()) * 100) / 100d);

        resultFinalLaboratoryRepository.save(resultFinalLaboratory);

    }

    public void updateResultFinalMerits(long id, double scoreMerits, int percentage) {
        ResultFinalLaboratory resultFinalLaboratory = resultFinalLaboratoryRepository.getOne(id);
        resultFinalLaboratory.setScoreMerits(Math.round(scoreMerits * percentage) / 100d);
        resultFinalLaboratory.setScoreTotal(Math.round((resultFinalLaboratory.getScoreMerits() + resultFinalLaboratory.getScoreLaboratory()) * 100) / 100d);

        resultFinalLaboratoryRepository.save(resultFinalLaboratory);
    }

    public void addNewResultFinalMerits(long idpostulant, long idannouncement, long idauxiliary, double scoreMerits, int percentage) {
        Postulant postulant = postulantRepository.getById(idpostulant);
        Announcement announcement = announcementRepository.getById(idannouncement);
        Auxiliary auxiliary = auxiliaryRepository.getById(idauxiliary);
        ResultFinalLaboratory resultFinalLaboratory = new ResultFinalLaboratory();
        resultFinalLaboratory.setIdresultfinallaboratory(999999);
        resultFinalLaboratory.setScoreMerits(Math.round(scoreMerits * percentage) / 100d);
        resultFinalLaboratory.setScoreTotal(Math.round((resultFinalLaboratory.getScoreMerits() + resultFinalLaboratory.getScoreLaboratory()) * 100) / 100d);
        resultFinalLaboratory.setPostulantes(postulant);
        resultFinalLaboratory.setAnnouncement(announcement);
        resultFinalLaboratory.setAuxiliary(auxiliary);

        resultFinalLaboratoryRepository.save(resultFinalLaboratory);
    }

    public boolean existResultLaboratory(long idpostulant, long idannouncement, long idauxiliary) {
        return resultFinalLaboratoryRepository.existsResultFinalLaboratoryByPostulantes_IdpostulantAndAnnouncement_IdannouncementAndAuxiliary_Idauxiliary(
                idpostulant,
                idannouncement,
                idauxiliary
        );
    }


    public void deleteResultFinalLaboratory(long id) {
        resultFinalLaboratoryRepository.DeleteByCi(id);
    }


    public List<ResultFinalLaboratory> listByManagementAreaAcademicunitAuxiliaryOrderByScoreDesc(long idmanagement, long idarea, long idacademicunit, long idauxiliary) {
        return resultFinalLaboratoryRepository.findByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit,
                idauxiliary
        );
    }

    public void UpdateResultFinalLaboratoryStatus(long id, boolean status) {
        resultFinalLaboratoryRepository.updateResultFinalLaboratoryStatus(id, status);
    }

    public List<ResultFinalLaboratory> ListByManagementAreaAcademicunit(long idmanagement, long idarea, long idacademicunit) {
        return resultFinalLaboratoryRepository.findResultFinalLaboratoriesByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit
        );
    }

    public List<ResultFinalLaboratory> ListByManagementAreaAcademicunitStatus(long idmanagement, long idarea, long idacademicunit, boolean status) {
        return resultFinalLaboratoryRepository.findResultFinalLaboratoryByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndStatusOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit,
                status
        );
    }

    public List<ResultFinalLaboratory> ListByManagementAreaAcademicunitAuxiliaryStatus(long idmanagement, long idarea, long idacademicunit, long idauxiliary, boolean status) {
        return resultFinalLaboratoryRepository.findResultFinalLaboratoryByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryAndStatusOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit,
                idauxiliary,
                status
        );
    }
}
