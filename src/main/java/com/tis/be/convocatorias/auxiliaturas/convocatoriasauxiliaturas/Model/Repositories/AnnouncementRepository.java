package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{

    @Query("select item from Announcement item where item.idannouncement = :id")
    Optional<Announcement> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Announcement item where item.idannouncement = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(item.idannouncement) from Announcement item ")
    long selectByIdAnnouncement();

    @Query("select item from Announcement item where item.idannouncement =:id ")
    Announcement getById(@Param("id") long id);

    @Query("select item.auxiliarys from Announcement item where item.idannouncement =:id")
    List<Auxiliary> listAuxiliarysByIdAnnouncement(@Param("id") long id);

    @Query("select aux from Announcement item join item.auxiliarys aux on item.academicUnit.idacademicunit =:idacademicunit and item.area.idarea =:idarea and item.management.idmanagement =:idmanagement group by aux.idauxiliary ")
    List<Auxiliary> listAuxiliarysByIdManagementAreaAcademicUnit(@Param("idmanagement") long idmanagement, @Param("idarea") long idarea, @Param("idacademicunit") long idacademicunit );

    @Query("select item from Announcement item where item.management.idmanagement = :idmanagement and item.area.idarea = :idarea and item.academicUnit.idacademicunit = :idacademicunit and item.faculty.idfaculty = :idfaculty")
    List<Announcement> listByManagementAreaAcademicUnitFaculty(@Param("idmanagement") long idmanagement, @Param("idarea") long idarea, @Param("idacademicunit") long idacademicunit, @Param("idfaculty") long idfaculty );

    @Query("select item from Announcement item where item.management.idmanagement = :idmanagement and item.academicUnit.idacademicunit = :idacademicunit")
    List<Announcement> listByManagementAcademicUnit(@Param("idmanagement") long idmanagement, @Param("idacademicunit") long idacademicunit);

    @Query("select item.auxiliarys from Announcement item where item.area.idarea =:idarea and item.academicUnit.idacademicunit =:idacademicunit")
    List<Auxiliary> listAuxiliarysByIdAreaAcademicUnit(@Param("idarea") long idarea, @Param("idacademicunit") long idacademicunit);

    @Query("select item.area.idarea from Announcement item where item.idannouncement = :idAnnouncement")
    long findAreaNameByIdAnnouncement(@Param("idAnnouncement") long idAnnouncement);

    List<Announcement> findAnnouncementsByManagement_IdmanagementAndArea_IdareaAndAcademicUnit_Idacademicunit(
            long idmanagement,
            long idarea,
            long idacademicunit
            );

    boolean existsAnnouncementByAcademicUnit_IdacademicunitAndArea_IdareaAndFaculty_IdfacultyAndManagement_PeriodAndTitle(long idacademicunit, long idarea, long idfaculty, String period, String title);

}
