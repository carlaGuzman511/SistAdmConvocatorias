package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultOralTeachingEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultOralTeachingEvaluationRepository extends JpaRepository<ResultOralTeachingEvaluation, Long>{

    @Query("select item from ResultOralTeachingEvaluation item where item.idresultoralteachingevaluation = :id")
    Optional<ResultOralTeachingEvaluation> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultOralTeachingEvaluation item where item.idresultoralteachingevaluation = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idresultoralteachingevaluation) from result_oral_teaching_evaluation", nativeQuery = true)
    long selectByIdresultoralteachingevaluation();

    @Query("select item from ResultOralTeachingEvaluation item join KnowledgeEvaluation knoweva on item.knowledgeEvaluation.idknowledgeevaluation = knoweva.idknowledgeevaluation " +
            "join Knowledge know on knoweva.knowledge.idknowledge = know.idknowledge " +
            "join Announcement ann on know.announcement.idannouncement = ann.idannouncement " +
            "join ann.auxiliarys aux on aux.idauxiliary = :idaux and ann.management.idmanagement = :idman " +
            "and ann.area.idarea = :idarea and ann.academicUnit.idacademicunit = :idaca")
    List<ResultOralTeachingEvaluation> listByIdManagementAreaAcademicUnitAuxiliary(
            @Param("idman") long idman,
            @Param("idarea") long idarea,
            @Param("idaca") long idaca,
            @Param("idaux") long idaux
    );

    List<ResultOralTeachingEvaluation> findResultOralTeachingEvaluationsByPostulantes_Idpostulant(long id);

    @Query("select item from ResultOralTeachingEvaluation item where item.idresultoralteachingevaluation =:id ")
    ResultOralTeachingEvaluation getById(@Param("id") long id);

    @Query("select item.idresultoralteachingevaluation from ResultOralTeachingEvaluation item where item.knowledgeEvaluation.idknowledgeevaluation = :idknowledgeevaluation " +
            "and item.postulantes.idpostulant = :idpostulant and item.auxiliary.idauxiliary = :idauxiliary")
    long OralByPostulant(@Param("idpostulant") long idpostulant,
                         @Param("idknowledgeevaluation") long idknowledgeevaluation,
                         @Param("idauxiliary") long idauxiliary
    );

    boolean existsResultOralTeachingEvaluationByPostulantes_IdpostulantAndKnowledgeEvaluation_IdknowledgeevaluationAndAuxiliary_Idauxiliary(long idpostulant, long idknowledgeevaluation, long idauxiliary);

    @Query("select item.idresultoralteachingevaluation from ResultOralTeachingEvaluation item where item.postulantes.idpostulant = :idpostulant " +
            "and item.knowledgeEvaluation.idknowledgeevaluation = :idknowledgeevaluation and item.auxiliary.idauxiliary = :idauxiliary")
    long getIdByPostulantKnowledgeEvaluationAuxiliary(
            @Param("idpostulant") long idpostulant,
            @Param("idknowledgeevaluation") long idknowledgeevaluation,
            @Param("idauxiliary") long idauxiliary
    );



    boolean existsResultOralTeachingEvaluationByAuxiliary_IdauxiliaryAndKnowledgeEvaluation_Idknowledgeevaluation(long idauxiliary, long idknowledgeevaluation);

}
