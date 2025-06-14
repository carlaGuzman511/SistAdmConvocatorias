package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers.ResultFinalTeachingController;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultOralTeachingEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultOralTeachingEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultOralTeachingEvaluationService {

    @Autowired
    private ResultOralTeachingEvaluationRepository resultOralTeachingEvaluationRepository;
    @Autowired
    private ResultFinalTeachingController resultFinalTeachingController;



    public List<ResultOralTeachingEvaluation> getAllResultOralTeachingEvaluation() {
        return resultOralTeachingEvaluationRepository.findAll();
    }


    public ResultOralTeachingEvaluation getResultOralTeachingEvaluation(long id) {
        return resultOralTeachingEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultOralTeachingEvaluation(ResultOralTeachingEvaluation resultOralTeachingEvaluation) {
        resultOralTeachingEvaluationRepository.save(resultOralTeachingEvaluation);
        return resultOralTeachingEvaluationRepository.selectByIdresultoralteachingevaluation();
    }

    public void addNewScore(long idpostulant, long idknowledgeevaluation, long idauxiliary, double scoreOral, int percentage, int percentageFinal, long idResultOral){
        if(resultOralTeachingEvaluationRepository.existsResultOralTeachingEvaluationByPostulantes_IdpostulantAndKnowledgeEvaluation_IdknowledgeevaluationAndAuxiliary_Idauxiliary(idpostulant, idknowledgeevaluation, idauxiliary)){
            resultFinalTeachingController.updateResultOralTeaching(idpostulant, idknowledgeevaluation, idauxiliary,scoreOral, percentage, percentageFinal);
            ResultOralTeachingEvaluation resultOralTeachingEvaluation = resultOralTeachingEvaluationRepository.getOne(idResultOral);
            resultOralTeachingEvaluation.setScore(Math.round(scoreOral * 100) / 100d);
            resultOralTeachingEvaluationRepository.save(resultOralTeachingEvaluation);
        }else {
            resultFinalTeachingController.addResultOralTeaching(idpostulant, idknowledgeevaluation, idauxiliary,scoreOral, percentage, percentageFinal);
        }
    }


    public void deleteResultOralTeachingEvaluation(long id) {
        resultOralTeachingEvaluationRepository.DeleteByCi(id);
    }

    public List<ResultOralTeachingEvaluation> listByIdManagementAreaAcademicUnitAuxiliary(long idman, long idarea, long idaca, long idaux){
        return resultOralTeachingEvaluationRepository.listByIdManagementAreaAcademicUnitAuxiliary(idman, idarea, idaca, idaux);
    }

    public List<ResultOralTeachingEvaluation> listByIdPostulant(long id){
        return resultOralTeachingEvaluationRepository.findResultOralTeachingEvaluationsByPostulantes_Idpostulant(id);
    }

    public boolean existsByIdAuxiliaryKnowledgeEvaluation(long idauxiliary, long idknowledgeevaluation){
        return resultOralTeachingEvaluationRepository.existsResultOralTeachingEvaluationByAuxiliary_IdauxiliaryAndKnowledgeEvaluation_Idknowledgeevaluation(idauxiliary, idknowledgeevaluation);
    }

}
