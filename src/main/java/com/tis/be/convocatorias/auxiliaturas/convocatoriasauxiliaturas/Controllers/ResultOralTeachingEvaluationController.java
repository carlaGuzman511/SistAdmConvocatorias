package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.IdAuxilliaryKnowledgeEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByManagementAreaAcademicunitAuxiliaryInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ResultOralTeachingEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultOralTeachingEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultOralTeachingEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultOralTeachingEvaluationController {

    @Autowired
    private ResultOralTeachingEvaluationService resultOralTeachingEvaluationService;
    @Autowired
    private ResultOralTeachingEvaluationRepository resultOralTeachingEvaluationRepository;
    @Autowired
    private KnowledgeEvaluationRepository knowledgeEvaluationRepository;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;


    @RequestMapping("/resultoralteachingevaluation")
    public List<ResultOralTeachingEvaluation> getAllResultOralTeachingEvaluation() {
        return resultOralTeachingEvaluationService.getAllResultOralTeachingEvaluation();
    }

    @RequestMapping("/resultoralteachingevaluation/{id}")
    public ResultOralTeachingEvaluation getResultOralTeachingEvaluation(@PathVariable long id) {
        return resultOralTeachingEvaluationService.getResultOralTeachingEvaluation(id);
    }

    @PostMapping("/resultoralteachingevaluation")
    public long addResultOralTeachingEvaluation(@RequestBody ResultOralTeachingEvaluationInput resultOralTeachingEvaluationInput) {
        KnowledgeEvaluation knowledgeEvaluation = knowledgeEvaluationRepository.getById(resultOralTeachingEvaluationInput.getKnowledgeEvaluation());
        Postulant postulant = postulantRepository.getById(resultOralTeachingEvaluationInput.getPostulant());
        Auxiliary auxiliary = auxiliaryRepository.getById(resultOralTeachingEvaluationInput.getAuxiliary());
        if (!resultOralTeachingEvaluationRepository.existsResultOralTeachingEvaluationByPostulantes_IdpostulantAndKnowledgeEvaluation_IdknowledgeevaluationAndAuxiliary_Idauxiliary(
                resultOralTeachingEvaluationInput.getPostulant(),
                resultOralTeachingEvaluationInput.getKnowledgeEvaluation(),
                resultOralTeachingEvaluationInput.getAuxiliary())
        ) {
            ResultOralTeachingEvaluation resultOralTeachingEvaluation = new ResultOralTeachingEvaluation();
            resultOralTeachingEvaluation.setIdresultoralteachingevaluation(999999);
            resultOralTeachingEvaluation.setScore(Math.round(resultOralTeachingEvaluationInput.getScore() *100) / 100d);
            resultOralTeachingEvaluation.setKnowledgeEvaluation(knowledgeEvaluation);
            resultOralTeachingEvaluation.setPostulantes(postulant);
            resultOralTeachingEvaluation.setAuxiliary(auxiliary);
            resultOralTeachingEvaluationService.addNewScore(postulant.getIdpostulant(),
                    knowledgeEvaluation.getIdknowledgeevaluation(),
                    auxiliary.getIdauxiliary(),
                    resultOralTeachingEvaluationInput.getScore(),
                    knowledgeEvaluation.getPercentage(),
                    knowledgeEvaluation.getKnowledge().getFinalScore(),
                    0
            );
            return resultOralTeachingEvaluationService.addResultOralTeachingEvaluation(resultOralTeachingEvaluation);
        }else {
            long id = resultOralTeachingEvaluationRepository.getIdByPostulantKnowledgeEvaluationAuxiliary(
                    resultOralTeachingEvaluationInput.getPostulant(),
                    resultOralTeachingEvaluationInput.getKnowledgeEvaluation(),
                    resultOralTeachingEvaluationInput.getAuxiliary()
        );
            resultOralTeachingEvaluationService.addNewScore(postulant.getIdpostulant(),
                    knowledgeEvaluation.getIdknowledgeevaluation(),
                    auxiliary.getIdauxiliary(),
                    (float) resultOralTeachingEvaluationInput.getScore(),
                    knowledgeEvaluation.getPercentage(),
                    knowledgeEvaluation.getKnowledge().getFinalScore(),
                    id
            );
            return -1;
        }
    }

    @DeleteMapping("/resultoralteachingevaluation/{id}")
    public void deleteResultOralTeachingEvaluation(@PathVariable long id) {
        resultOralTeachingEvaluationService.deleteResultOralTeachingEvaluation(id);
    }

    @PostMapping("/resultoralteachingevaluation/list/MAAUA")
    public List<ResultOralTeachingEvaluation> ListByIdManagementAreaAcademicUnitAuxiliary(@RequestBody ListByManagementAreaAcademicunitAuxiliaryInput listByManagementAreaAcademicunitAuxiliaryInput) {
        return resultOralTeachingEvaluationService.listByIdManagementAreaAcademicUnitAuxiliary(
                listByManagementAreaAcademicunitAuxiliaryInput.getIdmanagement(),
                listByManagementAreaAcademicunitAuxiliaryInput.getIdarea(),
                listByManagementAreaAcademicunitAuxiliaryInput.getIdacademicunit(),
                listByManagementAreaAcademicunitAuxiliaryInput.getIdauxiliary()
        );
    }

    @RequestMapping("resultoralteachingevaluation/postulant/{id}")
    public List<ResultOralTeachingEvaluation> listByIdPostulant(@PathVariable("id") long id) {
        return resultOralTeachingEvaluationService.listByIdPostulant(id);
    }

    @PostMapping("resultoralteachingevaluation/auxiliary")
    public boolean existsByIdAuxiliaryKnowledgeEvaluation(@RequestBody IdAuxilliaryKnowledgeEvaluationInput idAuxilliaryKnowledgeEvaluationInput) {
        return resultOralTeachingEvaluationService.existsByIdAuxiliaryKnowledgeEvaluation(idAuxilliaryKnowledgeEvaluationInput.getIdauxiliary(), idAuxilliaryKnowledgeEvaluationInput.getIdknowledgeevaluation());
    }


}
