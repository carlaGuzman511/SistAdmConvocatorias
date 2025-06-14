package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.IdLaboratoryevaluationPostulantInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByManagementAreaAcademicunitAuxiliaryInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ResultLaboratoryEvaluationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Postulant;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultLaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LaboratoryEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultLaboratoryEvaluationRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ResultLaboratoryEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ResultLaboratoryEvaluationController {

    @Autowired
    private ResultLaboratoryEvaluationService resultLaboratoryEvaluationService;
    @Autowired
    private ResultLaboratoryEvaluationRepository resultLaboratoryEvaluationRepository;
    @Autowired
    private LaboratoryEvaluationRepository laboratoryEvaluationRepository;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private ResultFinalLaboratoryController resultFinalLaboratoryController;

    @RequestMapping("/resultlaboratoryevaluation")
    public List<ResultLaboratoryEvaluation> getAllResultLaboratoryEvaluation() {
        return resultLaboratoryEvaluationService.getAllResultLaboratoryEvaluation();
    }

    @RequestMapping("/resultlaboratoryevaluation/{id}")
    public ResultLaboratoryEvaluation getResultLaboratoryEvaluation(@PathVariable long id) {
        return resultLaboratoryEvaluationService.getResultLaboratoryEvaluation(id);
    }

    @RequestMapping("/resultlaboratoryevaluation/postulant/{id}")
    public List<ResultLaboratoryEvaluation> getResultLaboratoryEvaluationByIdpostulant(@PathVariable long id) {
        return resultLaboratoryEvaluationService.getResultLaboratoryEvaluationByIdpostulant(id);
    }

    @PostMapping("/resultlaboratoryevaluation")
    public long addResultLaboratoryEvaluation(@RequestBody ResultLaboratoryEvaluationInput resultLaboratoryEvaluationInput) {
        LaboratoryEvaluation laboratoryEvaluation = laboratoryEvaluationRepository.getById(resultLaboratoryEvaluationInput.getLaboratoryEvaluation());
        Postulant postulant = postulantRepository.getById(resultLaboratoryEvaluationInput.getPostulant());
        if (!resultLaboratoryEvaluationRepository.existsResultLaboratoryEvaluationByPostulantes_IdpostulantAndLaboratoryEvaluation_Idlaboratoryevaluation(
                postulant.getIdpostulant(), laboratoryEvaluation.getIdlaboratoryevaluation())
        ) {
            ResultLaboratoryEvaluation resultLaboratoryEvaluation = new ResultLaboratoryEvaluation();
            resultLaboratoryEvaluation.setIdresultlaboratoryevaluation(999999);
            resultLaboratoryEvaluation.setScore(resultLaboratoryEvaluationInput.getScore());
            resultLaboratoryEvaluation.setLaboratoryEvaluation(laboratoryEvaluation);
            resultLaboratoryEvaluation.setPostulantes(postulant);
            resultFinalLaboratoryController.addResultFinalLaboratorySingle(
                    postulant.getIdpostulant(),
                    laboratoryEvaluation.getKnowledge().getAnnouncement().getIdannouncement(),
                    laboratoryEvaluation.getAuxiliary().getIdauxiliary(),
                    resultLaboratoryEvaluation.getScore(),
                    laboratoryEvaluation.getPercentage(),
                    laboratoryEvaluation.getKnowledge().getFinalScore()
            );

            return resultLaboratoryEvaluationService.addResultLaboratoryEvaluation(resultLaboratoryEvaluation);
        }else {
            resultFinalLaboratoryController.addResultFinalLaboratorySingle(
                    postulant.getIdpostulant(),
                    laboratoryEvaluation.getKnowledge().getAnnouncement().getIdannouncement(),
                    laboratoryEvaluation.getAuxiliary().getIdauxiliary(),
                    (float) resultLaboratoryEvaluationInput.getScore(),
                    laboratoryEvaluation.getPercentage(),
                    laboratoryEvaluation.getKnowledge().getFinalScore()
            );
            ResultLaboratoryEvaluation resultLaboratoryEvaluation = resultLaboratoryEvaluationRepository.getOne(
                    resultLaboratoryEvaluationRepository.getIdByPostulantAndLaboratoryevaluation(
                    postulant.getIdpostulant(), laboratoryEvaluation.getIdlaboratoryevaluation())
            );
            resultLaboratoryEvaluation.setScore((float) resultLaboratoryEvaluationInput.getScore());
            resultLaboratoryEvaluationRepository.save(resultLaboratoryEvaluation);
            return -1;
        }
    }

    @DeleteMapping("/resultlaboratoryevaluation/{id}")
    public void deleteResultLaboratoryEvaluation(@PathVariable long id) {
        resultLaboratoryEvaluationService.deleteResultLaboratoryEvaluation(id);
    }

    @PostMapping("/resultlaboratoryevaluation/listadoMAAUA")
    public List<ResultLaboratoryEvaluation> listByManagementAreaAcademicunitAuxiliary(@RequestBody ListByManagementAreaAcademicunitAuxiliaryInput listInput) {
        return resultLaboratoryEvaluationService.listByManagementAreaAcademicunitAuxiliary(
                listInput.getIdmanagement(),
                listInput.getIdarea(),
                listInput.getIdacademicunit(),
                listInput.getIdauxiliary()
        );
    }

    @RequestMapping("/resultlaboratoryevaluation/laboratoryevaluation")
    public boolean existsByIdLaboratoryEvaluation(@RequestBody IdLaboratoryevaluationPostulantInput idLaboratoryevaluationPostulantInput) {
        return resultLaboratoryEvaluationService.existsByIdLaboratoryEvaluation(idLaboratoryevaluationPostulantInput.getIdlaboratoryevaluation(), idLaboratoryevaluationPostulantInput.getIdpostulant());
    }

}
