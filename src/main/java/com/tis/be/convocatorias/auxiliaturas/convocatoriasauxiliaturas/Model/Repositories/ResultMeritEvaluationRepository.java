package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultMeritEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultMeritEvaluationRepository extends JpaRepository<ResultMeritEvaluation, Long> {

    @Query("select item from ResultMeritEvaluation item where item.idresultmeritevaluation = :id")
    Optional<ResultMeritEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultMeritEvaluation item where item.idresultmeritevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from ResultMeritEvaluation item where item.idresultmeritevaluation =:id ")
    ResultMeritEvaluation getById(@Param("id") long id);

    @Query(value = "select MAX(item.idresultmeritevaluation) from ResultMeritEvaluation item")
    long selectByIdResultMeritEvaluation();

    @Query("select item.idresultmeritevaluation from ResultMeritEvaluation item where item.label.idlabel = :idlabel")
    long getIdByLabel(@Param("idlabel") long idlabel);



    boolean existsResultMeritEvaluationByLabel_Idlabel(long idlabel);

}
