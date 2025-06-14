package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultSubsubdetailMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultSubsubdetailMeritEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultSubsubdetailMeritEvaluationController {

    @Autowired
    private ResultSubsubdetailMeritEvaluationService resultSubsubdetailMeritEvaluationService;


    @RequestMapping("/resultsubsubdetailmeritevaluation")
    public List<ResultSubsubdetailMeritEvaluation> getAllResultSubsubdetailMeritEvaluation() {
        return resultSubsubdetailMeritEvaluationService.getAllResultSubsubdetailMeritEvaluation();
    }

    @RequestMapping("/resultsubsubdetailmeritevaluation/{id}")
    public ResultSubsubdetailMeritEvaluation getResultSubsubdetailMeritEvaluation(@PathVariable long id) {
        return resultSubsubdetailMeritEvaluationService.getResultSubsubdetailMeritEvaluation(id);
    }

    @DeleteMapping("/resultsubsubdetailmeritevaluation/{id}")
    public void deleteResultSubsubdetailMeritEvaluation(@PathVariable long id) {
        resultSubsubdetailMeritEvaluationService.deleteResultSubsubdetailMeritEvaluation(id);
    }


}
