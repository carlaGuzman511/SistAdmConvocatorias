package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultDetailMeritEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultDetailMeritEvaluationRepository extends JpaRepository<ResultDetailMeritEvaluation, Long> {

    @Query("select item from ResultDetailMeritEvaluation item where item.idresultdetailmeritevaluation = :id")
    Optional<ResultDetailMeritEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultDetailMeritEvaluation item where item.idresultdetailmeritevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from ResultDetailMeritEvaluation item where item.idresultdetailmeritevaluation =:id ")
    ResultDetailMeritEvaluation getById(@Param("id") long id);

    @Query(value = "select MAX(item.idresultdetailmeritevaluation) from ResultDetailMeritEvaluation item")
    long selectByIdResultDetailMeritEvaluation();


}
