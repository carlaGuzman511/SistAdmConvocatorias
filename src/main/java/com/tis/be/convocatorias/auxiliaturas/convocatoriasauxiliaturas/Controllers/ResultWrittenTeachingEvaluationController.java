package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.IdAuxilliaryKnowledgeEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByManagementAreaAcademicunitAuxiliaryInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ResultWrittenTeachingEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultWrittenTeachingEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultWrittenTeachingEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultWrittenTeachingEvaluationController {

    @Autowired
    private ResultWrittenTeachingEvaluationService resultWrittenTeachingEvaluationService;
    @Autowired
    private ResultWrittenTeachingEvaluationRepository resultWrittenTeachingEvaluationRepository;
    @Autowired
    private KnowledgeEvaluationRepository knowledgeEvaluationRepository;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;

    @RequestMapping("/resultwrittenteachingevaluation")
    public List<ResultWrittenTeachingEvaluation> getAllResultWrittenTeachingEvaluation() {
        return resultWrittenTeachingEvaluationService.getAllResultWrittenTeachingEvaluation();
    }

    @RequestMapping("/resultwrittenteachingevaluation/{id}")
    public ResultWrittenTeachingEvaluation getResultWrittenTeachingEvaluation(@PathVariable long id) {
        return resultWrittenTeachingEvaluationService.getResultWrittenTeachingEvaluation(id);
    }

    @PostMapping("/resultwrittenteachingevaluation")
    public long addResultWrittenTeachingEvaluation(@RequestBody ResultWrittenTeachingEvaluationInput resultWrittenTeachingEvaluationInput) {
        KnowledgeEvaluation knowledgeEvaluation = knowledgeEvaluationRepository.getById(resultWrittenTeachingEvaluationInput.getKnowledgeEvaluation());
        Postulant postulant = postulantRepository.getById(resultWrittenTeachingEvaluationInput.getPostulant());
        Auxiliary auxiliary = auxiliaryRepository.getById(resultWrittenTeachingEvaluationInput.getAuxiliary());
        if (!resultWrittenTeachingEvaluationRepository.existsResultWrittenTeachingEvaluationByPostulantes_IdpostulantAndKnowledgeEvaluation_IdknowledgeevaluationAndAuxiliary_Idauxiliary(resultWrittenTeachingEvaluationInput.getPostulant(), resultWrittenTeachingEvaluationInput.getKnowledgeEvaluation(), resultWrittenTeachingEvaluationInput.getAuxiliary())) {
            ResultWrittenTeachingEvaluation resultTeachingEvaluation = new ResultWrittenTeachingEvaluation();
            resultTeachingEvaluation.setIdresultwrittenteachingevaluation(999999);
            resultTeachingEvaluation.setScore(Math.round(resultWrittenTeachingEvaluationInput.getScore() *100) /100d);
            resultTeachingEvaluation.setKnowledgeEvaluation(knowledgeEvaluation);
            resultTeachingEvaluation.setPostulantes(postulant);
            resultTeachingEvaluation.setAuxiliary(auxiliary);
            resultWrittenTeachingEvaluationService.addNewScore(postulant.getIdpostulant(),
                    knowledgeEvaluation.getIdknowledgeevaluation(),
                    auxiliary.getIdauxiliary(),
                    resultWrittenTeachingEvaluationInput.getScore(),
                    knowledgeEvaluation.getPercentage(),
                    knowledgeEvaluation.getKnowledge().getFinalScore(),
                    0
            );

            return resultWrittenTeachingEvaluationService.addResultWrittenTeachingEvaluation(resultTeachingEvaluation);
        } else {
            long id = resultWrittenTeachingEvaluationRepository.getIdByPostulantKnowledgeEvaluationAuxiliary(resultWrittenTeachingEvaluationInput.getPostulant(), resultWrittenTeachingEvaluationInput.getKnowledgeEvaluation(), resultWrittenTeachingEvaluationInput.getAuxiliary());
            resultWrittenTeachingEvaluationService.addNewScore(postulant.getIdpostulant(),
                    knowledgeEvaluation.getIdknowledgeevaluation(),
                    auxiliary.getIdauxiliary(),
                    resultWrittenTeachingEvaluationInput.getScore(),
                    knowledgeEvaluation.getPercentage(),
                    knowledgeEvaluation.getKnowledge().getFinalScore(),
                    id
            );
            return -1;
        }
    }

    @DeleteMapping("/resultwrittenteachingevaluation/{id}")
    public void deleteResultWrittenTeachingEvaluation(@PathVariable long id) {
        resultWrittenTeachingEvaluationService.deleteResultWrittenTeachingEvaluation(id);
    }

    @PostMapping("/resultwrittenteachingevaluation/list/MAAUA")
    public List<ResultWrittenTeachingEvaluation> ListByIdManagementAreaAcademicUnitAuxiliary(@RequestBody ListByManagementAreaAcademicunitAuxiliaryInput listByManagementAreaAcademicunitAuxiliaryInput) {
        return resultWrittenTeachingEvaluationService.ListByIdManagementAreaAcademicUnitAuxiliary(
                listByManagementAreaAcademicunitAuxiliaryInput.getIdmanagement(),
                listByManagementAreaAcademicunitAuxiliaryInput.getIdarea(),
                listByManagementAreaAcademicunitAuxiliaryInput.getIdacademicunit(),
                listByManagementAreaAcademicunitAuxiliaryInput.getIdauxiliary()
        );
    }

    @RequestMapping("/resultwrittenteachingevaluation/postulant/{id}")
    public List<ResultWrittenTeachingEvaluation> listByIdPostulant(@PathVariable("id") long id) {
        return resultWrittenTeachingEvaluationService.listByIdPostulant(id);
    }

    @PostMapping("resultwrittenteachingevaluation/auxiliary")
    public boolean existsByIdAuxiliaryKnowledgeEvaluation(@RequestBody IdAuxilliaryKnowledgeEvaluationInput idAuxilliaryKnowledgeEvaluationInput) {
        return resultWrittenTeachingEvaluationService.existsByIdAuxiliaryKnowledgeEvaluation(idAuxilliaryKnowledgeEvaluationInput.getIdauxiliary(), idAuxilliaryKnowledgeEvaluationInput.getIdknowledgeevaluation());
    }

}
