package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.LaboratoryEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Auxiliary;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Knowledge;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LaboratoryEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ThematicRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.LaboratoryEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class LaboratoryEvaluationController {

    @Autowired
    private LaboratoryEvaluationService laboratoryEvaluationService;
    @Autowired
    private LaboratoryEvaluationRepository laboratoryEvaluationRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;
    @Autowired
    private ThematicRepository thematicRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @RequestMapping("/laboratoryevaluation")
    public List<LaboratoryEvaluation> getAllLaboratoryEvaluation() {
        return laboratoryEvaluationService.getAllLaboratoryEvaluation();
    }

    @RequestMapping("/laboratoryevaluation/{id}")
    public LaboratoryEvaluation getLaboratoryEvaluation(@PathVariable long id) {
        return laboratoryEvaluationService.getLaboratoryEvaluation(id);
    }

    @PostMapping("/laboratoryevaluation")
    public ResponseEntity<Long> addLaboratoryEvaluation(@RequestBody LaboratoryEvaluationInput laboratoryEvaluationInput) {
        Auxiliary auxiliary = auxiliaryRepository.getById(laboratoryEvaluationInput.getAuxiliary());
        Thematic thematic = thematicRepository.getById(laboratoryEvaluationInput.getThematic());
        Knowledge knowledge = knowledgeRepository.getById(laboratoryEvaluationInput.getKnowledge());
        if (!laboratoryEvaluationRepository.existsLaboratoryEvaluationByAuxiliary_IdauxiliaryAndKnowledge_IdknowledgeAndThematic_IdthematicAndPercentage(
                laboratoryEvaluationInput.getAuxiliary(), laboratoryEvaluationInput.getKnowledge(), laboratoryEvaluationInput.getThematic(), laboratoryEvaluationInput.getPercentage())){
        LaboratoryEvaluation laboratoryEvaluation = new LaboratoryEvaluation();
        laboratoryEvaluation.setIdlaboratoryevaluation(999999);
        laboratoryEvaluation.setPercentage(laboratoryEvaluationInput.getPercentage());
        laboratoryEvaluation.setAuxiliary(auxiliary);
        laboratoryEvaluation.setThematic(thematic);
        laboratoryEvaluation.setKnowledge(knowledge);

        return ResponseEntity.ok().body(laboratoryEvaluationService.addLaboratoryEvaluation(laboratoryEvaluation));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/laboratoryevaluation/{id}")
    public void deleteLaboratoryEvaluation(@PathVariable long id) {
        laboratoryEvaluationService.deleteLaboratoryEvaluation(id);
    }


    @RequestMapping("/laboratoryevaluation/auxiliary/{id}")
    public List<LaboratoryEvaluation> ListByAuxiliary(@PathVariable("id") long id) {
        return laboratoryEvaluationService.ListByAuxiliary(id);
    }

    @RequestMapping("/laboratoryevaluation/thematic/{id}")
    public List<LaboratoryEvaluation> ListByThematic(@PathVariable("id") long id){
        return laboratoryEvaluationService.ListByThematic(id);
    }

    @RequestMapping("/laboratoryevaluation/knowledge/{id}")
    public List<LaboratoryEvaluation> ListByKnowledge(@PathVariable("id") long id){
        return laboratoryEvaluationService.ListByKnowledge(id);
    }
}
