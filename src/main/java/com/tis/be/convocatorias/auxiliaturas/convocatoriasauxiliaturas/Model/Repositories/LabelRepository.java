package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long>{

    @Query("select item from Label item where item.idlabel = :id")
    Optional<Label> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Label item where item.idlabel = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select label0_.idlabel as idlabel, label0_.idauxiliary as idauxiliary, label0_.idpostulant as idpostulant, label0_.idannouncement as idannouncement from label label0_ left outer join postulant postulante1_ on label0_.idpostulant=postulante1_.idpostulant left outer join person person2_ on postulante1_.idperson=person2_.idperson left outer join auxiliary auxiliary2_ on label0_.idauxiliary=auxiliary2_.idauxiliary left outer join announcement announcement2_ on label0_.idannouncement=announcement2_.idannouncement  left outer join academic_unit academic_unit2_ on announcement2_.idacademicunit=academic_unit2_.idacademicunit left outer join management management2_ on announcement2_.idmanagement=management2_.idmanagement where (person2_.name like  CONCAT('%',:textAlfa,'%') or person2_.surname like CONCAT('%',:textBeta,'%')) and academic_unit2_.idacademicunit=:idAcad and management2_.idmanagement=:idMan and announcement2_.idannouncement =:idAnnoun", nativeQuery = true)
    List<Label> searcherByTextAcadManAnnoun(@Param("textAlfa") String textAlfa, @Param("textBeta") String textBeta, @Param("idAcad") String idAcad, @Param("idMan") String idMan, @Param("idAnnoun") String idAnnoun);

    @Query(value = "select label0_.idlabel as idlabel, label0_.idauxiliary as idauxiliary, label0_.idpostulant as idpostulant, label0_.idannouncement as idannouncement from label label0_ left outer join postulant postulante1_ on label0_.idpostulant = postulante1_.idpostulant left outer join person person2_ on postulante1_.idperson = person2_.idperson left outer join auxiliary auxiliary2_ on label0_.idauxiliary = auxiliary2_.idauxiliary left outer join announcement announcement2_ on label0_.idannouncement = announcement2_.idannouncement where ( person2_.name like CONCAT('%', :textAlfa, '%') or person2_.surname like CONCAT('%', :textBeta, '%') ) and announcement2_.idannouncement = :idAnnoun", nativeQuery = true)
    List<Label> searcherByTextAnnoun(@Param("textAlfa") String textAlfa, @Param("textBeta") String textBeta, @Param("idAnnoun") String idAnnoun);

    @Query(value = "select label0_.idlabel as idlabel, label0_.idauxiliary as idauxiliary, label0_.idpostulant as idpostulant, label0_.idannouncement as idannouncement from label label0_ left outer join postulant postulante1_ on label0_.idpostulant = postulante1_.idpostulant left outer join person person2_ on postulante1_.idperson = person2_.idperson left outer join announcement announcement2_ on label0_.idannouncement = announcement2_.idannouncement where ( person2_.name like CONCAT('%', :textAlfa, '%') or person2_.surname like CONCAT('%', :textBeta, '%') ) and announcement2_.idannouncement = :idAnnoun and postulante1_.status = TRUE", nativeQuery = true)
    List<Label> searcherByTextAnnounAndStatusTrue(@Param("textAlfa") String textAlfa, @Param("textBeta") String textBeta, @Param("idAnnoun") String idAnnoun);

    @Query(value = "select MAX(management2_.idmanagement) from label label0_ left outer join announcement announcement2_ on label0_.idannouncement=announcement2_.idannouncement left outer join management management2_ on announcement2_.idmanagement=management2_.idmanagement ", nativeQuery = true)
    String selectByIdmanagement();

    @Query(value = "select MAX(idlabel) from label", nativeQuery = true)
    long selectByIdlabel();

    @Query("select item from Label item where item.idlabel =:id ")
    Label getById(@Param("id") long id);

    List<Label> findLabelByPostulantes_Person_Id(long idperson);

    boolean existsLabelByAnnouncement_IdannouncementAndAuxiliary_IdauxiliaryAndPostulantes_Idpostulant(long idAnnouncement, long idAuxiliary, long idPostulant);

}
