package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers.ResultFinalTeachingController;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultWrittenTeachingEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultWrittenTeachingEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultWrittenTeachingEvaluationService {

    @Autowired
    private ResultWrittenTeachingEvaluationRepository resultWrittenTeachingEvaluationRepository;
    @Autowired
    private ResultFinalTeachingController resultFinalTeachingController;


    public List<ResultWrittenTeachingEvaluation> getAllResultWrittenTeachingEvaluation() {
        return resultWrittenTeachingEvaluationRepository.findAll();
    }


    public ResultWrittenTeachingEvaluation getResultWrittenTeachingEvaluation(long id) {
        return resultWrittenTeachingEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultWrittenTeachingEvaluation(ResultWrittenTeachingEvaluation resultTeachingEvaluation) {
        resultWrittenTeachingEvaluationRepository.save(resultTeachingEvaluation);
        return resultWrittenTeachingEvaluationRepository.selectByIdresultwrittenteachingevaluation();
    }


    public void deleteResultWrittenTeachingEvaluation(long id) {
        resultWrittenTeachingEvaluationRepository.DeleteByCi(id);
    }

    public List<ResultWrittenTeachingEvaluation> ListByIdManagementAreaAcademicUnitAuxiliary(long idman, long idarea, long idaca, long idaux){
        return resultWrittenTeachingEvaluationRepository.listByIdManagementAreaAcademicUnitAuxiliary(idman, idarea, idaca, idaux);
    }

    public List<ResultWrittenTeachingEvaluation> listByIdPostulant(long id){
        return resultWrittenTeachingEvaluationRepository.findResultWrittenTeachingEvaluationsByPostulantes_Idpostulant(id);
    }

    public void addNewScore(long idpostulant,  long idknowledgeevaluation, long idauxiliary, double scoreWritten, int percentage, int percentageFinal, long idResultWritten) {
        if(resultWrittenTeachingEvaluationRepository.existsResultWrittenTeachingEvaluationByPostulantes_IdpostulantAndKnowledgeEvaluation_IdknowledgeevaluationAndAuxiliary_Idauxiliary(idpostulant, idknowledgeevaluation, idauxiliary)){
            resultFinalTeachingController.updateResultWrittenTeaching(idpostulant, idknowledgeevaluation, idauxiliary, scoreWritten, percentage,  percentageFinal);
            ResultWrittenTeachingEvaluation resultWrittenTeachingEvaluation = resultWrittenTeachingEvaluationRepository.getOne(idResultWritten);
            resultWrittenTeachingEvaluation.setScore(Math.round(scoreWritten *100) /100d);
            resultWrittenTeachingEvaluationRepository.save(resultWrittenTeachingEvaluation);
        }else {
            resultFinalTeachingController.addResultWrittenTeaching(idpostulant, idknowledgeevaluation, idauxiliary,scoreWritten, percentage, percentageFinal)  ;
        }
    }

    public boolean existsByIdAuxiliaryKnowledgeEvaluation(long idauxiliary, long idknowledgeevaluation){
        return resultWrittenTeachingEvaluationRepository.existsResultWrittenTeachingEvaluationByAuxiliary_IdauxiliaryAndKnowledgeEvaluation_Idknowledgeevaluation(idauxiliary, idknowledgeevaluation);
    }

}
