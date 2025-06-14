package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultFinalTeaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultFinalTeachingRepository extends JpaRepository<ResultFinalTeaching, Long>{

    @Query("select item from ResultFinalTeaching item where item.idresultfinalteaching = :id")
    Optional<ResultFinalTeaching> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultFinalTeaching item where item.idresultfinalteaching = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select MAX(item.idresultfinalteaching) from ResultFinalTeaching item")
    long selectByIdresultfinalteaching();

    @Query("select item from ResultFinalTeaching item where item.idresultfinalteaching =:id ")
    ResultFinalTeaching getById(@Param("id") long id);

    List<ResultFinalTeaching> findByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit,
            long idauxiliary
    );

    @Modifying
    @Transactional
    @Query("update ResultFinalTeaching item set item.status = :status where item.idresultfinalteaching = :idresults")
    void updateResultsStatus(
            @Param("idresults") long idresults,
            @Param("status") boolean status
    );

    List<ResultFinalTeaching> findResultFinalTeachingByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit
    );

    List<ResultFinalTeaching> findResultFinalTeachingByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndStatusOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit,
            boolean status
    );

    List<ResultFinalTeaching> findResultFinalTeachingByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryAndStatusOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit,
            long idauxiliary,
            boolean status
    );


    @Query("select item.idresultfinalteaching from ResultFinalTeaching item where item.postulantes.idpostulant = :idpostulant and item.announcement.idannouncement = :idannouncement " +
            "and item.auxiliary.idauxiliary = :idauxiliary")
    long getByIdPostulant(@Param("idpostulant") long idpostulant,
                          @Param("idannouncement") long idannouncement,
                          @Param("idauxiliary") long idauxiliary
                          );

    @Query("select item.announcement.idannouncement from Knowledge item join KnowledgeEvaluation knoweva on knoweva.knowledge.idknowledge = item.idknowledge " +
            "and knoweva.idknowledgeevaluation = :idknowledgeevaluation")
    long idAnnouncement(@Param("idknowledgeevaluation") long idknowledgeevaluation);


    boolean existsResultFinalTeachingByPostulantes_IdpostulantAndAnnouncement_IdannouncementAndAuxiliary_Idauxiliary(
            long idpostulant,
            long idannouncement,
            long idauxiliary
    );
}
