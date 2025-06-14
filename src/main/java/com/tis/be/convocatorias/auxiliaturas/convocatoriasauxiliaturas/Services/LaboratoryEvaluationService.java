package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LaboratoryEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaboratoryEvaluationService {

    @Autowired
    private LaboratoryEvaluationRepository laboratoryEvaluationRepository;


    public List<LaboratoryEvaluation> getAllLaboratoryEvaluation() {
        return laboratoryEvaluationRepository.findAll();
    }


    public LaboratoryEvaluation getLaboratoryEvaluation(long id) {
        return laboratoryEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public long addLaboratoryEvaluation(LaboratoryEvaluation laboratoryEvaluation) {
        laboratoryEvaluationRepository.save(laboratoryEvaluation);
        return laboratoryEvaluationRepository.selectByIdlaboratoryevaluation();
    }


    public void deleteLaboratoryEvaluation(long id) {
        laboratoryEvaluationRepository.DeleteByCi(id);
    }


    public List<LaboratoryEvaluation> ListByAuxiliary(long id){
        return laboratoryEvaluationRepository.findLaboratoryEvaluationsByAuxiliary_Idauxiliary(id);
    }

    public List<LaboratoryEvaluation> ListByThematic(long id){
        return laboratoryEvaluationRepository.findLaboratoryEvaluationsByThematic_IdthematicOrderByThematicDesc(id);
    }


    public List<LaboratoryEvaluation> ListByKnowledge(long id){
        return laboratoryEvaluationRepository.findLaboratoryEvaluationsByKnowledge_Idknowledge(id);
    }

}
