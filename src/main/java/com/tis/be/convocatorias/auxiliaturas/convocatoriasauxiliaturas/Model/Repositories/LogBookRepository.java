package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Label;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.LogBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LogBookRepository extends JpaRepository<LogBook, Long> {

    @Query("select item from LogBook item where item.idlogbook = :id")
    Optional<LogBook> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from LogBook item where item.idlogbook = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select max(item.idlogbook) from LogBook item")
    long selectByIdlogbook();

    List<LogBook> findLogBookByLabel_Announcement_Idannouncement(long id_announ);

    List<LogBook> findLogBookByLabel_Announcement_IdannouncementAndLabel_Postulantes_Status(long id_announ, boolean status);

    @Query("select item from LogBook item where item.idlogbook =:id ")
    LogBook getById(@Param("id") long id);

    @Query("select item from LogBook item join Label label on item.label.idlabel = label.idlabel " +
            "join Postulant post on label.postulantes.idpostulant= post.idpostulant and post.status = true " +
            "join post.announcements ann on ann.idannouncement = :idannouncement and ann.area.idarea = :areawork " +
            "join ann.auxiliarys aux " +
            "join aux.thematics them on them.idthematic = :idwork " +
            "join ann.users user on user.iduser = :iduser join Role role on user.role.idrole = role.idrole " +
            "and (role.name = 'Comision de Conocimientos Docente' or role.name = 'Comision de Conocimientos Estudiante') ")
    List<LogBook> ListByIdUserAnnouncementT(
            @Param("idannouncement") long idannouncement,
            @Param("areawork") long areawork,
            @Param("idwork") long idwork,
            @Param("iduser") long iduser
    );

    @Query("select item from LogBook item join Label label on item.label.idlabel = label.idlabel " +
            "join Postulant post on label.postulantes.idpostulant= post.idpostulant and post.status = true " +
            "join post.announcements ann on ann.idannouncement = :idannouncement and ann.area.idarea = :areawork " +
            "join label.auxiliary aux on aux.idauxiliary = :idwork " +
            "join ann.users user on user.iduser = :iduser join Role role on user.role.idrole = role.idrole " +
            "and (role.name = 'Comision de Conocimientos Docente' or role.name = 'Comision de Conocimientos Estudiante') ")
    List<LogBook> ListByIdUserAnnouncementA(
            @Param("idannouncement") long idannouncement,
            @Param("areawork") long areawork,
            @Param("idwork") long idwork,
            @Param("iduser") long iduser
    );

    @Query("select item from LogBook item join Label label on item.label.idlabel = label.idlabel " +
            "join ResultWrittenTeachingEvaluation written on written.postulantes = label.postulantes and written.score >= 51 " +
            "and written.auxiliary.idauxiliary = :idauxiliary join KnowledgeEvaluation knoweva on written.knowledgeEvaluation.idknowledgeevaluation = knoweva.idknowledgeevaluation " +
            "join Knowledge know on knoweva.knowledge.idknowledge = know.idknowledge and know.announcement.idannouncement = :idannouncement ")
    List<LogBook> ListByIdUserAnnouncementAuxiliaryAproved(
            @Param("idannouncement") long idannouncement,
            @Param("idauxiliary") long idauxiliary
    );


    @Query("select item from LogBook item join Label label on item.label.idlabel = label.idlabel " +
            "join Postulant post on post.idpostulant = label.postulantes.idpostulant and post.status = true " +
            "join Auxiliary aux on aux.idauxiliary = label.auxiliary.idauxiliary " +
//            "join aux.announcements auxann on auxann.idannouncement = :idannouncement " +
            "join aux.thematics auxthem on auxthem.idthematic = :idthematic and item.label.announcement.idannouncement = :idannouncement")
    List<LogBook> ListPostulantesHabilitadosPorTematica(
            @Param("idannouncement") long idannouncement,
            @Param("idthematic") long idthematic
    );


    boolean existsLogBookByLabel_Idlabel(long idlabel);


//    ----------------------------------------------------- Searchers ----------------------------------------------------------

    @Query("select item from LogBook item join Label label on label.idlabel = item.label.idlabel join Postulant postulant on label.postulantes.idpostulant = postulant.idpostulant join Person person on postulant.person.id = person.id " +
            " and (person.name like concat('%', :textAlpha, '%') or person.lastName like concat('%', :textBeta, '%')) " +
            " join Announcement announcement on label.announcement.idannouncement = announcement.idannouncement and announcement.idannouncement = :idannouncement")
    List<LogBook> searcherPostulantsByTextAnnouncement(
            @Param("textAlpha") String textAlpha,
            @Param("textBeta") String textBeta,
            @Param("idannouncement") long idannouncement
    );

    @Query("select item from LogBook item join Label label on item.label.idlabel = label.idlabel join Postulant postulant on label.postulantes.idpostulant = postulant.idpostulant join Person person on postulant.person.id = person.id " +
            "and (person.name like concat('%', :textAlpha, '%') or person.lastName like concat('%',:textBeta, '%')) and postulant.status = :status and label.announcement.idannouncement = :idannouncement")
    List<LogBook> searcherPostulantsByTextAnnouncementStatus(
            @Param("textAlpha") String textAlpha,
            @Param("textBeta") String textBeta,
            @Param("idannouncement") long idannouncement,
            @Param("status") boolean status
    );
//    ----------------------------------------------------- Searchers ----------------------------------------------------------


    boolean existsLogBookByLabel_IdlabelAndDeliveryDateAndDeliveryHour(long idLabel, Date deliveryDate, Date deliveryHour);

}
