package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultFinalLaboratory;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.ResultFinalTeaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResultFinalLaboratoryRepository extends JpaRepository<ResultFinalLaboratory, Long>{

    @Query("select item from ResultFinalLaboratory item where item.idresultfinallaboratory = :id")
    Optional<ResultFinalLaboratory> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from ResultFinalLaboratory item where item.idresultfinallaboratory = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from ResultFinalLaboratory item where item.idresultfinallaboratory =:id ")
    ResultFinalLaboratory getById(@Param("id") long id);

    @Query("select MAX(item.idresultfinallaboratory) from ResultFinalLaboratory item")
    long selectByIdresultfinallaboratory();

    List<ResultFinalLaboratory> findByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit,
            long idauxiliary
    );

    @Modifying
    @Transactional
    @Query("update ResultFinalLaboratory item set item.status = :status where item.idresultfinallaboratory = :idresults")
    void updateResultFinalLaboratoryStatus(
            @Param("idresults") long idresults,
            @Param("status") boolean status
    );

    List<ResultFinalLaboratory> findResultFinalLaboratoriesByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit
    );

    List<ResultFinalLaboratory> findResultFinalLaboratoryByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndStatusOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit,
            boolean status
    );

    List<ResultFinalLaboratory> findResultFinalLaboratoryByAnnouncement_Management_IdmanagementAndAnnouncement_Area_IdareaAndAnnouncement_AcademicUnit_IdacademicunitAndAuxiliary_IdauxiliaryAndStatusOrderByScoreTotalDesc(
            long idmanagement,
            long idarea,
            long idacademicunit,
            long idauxiliary,
            boolean status
    );

    @Query("select item.idresultfinallaboratory from ResultFinalLaboratory item where item.postulantes.idpostulant = :idpostulant and item.announcement.idannouncement = :idannouncement " +
            "and item.auxiliary.idauxiliary = :idauxiliary")
    long getLabolatoryByPostulant(@Param("idpostulant") long idpostulant,
                                  @Param("idannouncement") long idannouncement,
                                  @Param("idauxiliary") long idauxiliary
    );

    boolean existsResultFinalLaboratoryByPostulantes_IdpostulantAndAnnouncement_IdannouncementAndAuxiliary_Idauxiliary(long idpostulant, long idannouncement, long idauxiliary);

}
