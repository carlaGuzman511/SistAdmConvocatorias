package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultLaboratoryEvaluation;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ResultLaboratoryEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultLaboratoryEvaluationService {

    @Autowired
    private ResultLaboratoryEvaluationRepository resultLaboratoryEvaluationRepository;


    public List<ResultLaboratoryEvaluation> getAllResultLaboratoryEvaluation() {
        return resultLaboratoryEvaluationRepository.findAll();
    }


    public ResultLaboratoryEvaluation getResultLaboratoryEvaluation(long id) {
        return resultLaboratoryEvaluationRepository.FindById(id)
                .orElse(null);

    }

    public List<ResultLaboratoryEvaluation> getResultLaboratoryEvaluationByIdpostulant(long id) {
        return resultLaboratoryEvaluationRepository.findResultLaboratoryEvaluationsByPostulantes_Idpostulant(id);
    }

    public long addResultLaboratoryEvaluation(ResultLaboratoryEvaluation resultLaboratoryEvaluation) {
        resultLaboratoryEvaluationRepository.save(resultLaboratoryEvaluation);
        return resultLaboratoryEvaluationRepository.selectByIdresultlaboratoryevaluation();
    }


    public void deleteResultLaboratoryEvaluation(long id) {
        resultLaboratoryEvaluationRepository.DeleteByCi(id);
    }


    public List<ResultLaboratoryEvaluation> listByManagementAreaAcademicunitAuxiliary(long idmanagement, long idarea, long idacademicunit, long idauxiliary){
        return resultLaboratoryEvaluationRepository.findResultLaboratoryEvaluationsByLaboratoryEvaluation_Knowledge_Announcement_Management_IdmanagementAndAndLaboratoryEvaluation_Knowledge_Announcement_Area_IdareaAndAndLaboratoryEvaluation_Knowledge_Announcement_AcademicUnit_IdacademicunitAndAndLaboratoryEvaluation_Auxiliary_Idauxiliary(
                idmanagement,
                idarea,
                idacademicunit,
                idauxiliary
        );
    }

    public boolean existsByIdLaboratoryEvaluation(long idLaboratory, long idpostulant){
        return resultLaboratoryEvaluationRepository.existsResultLaboratoryEvaluationByLaboratoryEvaluation_IdlaboratoryevaluationAndPostulantes_Idpostulant(idLaboratory, idpostulant);
    }

}
