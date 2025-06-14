package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.ResultUpdateStatusInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultFinalLaboratory;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultFinalLaboratoryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultFinalLaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ResultFinalLaboratoryController {

    @Autowired
    private ResultFinalLaboratoryService resultFinalLaboratoryService;
    @Autowired
    private ResultFinalLaboratoryRepository resultFinalLaboratoryRepository;

    @RequestMapping("/resultfinallaboratory")
    public List<ResultFinalLaboratory> getAllResultFinalLaboratory() {
        return resultFinalLaboratoryService.getAllResultFinalLaboratory();
    }

    @RequestMapping("/resultfinallaboratory/{id}")
    public ResultFinalLaboratory getResultFinalLaboratory(@PathVariable long id) {
        return resultFinalLaboratoryService.getResultFinalLaboratory(id);
    }


    public void addResultFinalLaboratorySingle(long idpostulant, long idannouncement, long idauxiliary, double scoreLaboratory, int percentage, int percentageKnowledge) {
        if(resultFinalLaboratoryService.existResultLaboratory(idpostulant, idannouncement, idauxiliary)){
            resultFinalLaboratoryService.updateResultFinalLaboratory(resultFinalLaboratoryRepository.getLabolatoryByPostulant(idpostulant, idannouncement, idauxiliary), scoreLaboratory, percentage, percentageKnowledge);
        } else {
            resultFinalLaboratoryService.addNewResultFinalLaboratory(idpostulant, idannouncement, idauxiliary, scoreLaboratory, percentage, percentageKnowledge);
        }
    }



    @DeleteMapping("/resultfinallaboratory/{id}")
    public void deleteResultFinalLaboratory(@PathVariable long id) {
        resultFinalLaboratoryService.deleteResultFinalLaboratory(id);
    }

    @PostMapping("/resultfinallaboratory/listadoMAAUA")
    public List<ResultFinalLaboratory> listByManagementAreaAcademicunitAuxiliary(@RequestBody ListByManagementAreaAcademicunitAuxiliaryInput listInput) {
        return resultFinalLaboratoryService.listByManagementAreaAcademicunitAuxiliaryOrderByScoreDesc(
                listInput.getIdmanagement(),
                listInput.getIdarea(),
                listInput.getIdacademicunit(),
                listInput.getIdauxiliary()
        );
    }

    @PutMapping("/resultfinallaboratory/update/status")
    public List<ResultFinalLaboratory> UpdateResultFinalLaboratoryStatus(@RequestBody ListResultsUpdateStatusInput listResultsUpdateStatusInput){
        List<ResultFinalLaboratory> results = new ArrayList<>();
        ResultUpdateStatusInput resultUpdateStatusInput = new ResultUpdateStatusInput();
        for (int i = 0; i< listResultsUpdateStatusInput.getResultUpdateStatusInputs().size(); i++ ){
            resultUpdateStatusInput.setIdResult(listResultsUpdateStatusInput.getResultUpdateStatusInputs().get(i).getIdResult());
            resultUpdateStatusInput.setStatus(listResultsUpdateStatusInput.getResultUpdateStatusInputs().get(i).isStatus());
            resultFinalLaboratoryService.UpdateResultFinalLaboratoryStatus(
                    resultUpdateStatusInput.getIdResult(), resultUpdateStatusInput.isStatus()
            );
            results.add(resultFinalLaboratoryService.getResultFinalLaboratory(
                    resultUpdateStatusInput.getIdResult()
            ));
        }
        return results;
    }

    @PutMapping("/resultfinallaboratory/update/single/status")
    public ResultFinalLaboratory UpdateResultFinalLaboratorySingleStatus(@RequestBody ResultUpdateStatusInput resultUpdateStatusInput){
        resultFinalLaboratoryService.UpdateResultFinalLaboratoryStatus(resultUpdateStatusInput.getIdResult(), resultUpdateStatusInput.isStatus());
        return resultFinalLaboratoryService.getResultFinalLaboratory(resultUpdateStatusInput.getIdResult());
     }

     @PostMapping("/resultfinallaboratory/list/MAAU")
    public List<ResultFinalLaboratory> ListByManagementAreaAcademicunit(@RequestBody ListByManagementAreaAcademicunit listByManagementAreaAcademicunit){
        return resultFinalLaboratoryService.ListByManagementAreaAcademicunit(
                listByManagementAreaAcademicunit.getIdmanagement(),
                listByManagementAreaAcademicunit.getIdarea(),
                listByManagementAreaAcademicunit.getIdacademicunit()
        );
     }

     @PostMapping("/resultfinallaboratory/list/MAAUstatus")
    public List<ResultFinalLaboratory> ListByManagementAreaAcademicunitStatus(@RequestBody ListByManagementAreaAcademicunitStatus listByManagementAreaAcademicunitStatus){
        return resultFinalLaboratoryService.ListByManagementAreaAcademicunitStatus(
                listByManagementAreaAcademicunitStatus.getIdmanagement(),
                listByManagementAreaAcademicunitStatus.getIdarea(),
                listByManagementAreaAcademicunitStatus.getIdacademicunit(),
                listByManagementAreaAcademicunitStatus.isStatus()
        );
     }

     @PostMapping("/resultfinallaboratory/list/MAAUAstatus")
    public List<ResultFinalLaboratory> ListByManagementAreaAcademicunitAuxiliaryStatus(@RequestBody ListByManagementAreaAcademicunitAuxiliaryStatus listByManagementAreaAcademicunitAuxiliaryStatus){
        return resultFinalLaboratoryService.ListByManagementAreaAcademicunitAuxiliaryStatus(
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdmanagement(),
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdarea(),
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdacademicunit(),
                listByManagementAreaAcademicunitAuxiliaryStatus.getIdauxiliary(),
                listByManagementAreaAcademicunitAuxiliaryStatus.isStatus()
        );
     }

    public void addResultFinalMeritsSingle(long idpostulant, long idannouncement, long idauxiliary, float scoreMerits,int percentage) {

        if(resultFinalLaboratoryService.existResultLaboratory(idpostulant, idannouncement, idauxiliary)){

            resultFinalLaboratoryService.updateResultFinalMerits(resultFinalLaboratoryRepository.getLabolatoryByPostulant(idpostulant, idannouncement, idauxiliary), scoreMerits, percentage);
        } else {
            resultFinalLaboratoryService.addNewResultFinalMerits(idpostulant, idannouncement, idauxiliary, scoreMerits, percentage);
        }
    }






    //    @PostMapping("/resultfinallaboratory")
//    public long addResultFinalLaboratory(@RequestBody ResultFinalLaboratoryInput resultFinalLaboratoryInput) {
//        Auxiliary auxiliary = auxiliaryRepository.getById(resultFinalLaboratoryInput.getAuxiliary());
//        Announcement announcement = announcementRepository.getById(resultFinalLaboratoryInput.getAnnouncement());
//        Postulant postulant = postulantRepository.getById(resultFinalLaboratoryInput.getPostulant());
//        ResultFinalLaboratory resultFinalLaboratory = new ResultFinalLaboratory();
//        resultFinalLaboratory.setIdresultfinallaboratory(999999);
//        resultFinalLaboratory.setScoreTotal(resultFinalLaboratoryInput.getScoreTotal());
//        resultFinalLaboratory.setStatus(resultFinalLaboratoryInput.isStatus());
//        resultFinalLaboratory.setScoreMerits(resultFinalLaboratoryInput.getScoreMerits());
//        resultFinalLaboratory.setScoreLaboratory(resultFinalLaboratoryInput.getScoreLaboratory());
//        resultFinalLaboratory.setAuxiliary(auxiliary);
//        resultFinalLaboratory.setAnnouncement(announcement);
//        resultFinalLaboratory.setPostulantes(postulant);
//
//        return resultFinalLaboratoryService.addResultFinalLaboratory(resultFinalLaboratory);
//
//    }
}
