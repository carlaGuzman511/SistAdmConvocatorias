package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.KnowledgeEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeEvaluationService {

    @Autowired
    private KnowledgeEvaluationRepository knowledgeEvaluationRepository;


    public List<KnowledgeEvaluation> getAllKnowledgeEvaluation() {
        return knowledgeEvaluationRepository.findAll();
    }


    public KnowledgeEvaluation getKnowledgeEvaluation(long id) {
        return knowledgeEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addKnowledgeEvaluation(KnowledgeEvaluation knowledgeEvaluation) {
        knowledgeEvaluationRepository.save(knowledgeEvaluation);
        return knowledgeEvaluationRepository.selectByIdknowledgeevaluation();
    }

    public void deleteKnowledgeEvaluation(long id) {
        knowledgeEvaluationRepository.DeleteByCi(id);
    }

    public List<KnowledgeEvaluation> ListByKnowledge(long id){
        return knowledgeEvaluationRepository.findKnowledgeEvaluationsByKnowledge_Idknowledge(id);
    }

}
