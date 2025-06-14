package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultSubdetailMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultSubDetailMeritEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultSubdetailMeritEvaluationService {

    @Autowired
    private ResultSubDetailMeritEvaluationRepository resultSubDetailMeritEvaluationRepository;

    public List<ResultSubdetailMeritEvaluation> getAllResultSubdetailMeritEvaluation() {

        return resultSubDetailMeritEvaluationRepository.findAll();
    }

    public ResultSubdetailMeritEvaluation getResultSubdetailMeritEvaluation(long id) {
        return resultSubDetailMeritEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultSubdetailMeritEvaluation(ResultSubdetailMeritEvaluation resultSubdetailMeritEvaluation) {
        resultSubDetailMeritEvaluationRepository.save(resultSubdetailMeritEvaluation );
        return resultSubDetailMeritEvaluationRepository.selectByIdResultSubdetailMeritEvaluation();
    }

    public void deleteResultSubdetailMeritEvaluation(long id) {
        resultSubDetailMeritEvaluationRepository.DeleteByCi(id);
    }


    public long getByLastId(){
        return resultSubDetailMeritEvaluationRepository.selectByIdResultSubdetailMeritEvaluation();
    }

}
