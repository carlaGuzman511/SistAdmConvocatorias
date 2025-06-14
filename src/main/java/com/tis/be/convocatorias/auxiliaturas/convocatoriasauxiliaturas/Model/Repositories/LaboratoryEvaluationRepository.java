package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LaboratoryEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LaboratoryEvaluationRepository extends JpaRepository<LaboratoryEvaluation, Long>{

    @Query("select item from LaboratoryEvaluation item where item.idlaboratoryevaluation = :id")
    Optional<LaboratoryEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from LaboratoryEvaluation item where item.idlaboratoryevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idlaboratoryevaluation) from laboratory_evaluation", nativeQuery = true)
    long selectByIdlaboratoryevaluation();

    List<LaboratoryEvaluation> findLaboratoryEvaluationsByAuxiliary_Idauxiliary(long idaux);

    List<LaboratoryEvaluation> findLaboratoryEvaluationsByThematic_IdthematicOrderByThematicDesc(long idthem);

    List<LaboratoryEvaluation> findLaboratoryEvaluationsByKnowledge_Idknowledge(long idknow);

    @Query("select item from LaboratoryEvaluation item where item.idlaboratoryevaluation =:id ")
    LaboratoryEvaluation getById(@Param("id") long id);

    boolean existsLaboratoryEvaluationByAuxiliary_IdauxiliaryAndKnowledge_IdknowledgeAndThematic_IdthematicAndPercentage(long idAuxiliary, long idKnowledge, long idThematic, int percentage);

}
