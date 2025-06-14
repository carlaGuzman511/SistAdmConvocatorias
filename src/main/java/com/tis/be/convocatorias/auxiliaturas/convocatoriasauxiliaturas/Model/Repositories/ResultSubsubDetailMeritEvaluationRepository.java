package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultSubsubdetailMeritEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultSubsubDetailMeritEvaluationRepository extends JpaRepository<ResultSubsubdetailMeritEvaluation, Long> {

    @Query("select item from ResultSubsubdetailMeritEvaluation item where item.idresultsubsubdetailmeritevaluation = :id")
    Optional<ResultSubsubdetailMeritEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultSubsubdetailMeritEvaluation item where item.idresultsubsubdetailmeritevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from ResultSubsubdetailMeritEvaluation item where item.idresultsubsubdetailmeritevaluation =:id ")
    ResultSubsubdetailMeritEvaluation getById(@Param("id") long id);

    @Query(value = "select MAX(item.idresultsubsubdetailmeritevaluation) from ResultSubsubdetailMeritEvaluation item")
    long selectByIdResultSubsubdetailMeritEvaluation();


}
