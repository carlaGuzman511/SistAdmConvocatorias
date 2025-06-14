package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultDetailMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultDetailMeritEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultDetailMeritEvaluationController {

    @Autowired
    private ResultDetailMeritEvaluationService resultDetailMeritEvaluationService;


    @RequestMapping("/resultdetailmeritevaluation")
    public List<ResultDetailMeritEvaluation> getAllResultDetailMeritEvaluation() {
        return resultDetailMeritEvaluationService.getAllResultDetailMeritEvaluation();
    }

    @RequestMapping("/resultdetailmeritevaluation/{id}")
    public ResultDetailMeritEvaluation getResultDetailMeritEvaluation(@PathVariable long id) {
        return resultDetailMeritEvaluationService.getResultDetailMeritEvaluation(id);
    }

    @DeleteMapping("/resultdetailmeritevaluation/{id}")
    public void deleteResultDetailMeritEvaluation(@PathVariable long id) {
        resultDetailMeritEvaluationService.deleteResultDetailMeritEvaluation(id);
    }


}
