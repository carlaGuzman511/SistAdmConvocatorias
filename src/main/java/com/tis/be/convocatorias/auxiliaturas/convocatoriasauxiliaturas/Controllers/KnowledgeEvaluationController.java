package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.KnowledgeEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Knowledge;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.KnowledgeEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.KnowledgeEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class KnowledgeEvaluationController {

    @Autowired
    private KnowledgeEvaluationService knowledgeEvaluationService;
    @Autowired
    private KnowledgeEvaluationRepository knowledgeEvaluationRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @RequestMapping("/knowledgeevaluation")
    public List<KnowledgeEvaluation> getAllKnowledgeEvaluation() {
        return knowledgeEvaluationService.getAllKnowledgeEvaluation();
    }

    @RequestMapping("/knowledgeevaluation/{id}")
    public KnowledgeEvaluation getKnowledgeEvaluation(@PathVariable long id) {
        return knowledgeEvaluationService.getKnowledgeEvaluation(id);
    }

    @PostMapping("/knowledgeevaluation")
    public ResponseEntity<Long> addKnowledgeEvaluation(@RequestBody KnowledgeEvaluationInput knowledgeEvaluationInput) {
        Knowledge knowledge = knowledgeRepository.getById(knowledgeEvaluationInput.getKnowledge());
        if (!knowledgeEvaluationRepository.existsKnowledgeEvaluationByDescriptionAndPercentageAndKnowledge_Idknowledge(knowledgeEvaluationInput.getDescription(), knowledgeEvaluationInput.getPercentage(), knowledgeEvaluationInput.getKnowledge())){
        KnowledgeEvaluation knowledgeEvaluation = new KnowledgeEvaluation();
        knowledgeEvaluation.setIdknowledgeevaluation(999999);
        knowledgeEvaluation.setDescription(knowledgeEvaluationInput.getDescription());
        knowledgeEvaluation.setPercentage(knowledgeEvaluationInput.getPercentage());
        knowledgeEvaluation.setKnowledge(knowledge);

        return ResponseEntity.ok().body(knowledgeEvaluationService.addKnowledgeEvaluation(knowledgeEvaluation));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/knowledgeevaluation/{id}")
    public void deleteKnowledgeEvaluation(@PathVariable long id) {
        knowledgeEvaluationService.deleteKnowledgeEvaluation(id);
    }

    @RequestMapping("/knowledgeevaluation/knowledge/{id}")
    public List<KnowledgeEvaluation> ListByKnowledge(@PathVariable("id") long id) {
        return knowledgeEvaluationService.ListByKnowledge(id);
    }

}
