package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultTeachingEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultTeachingEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultTeachingEvaluationService {

    @Autowired
    private ResultTeachingEvaluationRepository resultTeachingEvaluationRepository;


    public List<ResultTeachingEvaluation> getAllResultTeachingEvaluation() {
        return resultTeachingEvaluationRepository.findAll();
    }


    public ResultTeachingEvaluation getResultTeachingEvaluation(long id) {
        return resultTeachingEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultTeachingEvaluation(ResultTeachingEvaluation resultTeachingEvaluation) {
        resultTeachingEvaluationRepository.save(resultTeachingEvaluation);
        return resultTeachingEvaluationRepository.selectByIdresultteachingevaluation();
    }


    public void deleteResultTeachingEvaluation(long id) {
        resultTeachingEvaluationRepository.DeleteByCi(id);
    }

}
