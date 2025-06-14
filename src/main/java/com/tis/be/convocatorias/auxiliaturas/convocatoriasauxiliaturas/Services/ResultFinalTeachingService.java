package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultFinalTeachingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultFinalTeachingService {

    @Autowired
    private ResultFinalTeachingRepository resultFinalTeachingRepository;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;



    public List<ResultFinalTeaching> getAllResultFinalTeaching() {
        return resultFinalTeachingRepository.findAll();
    }


    public ResultFinalTeaching getResultFinalTeaching(long id) {
        return resultFinalTeachingRepository.FindById(id)
                .orElse(null);

    }

    public long addResultFinalTeaching(ResultFinalTeaching resultFinalTeaching) {
        resultFinalTeachingRepository.save(resultFinalTeaching);
        return resultFinalTeachingRepository.selectByIdresultfinalteaching();
    }


    public void deleteResultFinalTeaching(long id) {
        resultFinalTeachingRepository.DeleteByCi(id);
    }


    public List<ResultFinalTeaching> listByManagementAreaAcademicunitAuxiliaryOrderByScoreDesc(long idmanagement, long idarea, long idacademicunit, long idauxiliary){
        return resultFinalTeachingRepository.findByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit,
                idauxiliary
        );
    }

    public void UpdateResultFinalTeachingStatus(long id, boolean status){
        resultFinalTeachingRepository.updateResultsStatus(id, status);
    }

    public List<ResultFinalTeaching> ListByManagementAreaAcademicunit(long idmanagement, long idarea, long idacademicunit){
        return resultFinalTeachingRepository.findResultFinalTeachingByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit
        );
    }

    public List<ResultFinalTeaching> ListByManagementAreaAcademicunitStatus(long idmanagement, long idarea, long idacademicunit, boolean status){
        return resultFinalTeachingRepository.findResultFinalTeachingByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndStatusOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit,
                status
        );
    }

    public List<ResultFinalTeaching> ListByManagementAreaAcademicunitAuxiliaryStatus(long idmanagement, long idarea, long idacademicunit, long idauxiliary, boolean status){
        return resultFinalTeachingRepository.findResultFinalTeachingByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryAndStatusOrderByScoreTotalDesc(
                idmanagement,
                idarea,
                idacademicunit,
                idauxiliary,
                status
        );
    }

    public void updateResultFinalMerits(long id, double scoreMerits, int percentage){

        ResultFinalTeaching resultFinalTeaching = resultFinalTeachingRepository.getOne(id);
        resultFinalTeaching.setScoreMerits(scoreMerits*percentage/100);
        resultFinalTeaching.setScoreTotal(resultFinalTeaching.getScoreTeaching() + resultFinalTeaching.getScoreMerits());

        resultFinalTeachingRepository.save(resultFinalTeaching);

    }

    public void addNewResultFinalMerits(long idpostulant, long idannouncement, long idauxiliary, double scoreMerits, int percentage){
        Postulant postulant = postulantRepository.getById(idpostulant);
        Announcement announcement = announcementRepository.getById(idannouncement);
        Auxiliary auxiliary = auxiliaryRepository.getById(idauxiliary);
        ResultFinalTeaching resultFinalTeaching = new ResultFinalTeaching();
        resultFinalTeaching.setIdresultfinalteaching(999999);
        resultFinalTeaching.setScoreMerits(Math.round(scoreMerits*percentage) / 100d);
        resultFinalTeaching.setScoreTotal(Math.round((resultFinalTeaching.getScoreTeaching() + resultFinalTeaching.getScoreMerits()) * 100) / 100d);
        resultFinalTeaching.setPostulantes(postulant);
        resultFinalTeaching.setAnnouncement(announcement);
        resultFinalTeaching.setAuxiliary(auxiliary);

        resultFinalTeachingRepository.save(resultFinalTeaching);
    }

    public void updateResultOralFinal(long byIdPostulant, double scoreOral, int percentage, int percentageFinal) {

        ResultFinalTeaching resultFinalTeaching = resultFinalTeachingRepository.getOne(byIdPostulant);
        resultFinalTeaching.setScoreOralTeaching(Math.round(scoreOral*percentage) / 100d);
        resultFinalTeaching.setScoreTeaching(Math.round((resultFinalTeaching.getScoreOralTeaching() + resultFinalTeaching.getScoreWrittenTeaching())*percentageFinal) /100d);
        resultFinalTeaching.setScoreTotal(resultFinalTeaching.getScoreMerits() + resultFinalTeaching.getScoreTeaching());

        resultFinalTeachingRepository.save(resultFinalTeaching);
    }

    public void addNewResultOralFinal(long idpostulant, long idannouncement, long idauxiliary, double scoreOral, int percentage, int percentageFinal) {
        Postulant postulant = postulantRepository.getById(idpostulant);
        Announcement announcement = announcementRepository.getById(idannouncement);
        Auxiliary auxiliary = auxiliaryRepository.getById(idauxiliary);
        ResultFinalTeaching resultFinalTeaching = new ResultFinalTeaching();
        resultFinalTeaching.setIdresultfinalteaching(999999);
        resultFinalTeaching.setScoreOralTeaching(Math.round(scoreOral*percentage)/ 100d);
        resultFinalTeaching.setScoreTeaching(Math.round((resultFinalTeaching.getScoreOralTeaching() + resultFinalTeaching.getScoreWrittenTeaching())*percentageFinal) /100d);
        resultFinalTeaching.setScoreTotal(Math.round((resultFinalTeaching.getScoreMerits() + resultFinalTeaching.getScoreTeaching()) *100) /100d);
        resultFinalTeaching.setPostulantes(postulant);
        resultFinalTeaching.setAnnouncement(announcement);
        resultFinalTeaching.setAuxiliary(auxiliary);

        resultFinalTeachingRepository.save(resultFinalTeaching);
    }


    public void updateResultWrittenFinal(long byIdPostulant, double scoreWritten, int percentage, int percentageFinal) {

        ResultFinalTeaching resultFinalTeaching = resultFinalTeachingRepository.getOne(byIdPostulant);
        resultFinalTeaching.setScoreWrittenTeaching(Math.round(scoreWritten*percentage) / 100d);
        resultFinalTeaching.setScoreTeaching(Math.round((resultFinalTeaching.getScoreWrittenTeaching() + resultFinalTeaching.getScoreOralTeaching())*percentageFinal) /100d);
        resultFinalTeaching.setScoreTotal(Math.round((resultFinalTeaching.getScoreMerits() + resultFinalTeaching.getScoreTeaching()) * 100) / 100d);

        resultFinalTeachingRepository.save(resultFinalTeaching);
    }

    public void addNewResultWrittenFinal(long idpostulant, long idannouncement, long idauxiliary, double scoreWritten, int percentage, int percentageFinal) {
        Postulant postulant = postulantRepository.getById(idpostulant);
        Announcement announcement = announcementRepository.getById(idannouncement);
        Auxiliary auxiliary = auxiliaryRepository.getById(idauxiliary);
        ResultFinalTeaching resultFinalTeaching = new ResultFinalTeaching();
        resultFinalTeaching.setIdresultfinalteaching(999999);
        resultFinalTeaching.setScoreWrittenTeaching(Math.round(scoreWritten*percentage) /100d);
        resultFinalTeaching.setScoreTeaching(Math.round((resultFinalTeaching.getScoreWrittenTeaching() + resultFinalTeaching.getScoreOralTeaching())*percentageFinal) /100d);
        resultFinalTeaching.setScoreTotal(Math.round((resultFinalTeaching.getScoreMerits() + resultFinalTeaching.getScoreTeaching()) *100 ) /100d);
        resultFinalTeaching.setPostulantes(postulant);
        resultFinalTeaching.setAnnouncement(announcement);
        resultFinalTeaching.setAuxiliary(auxiliary);

        resultFinalTeachingRepository.save(resultFinalTeaching);
    }
}
