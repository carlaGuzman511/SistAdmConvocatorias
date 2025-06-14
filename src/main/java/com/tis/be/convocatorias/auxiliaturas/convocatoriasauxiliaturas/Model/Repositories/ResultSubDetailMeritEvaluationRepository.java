package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultSubdetailMeritEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultSubDetailMeritEvaluationRepository extends JpaRepository<ResultSubdetailMeritEvaluation, Long> {

    @Query("select item from ResultSubdetailMeritEvaluation item where item.idresultsubdetailmeritevaluation = :id")
    Optional<ResultSubdetailMeritEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultSubdetailMeritEvaluation item where item.idresultsubdetailmeritevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from ResultSubdetailMeritEvaluation item where item.idresultsubdetailmeritevaluation =:id ")
    ResultSubdetailMeritEvaluation getById(@Param("id") long id);

    @Query(value = "select MAX(item.idresultsubdetailmeritevaluation) from ResultSubdetailMeritEvaluation item")
    long selectByIdResultSubdetailMeritEvaluation();


}
