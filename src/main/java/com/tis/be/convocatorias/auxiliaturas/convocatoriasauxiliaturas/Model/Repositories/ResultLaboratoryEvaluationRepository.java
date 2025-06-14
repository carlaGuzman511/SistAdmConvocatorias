package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultLaboratoryEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultLaboratoryEvaluationRepository extends JpaRepository<ResultLaboratoryEvaluation, Long>{

    @Query("select item from ResultLaboratoryEvaluation item where item.idresultlaboratoryevaluation = :id")
    Optional<ResultLaboratoryEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultLaboratoryEvaluation item where item.idresultlaboratoryevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idresultlaboratoryevaluation) from result_Laboratory_evaluation", nativeQuery = true)
    long selectByIdresultlaboratoryevaluation();

    @Query("select item from ResultLaboratoryEvaluation item where item.idresultlaboratoryevaluation =:id ")
    ResultLaboratoryEvaluation getById(@Param("id") long id);

    List<ResultLaboratoryEvaluation> findResultLaboratoryEvaluationsByLaboratoryEvaluation_Knowledge_Announcement_Management_IdmanagementAndAndLaboratoryEvaluation_Knowledge_Announcement_Area_IdareaAndAndLaboratoryEvaluation_Knowledge_Announcement_AcademicUnit_IdacademicunitAndAndLaboratoryEvaluation_Auxiliary_Idauxiliary(
      long idmanagement, long idarea, long idacademicunit, long idauxiliary
    );

    List<ResultLaboratoryEvaluation> findResultLaboratoryEvaluationsByPostulantes_Idpostulant(
      long idpostulant
    );

    @Query("select item.idresultlaboratoryevaluation from ResultLaboratoryEvaluation item where item.postulantes.idpostulant = :idpostulant " +
            "and item.laboratoryEvaluation.idlaboratoryevaluation = :idlaboratoryEvaluation")
    long getIdByPostulantAndLaboratoryevaluation(
            @Param("idpostulant") long idpostulant,
            @Param("idlaboratoryEvaluation") long idlaboratoryEvaluation
    );


    boolean existsResultLaboratoryEvaluationByPostulantes_IdpostulantAndLaboratoryEvaluation_Idlaboratoryevaluation(long idpostulant, long idlaboratory);

    boolean existsResultLaboratoryEvaluationByLaboratoryEvaluation_IdlaboratoryevaluationAndPostulantes_Idpostulant(long idLaboratory, long idpostulant);
}
