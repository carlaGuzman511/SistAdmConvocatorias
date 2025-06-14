package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultTeachingEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ResultTeachingEvaluationRepository extends JpaRepository<ResultTeachingEvaluation, Long>{

    @Query("select item from ResultTeachingEvaluation item where item.idresultteachingevaluation = :id")
    Optional<ResultTeachingEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultTeachingEvaluation item where item.idresultteachingevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idresultteachingevaluation) from result_teaching_evaluation", nativeQuery = true)
    long selectByIdresultteachingevaluation();

    @Query("select item from ResultTeachingEvaluation item where item.idresultteachingevaluation =:id ")
    ResultTeachingEvaluation getById(@Param("id") long id);

}
