package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.ResultUpdateStatusInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultFinalTeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ResultFinalTeachingController {

    @Autowired
    private ResultFinalTeachingService resultFinalTeachingService;
    @Autowired
    private ResultFinalTeachingRepository resultFinalTeachingRepository;

    @RequestMapping("/resultfinalteaching")
    public List<ResultFinalTeaching> getAllResultFinalTeaching() {
        return resultFinalTeachingService.getAllResultFinalTeaching();
    }

    @RequestMapping("/resultfinalteaching/{id}")
    public ResultFinalTeaching getResultFinalTeaching(@PathVariable long id) {
        return resultFinalTeachingService.getResultFinalTeaching(id);
    }


    public void addResultFinalMeritsSingle(long idpostulant, long idannouncement, long idauxiliary, double scoreMerits, int percentage) {

        if (resultFinalTeachingRepository.existsResultFinalTeachingByPostulantes_IdpostulantAndAnnouncement_IdannouncementAndAuxiliary_Idauxiliary(idpostulant, idannouncement, idauxiliary)) {
            resultFinalTeachingService.updateResultFinalMerits(resultFinalTeachingRepository.getByIdPostulant(idpostulant, idannouncement, idauxiliary), scoreMerits, percentage);
        } else {
            resultFinalTeachingService.addNewResultFinalMerits(idpostulant, idannouncement, idauxiliary, scoreMerits, percentage);
        }
    }

    public void addResultOralTeaching(long idpostulant, long idknowledgeevaluation, long idauxiliary, double scoreOral, int percentage, int percentageFinal){
        long idannouncement = resultFinalTeachingRepository.idAnnouncement(idknowledgeevaluation);
        if (resultFinalTeachingRepository.existsResultFinalTeachingByPostulantes_IdpostulantAndAnnouncement_IdannouncementAndAuxiliary_Idauxiliary(idpostulant,idannouncement,idauxiliary)) {
            resultFinalTeachingService.updateResultOralFinal(resultFinalTeachingRepository.getByIdPostulant(idpostulant, idannouncement, idauxiliary), scoreOral, percentage, percentageFinal);
        }else {
            resultFinalTeachingService.addNewResultOralFinal(idpostulant, idannouncement, idauxiliary, scoreOral, percentage, percentageFinal);
        }
    }

    public void updateResultOralTeaching(long idpostulant, long idknowledgeevaluation, long idauxiliary, double scoreOral, int percentage, int percentageFinal){
            long idannouncement = resultFinalTeachingRepository.idAnnouncement(idknowledgeevaluation);
            resultFinalTeachingService.updateResultOralFinal(resultFinalTeachingRepository.getByIdPostulant(idpostulant, idannouncement, idauxiliary), scoreOral, percentage, percentageFinal);
    }

    public void addResultWrittenTeaching(long idpostulant, long idknowledgeevaluation, long idauxiliary, double scoreWritten, int percentage, int percentageFinal) {
        long idannouncement = resultFinalTeachingRepository.idAnnouncement(idknowledgeevaluation);
        if (resultFinalTeachingRepository.existsResultFinalTeachingByPostulantes_IdpostulantAndAnnouncement_IdannouncementAndAuxiliary_Idauxiliary(idpostulant,idannouncement,idauxiliary)) {
            resultFinalTeachingService.updateResultWrittenFinal(resultFinalTeachingRepository.getByIdPostulant(idpostulant, idannouncement, idauxiliary), scoreWritten, percentage, percentageFinal);
        }else {
            resultFinalTeachingService.addNewResultWrittenFinal(idpostulant, idannouncement, idauxiliary, scoreWritten, percentage, percentageFinal);
        }
    }

    public void updateResultWrittenTeaching(long idpostulant, long idknowledgeevaluation, long idauxiliary, double scoreWritten, int percentage, int percentageFinal) {
        long idannouncement = resultFinalTeachingRepository.idAnnouncement(idknowledgeevaluation);
        resultFinalTeachingService.updateResultWrittenFinal(resultFinalTeachingRepository.getByIdPostulant(idpostulant, idannouncement, idauxiliary), scoreWritten, percentage, percentageFinal);
    }

    @PostMapping("/resultfinalteaching/listadoMAAUA")
    public List<ResultFinalTeaching> listByManagementAreaAcademicunitAuxiliary(@RequestBody ListByManagementAreaAcademicunitAuxiliaryInput listInput) {
        return resultFinalTeachingService.listByManagementAreaAcademicunitAuxiliaryOrderByScoreDesc(
                listInput.getIdmanagement(),
                listInput.getIdarea(),
                listInput.getIdacademicunit(),
                listInput.getIdauxiliary()
        );
    }

    @PutMapping("/resultfinalteaching/update/status")
    public List<ResultFinalTeaching> UpdateResultFinalTeachingStatus(@RequestBody ListResultsUpdateStatusInput listResultsUpdateStatusInput){
        List<ResultFinalTeaching> results = new ArrayList<>();
        ResultUpdateStatusInput resultUpdateStatusInput = new ResultUpdateStatusInput();
        for (int i = 0; i< listResultsUpdateStatusInput.getResultUpdateStatusInputs().size(); i++ ){
            resultUpdateStatusInput.setIdResult(listResultsUpdateStatusInput.getResultUpdateStatusInputs().get(i).getIdResult());
            resultUpdateStatusInput.setStatus(listResultsUpdateStatusInput.getResultUpdateStatusInputs().get(i).isStatus());
            resultFinalTeachingService.UpdateResultFinalTeachingStatus(
                    resultUpdateStatusInput.getIdResult(), resultUpdateStatusInput.isStatus()
            );
            results.add(resultFinalTeachingService.getResultFinalTeaching(
                    resultUpdateStatusInput.getIdResult()
            ));
        }
        return results;
    }

    @PutMapping("/resultfinalteaching/update/single/status")
    public ResultFinalTeaching UpdateResultFinalTeachingSingleStatus(@RequestBody ResultUpdateStatusInput resultUpdateStatusInput){
        resultFinalTeachingService.UpdateResultFinalTeachingStatus(resultUpdateStatusInput.getIdResult(), resultUpdateStatusInput.isStatus());
        return resultFinalTeachingService.getResultFinalTeaching(resultUpdateStatusInput.getIdResult());
     }

     @PostMapping("/resultfinalteaching/list/MAAU")
    public List<ResultFinalTeaching> ListByManagementAreaAcademicunit(@RequestBody ListByManagementAreaAcademicunit listByManagementAreaAcademicunit){
        return resultFinalTeachingService.ListByManagementAreaAcademicunit(
                listByManagementAreaAcademicunit.getIdmanagement(),
                listByManagementAreaAcademicunit.getIdarea(),
                listByManagementAreaAcademicunit.getIdacademicunit()
        );
     }

     @PostMapping("/resultfinalteaching/list/MAAUstatus")
    public List<ResultFinalTeaching> ListByManagementAreaAcademicunitStatus(@RequestBody ListByManagementAreaAcademicunitStatus listByManagementAreaAcademicunitStatus){
        return resultFinalTeachingService.ListByManagementAreaAcademicunitStatus(
                listByManagementAreaAcademicunitStatus.getIdmanagement(),
                listByManagementAreaAcademicunitStatus.getIdarea(),
                listByManagementAreaAcademicunitStatus.getIdacademicunit(),
                listByManagementAreaAcademicunitStatus.isStatus()
        );
     }

     @PostMapping("/resultfinalteaching/list/MAAUAstatus")
    public List<ResultFinalTeaching> ListByManagementAreaAcademicunitAuxiliaryStatus(@RequestBody ListByManagementAreaAcademicunitAuxiliaryStatus listByManagementAreaAcademicunitAuxiliaryStatus){
        return resultFinalTeachingService.ListByManagementAreaAcademicunitAuxiliaryStatus(
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdmanagement(),
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdarea(),
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdacademicunit(),
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdauxiliary(),
                listByManagementAreaAcademicunitAuxiliaryStatus.isStatus()
        );
     }

    @DeleteMapping("/resultfinalteaching/{id}")
    public void deleteResultFinalTeaching(@PathVariable long id) {
        resultFinalTeachingService.deleteResultFinalTeaching(id);
    }












    //    @PostMapping("/resultfinalteaching")
//    public long addResultFinalTeaching(@RequestBody ResultFinalTeachingInput resultFinalTeachingInput) {
//        Auxiliary auxiliary = auxiliaryRepository.getById(resultFinalTeachingInput.getAuxiliary());
//        Announcement announcement = announcementRepository.getById(resultFinalTeachingInput.getAnnouncement());
//        Postulant postulant = postulantRepository.getById(resultFinalTeachingInput.getPostulant());
//        ResultFinalTeaching resultFinalTeaching = new ResultFinalTeaching();
//        resultFinalTeaching.setIdresultfinalteaching(999999);
//        resultFinalTeaching.setScoreTotal(resultFinalTeachingInput.getScoreTotal());
//        resultFinalTeaching.setStatus(resultFinalTeachingInput.isStatus());
//        resultFinalTeaching.setScoreMerits(resultFinalTeachingInput.getScoreMerits());
//        resultFinalTeaching.setScoreTeaching(resultFinalTeachingInput.getScoreTeaching());
//        resultFinalTeaching.setScoreOralTeaching(resultFinalTeachingInput.getScoreOralTeaching());
//        resultFinalTeaching.setScoreWrittenTeaching(resultFinalTeachingInput.getScoreWrittenTeaching());
//        resultFinalTeaching.setAuxiliary(auxiliary);
//        resultFinalTeaching.setAnnouncement(announcement);
//        resultFinalTeaching.setPostulantes(postulant);
//
//        return resultFinalTeachingService.addResultFinalTeaching(resultFinalTeaching);
//
//    }
}
