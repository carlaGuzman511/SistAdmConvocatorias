package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultDetailMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultDetailMeritEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultDetailMeritEvaluationService {

    @Autowired
    private ResultDetailMeritEvaluationRepository resultDetailMeritEvaluationRepository;

    public List<ResultDetailMeritEvaluation> getAllResultDetailMeritEvaluation() {

        return resultDetailMeritEvaluationRepository.findAll();
    }

    public ResultDetailMeritEvaluation getResultDetailMeritEvaluation(long id) {
        return resultDetailMeritEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultDetailMeritEvaluation(ResultDetailMeritEvaluation resultDetailMeritEvaluation) {
        resultDetailMeritEvaluationRepository.save(resultDetailMeritEvaluation);
        return resultDetailMeritEvaluationRepository.selectByIdResultDetailMeritEvaluation();
    }

    public void deleteResultDetailMeritEvaluation(long id) {
        resultDetailMeritEvaluationRepository.DeleteByCi(id);
    }


    public long getByLastId(){
        return resultDetailMeritEvaluationRepository.selectByIdResultDetailMeritEvaluation();
    }

}
