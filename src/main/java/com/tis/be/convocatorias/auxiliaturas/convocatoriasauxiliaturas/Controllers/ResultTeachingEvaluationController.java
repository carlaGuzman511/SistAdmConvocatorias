package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ResultTeachingEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.KnowledgeEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Postulant;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultTeachingEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultTeachingEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ResultTeachingEvaluationController {

    @Autowired
    private ResultTeachingEvaluationService resultTeachingEvaluationService;
    @Autowired
    private KnowledgeEvaluationRepository knowledgeEvaluationRepository;
    @Autowired
    private PostulantRepository postulantRepository;

    @RequestMapping("/resultteachingevaluation")
    public List<ResultTeachingEvaluation> getAllResultTeachingEvaluation() {
        return resultTeachingEvaluationService.getAllResultTeachingEvaluation();
    }

    @RequestMapping("/resultteachingevaluation/{id}")
    public ResultTeachingEvaluation getResultTeachingEvaluation(@PathVariable long id) {
        return resultTeachingEvaluationService.getResultTeachingEvaluation(id);
    }

    @PostMapping("/resultteachingevaluation")
    public long addResultTeachingEvaluation(@RequestBody ResultTeachingEvaluationInput resultTeachingEvaluationInput) {
        KnowledgeEvaluation knowledgeEvaluation = knowledgeEvaluationRepository.getById(resultTeachingEvaluationInput.getKnowledgeEvaluation());
        Postulant postulant = postulantRepository.getById(resultTeachingEvaluationInput.getPostulant());
        ResultTeachingEvaluation resultTeachingEvaluation = new ResultTeachingEvaluation();
        resultTeachingEvaluation.setIdresultteachingevaluation(999999);
        resultTeachingEvaluation.setScore(resultTeachingEvaluationInput.getScore());
        resultTeachingEvaluation.setKnowledgeEvaluation(knowledgeEvaluation);
        resultTeachingEvaluation.setPostulantes(postulant);

        return resultTeachingEvaluationService.addResultTeachingEvaluation(resultTeachingEvaluation);

    }

    @DeleteMapping("/resultteachingevaluation/{id}")
    public void deleteResultTeachingEvaluation(@PathVariable long id) {
        resultTeachingEvaluationService.deleteResultTeachingEvaluation(id);
    }
}
