package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultSubsubdetailMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultSubsubDetailMeritEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultSubsubdetailMeritEvaluationService {

    @Autowired
    private ResultSubsubDetailMeritEvaluationRepository resultSubsubDetailMeritEvaluationRepository;

    public List<ResultSubsubdetailMeritEvaluation> getAllResultSubsubdetailMeritEvaluation() {

        return resultSubsubDetailMeritEvaluationRepository.findAll();
    }

    public ResultSubsubdetailMeritEvaluation getResultSubsubdetailMeritEvaluation(long id) {
        return resultSubsubDetailMeritEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultSubsubdetailMeritEvaluation(ResultSubsubdetailMeritEvaluation resultSubsubdetailMeritEvaluation) {
        resultSubsubDetailMeritEvaluationRepository.save(resultSubsubdetailMeritEvaluation);
        return resultSubsubDetailMeritEvaluationRepository.selectByIdResultSubsubdetailMeritEvaluation();
    }

    public void deleteResultSubsubdetailMeritEvaluation(long id) {
        resultSubsubDetailMeritEvaluationRepository.DeleteByCi(id);
    }


    public long getByLastId(){
        return resultSubsubDetailMeritEvaluationRepository.selectByIdResultSubsubdetailMeritEvaluation();
    }

}
