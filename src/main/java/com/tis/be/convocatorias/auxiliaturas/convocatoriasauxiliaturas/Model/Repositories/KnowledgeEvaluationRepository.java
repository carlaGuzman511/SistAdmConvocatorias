package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.KnowledgeEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface KnowledgeEvaluationRepository extends JpaRepository<KnowledgeEvaluation, Long>{

    @Query("select item from KnowledgeEvaluation item where item.idknowledgeevaluation = :id")
    Optional<KnowledgeEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from KnowledgeEvaluation item where item.idknowledgeevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idknowledgeevaluation) from knowledge_evaluation", nativeQuery = true)
    long selectByIdknowledgeevaluation();

    List<KnowledgeEvaluation> findKnowledgeEvaluationsByKnowledge_Idknowledge(long idknow);

    @Query("select item from KnowledgeEvaluation item where item.idknowledgeevaluation =:id ")
    KnowledgeEvaluation getById(@Param("id") long id);

    @Query("select item.announcement.idannouncement from Knowledge item join KnowledgeEvaluation knoweva on knoweva.knowledge.idknowledge = item.idknowledge " +
            "and knoweva.idknowledgeevaluation = :id")
    long IdAnnouncementForKnowledgeEvaluation(@Param("id") long id);

    boolean existsKnowledgeEvaluationByDescriptionAndPercentageAndKnowledge_Idknowledge(String description, int percentage, long idKnowledge);

}
