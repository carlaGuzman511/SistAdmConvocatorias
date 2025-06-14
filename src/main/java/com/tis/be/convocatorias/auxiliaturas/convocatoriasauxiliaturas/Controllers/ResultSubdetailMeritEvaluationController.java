package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultSubdetailMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultSubdetailMeritEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultSubdetailMeritEvaluationController {

    @Autowired
    private ResultSubdetailMeritEvaluationService resultSubdetailMeritEvaluationService;


    @RequestMapping("/resultsubdetailmeritevaluation")
    public List<ResultSubdetailMeritEvaluation> getAllResultSubdetailMeritEvaluation() {
        return resultSubdetailMeritEvaluationService.getAllResultSubdetailMeritEvaluation();
    }

    @RequestMapping("/resultsubdetailmeritevaluation/{id}")
    public ResultSubdetailMeritEvaluation getResultSubdetailMeritEvaluation(@PathVariable long id) {
        return resultSubdetailMeritEvaluationService.getResultSubdetailMeritEvaluation(id);
    }


    @DeleteMapping("/resultsubdetailmeritevaluation/{id}")
    public void deleteResultSubdetailMeritEvaluation(@PathVariable long id) {
        resultSubdetailMeritEvaluationService.deleteResultSubdetailMeritEvaluation(id);
    }


}
