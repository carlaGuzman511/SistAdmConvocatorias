package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultWrittenTeachingEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultWrittenTeachingEvaluationRepository extends JpaRepository<ResultWrittenTeachingEvaluation, Long>{

    @Query("select item from ResultWrittenTeachingEvaluation item where item.idresultwrittenteachingevaluation = :id")
    Optional<ResultWrittenTeachingEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultWrittenTeachingEvaluation item where item.idresultwrittenteachingevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idresultwrittenteachingevaluation) from result_written_teaching_evaluation", nativeQuery = true)
    long selectByIdresultwrittenteachingevaluation();

    @Query("select item from ResultWrittenTeachingEvaluation item where item.idresultwrittenteachingevaluation =:id ")
    ResultWrittenTeachingEvaluation getById(@Param("id") long id);

    @Query("select item from ResultWrittenTeachingEvaluation item join KnowledgeEvaluation knoweva on item.knowledgeEvaluation.idknowledgeevaluation = knoweva.idknowledgeevaluation " +
            "join Knowledge know on knoweva.knowledge.idknowledge = know.idknowledge " +
            "join Announcement ann on know.announcement.idannouncement = ann.idannouncement " +
            "join ann.auxiliarys aux on aux.idauxiliary = :idaux and ann.management.idmanagement = :idman " +
            "and ann.area.idarea = :idarea and ann.academicUnit.idacademicunit = :idaca")
    List<ResultWrittenTeachingEvaluation> listByIdManagementAreaAcademicUnitAuxiliary(
            @Param("idman") long idman,
            @Param("idarea") long idarea,
            @Param("idaca") long idaca,
            @Param("idaux") long idaux
    );

    List<ResultWrittenTeachingEvaluation> findResultWrittenTeachingEvaluationsByPostulantes_Idpostulant(long id);

    boolean existsResultWrittenTeachingEvaluationByPostulantes_IdpostulantAndKnowledgeEvaluation_IdknowledgeevaluationAndAuxiliary_Idauxiliary(
            long idpostulant, long idknowledgeevaluation, long idauxiliary);

    @Query("select item.idresultwrittenteachingevaluation from ResultWrittenTeachingEvaluation item where item.postulantes.idpostulant = :idpostulant " +
            "and item.knowledgeEvaluation.idknowledgeevaluation = :idknowledgeevaluation and item.auxiliary.idauxiliary = :idauxiliary")
    long getIdByPostulantKnowledgeEvaluationAuxiliary(
            @Param("idpostulant") long idpostulant,
            @Param("idknowledgeevaluation") long idknowledgeevaluation,
            @Param("idauxiliary") long idauxiliary
    );



    boolean existsResultWrittenTeachingEvaluationByAuxiliary_IdauxiliaryAndKnowledgeEvaluation_Idknowledgeevaluation(long idauxiliary, long idknowledgeevaluation);

}
