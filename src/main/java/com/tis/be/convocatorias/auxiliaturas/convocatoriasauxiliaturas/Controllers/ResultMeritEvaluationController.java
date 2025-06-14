package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ResultMeritEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.IdlabelInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LabelRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultMeritEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultMeritEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultMeritEvaluationController {

    @Autowired
    private ResultMeritEvaluationService resultMeritEvaluationService;
    @Autowired
    private ResultMeritEvaluationRepository resultMeritEvaluationRepository;
    @Autowired
    private ResultFinalTeachingController resultFinalTeachingController;
    @Autowired
    private ResultFinalLaboratoryController resultFinalLaboratoryController;
    @Autowired
    private LabelRepository labelRepository;



    @RequestMapping("/resultmeritevaluation")
    public List<ResultMeritEvaluation> getAllResultMeritEvaluation() {
        return resultMeritEvaluationService.getAllResultMeritEvaluation();
    }

    @RequestMapping("/resultmeritevaluation/{id}")
    public ResultMeritEvaluation getResultMeritEvaluation(@PathVariable long id) {
        return resultMeritEvaluationService.getResultMeritEvaluation(id);
    }

    @PostMapping("/resultmeritevaluation")
    public void addResultMeritEvaluation(@RequestBody ResultMeritEvaluationInput resultMeritEvaluationInput) {
        Label label = labelRepository.getById(resultMeritEvaluationInput.getLabel());
        if(!resultMeritEvaluationRepository.existsResultMeritEvaluationByLabel_Idlabel(label.getIdlabel())) {
            ResultMeritEvaluation resultMeritEvaluation = new ResultMeritEvaluation();
            resultMeritEvaluation.setIdresultmeritevaluation(999999);
            resultMeritEvaluation.setScore(resultMeritEvaluationInput.getScore());
            resultMeritEvaluation.setLabel(label);
            resultFinalLaboratoryController.addResultFinalMeritsSingle(
                    label.getPostulantes().getIdpostulant(),
                    label.getAnnouncement().getIdannouncement(),
                    label.getAuxiliary().getIdauxiliary(),
                    resultMeritEvaluationInput.getScore(),
                    resultMeritEvaluationInput.getPercentage()
            );
            resultFinalTeachingController.addResultFinalMeritsSingle(
                    label.getPostulantes().getIdpostulant(),
                    label.getAnnouncement().getIdannouncement(),
                    label.getAuxiliary().getIdauxiliary(),
                    resultMeritEvaluationInput.getScore(),
                    resultMeritEvaluationInput.getPercentage()
            );

            resultMeritEvaluationService.addResultMeritEvaluation(resultMeritEvaluation);
        } else {
            resultFinalLaboratoryController.addResultFinalMeritsSingle(
                    label.getPostulantes().getIdpostulant(),
                    label.getAnnouncement().getIdannouncement(),
                    label.getAuxiliary().getIdauxiliary(),
                    resultMeritEvaluationInput.getScore(),
                    resultMeritEvaluationInput.getPercentage()
            );
            resultFinalTeachingController.addResultFinalMeritsSingle(
                    label.getPostulantes().getIdpostulant(),
                    label.getAnnouncement().getIdannouncement(),
                    label.getAuxiliary().getIdauxiliary(),
                    resultMeritEvaluationInput.getScore(),
                    resultMeritEvaluationInput.getPercentage()
            );
            ResultMeritEvaluation resultMeritEvaluation = resultMeritEvaluationRepository.getOne(resultMeritEvaluationRepository.getIdByLabel(label.getIdlabel()));
            resultMeritEvaluation.setScore(resultMeritEvaluationInput.getScore());
            resultMeritEvaluationRepository.save(resultMeritEvaluation);
        }
    }


    @DeleteMapping("/resultmeritevaluation/{id}")
    public void deleteResultMeritEvaluation(@PathVariable long id) {
        resultMeritEvaluationService.deleteResultMeritEvaluation(id);
    }


    @PostMapping("/resultmeritevaluation/verification/label")
    public int existByIdLabel(@RequestBody IdlabelInput idlabelInput){
        if (resultMeritEvaluationService.existByIdLabel(idlabelInput.getIdlabel())) {
            return 1;
        }else{
            return 2;
        }

    }


}
