package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Merit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MeritRepository extends JpaRepository<Merit, Long>{

    @Query("select item from Merit item where item.idmerit = :id")
    Optional<Merit> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Merit item where item.idmerit = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idmerit) from merit", nativeQuery = true)
    long selectByIdmerit();

    @Query("select item from Merit item where item.idmerit =:id ")
    Merit getById(@Param("id") long id);

    List<Merit> findMeritsByAnnouncement_Idannouncement(long id);

    @Query("select item from Merit item where item.announcement.idannouncement = :id order by item.idmerit asc")
    List<Merit> listByUp(@Param("id") long id);

    boolean existsMeritByAnnouncement_IdannouncementAndDescriptionAndBaseScoreAndFinalScore(long idAnnouncement, String decription, int baseScore, int finalScore);

}
