package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultMeritEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultMeritEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultMeritEvaluationService {

    @Autowired
    private ResultMeritEvaluationRepository resultMeritEvaluationRepository;

    public List<ResultMeritEvaluation> getAllResultMeritEvaluation() {

        return resultMeritEvaluationRepository.findAll();
    }

    public ResultMeritEvaluation getResultMeritEvaluation(long id) {
        return resultMeritEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addResultMeritEvaluation(ResultMeritEvaluation resultMeritEvaluation) {
        resultMeritEvaluationRepository.save(resultMeritEvaluation);
        return resultMeritEvaluationRepository.selectByIdResultMeritEvaluation();
    }

    public void deleteResultMeritEvaluation(long id) {
        resultMeritEvaluationRepository.DeleteByCi(id);
    }


    public long getByLastId(){
        return resultMeritEvaluationRepository.selectByIdResultMeritEvaluation();
    }

    public boolean existByIdLabel(long idlabel){
        return resultMeritEvaluationRepository.existsResultMeritEvaluationByLabel_Idlabel(idlabel);
    }

}
