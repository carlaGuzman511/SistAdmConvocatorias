package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>{

    @Query("select item from Knowledge item where item.idknowledge = :id")
    Optional<Knowledge> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Knowledge item where item.idknowledge = :id")
    void DeleteByCi(@Param("id") long id);


    @Query(value = "select MAX(idknowledge) from knowledge", nativeQuery = true)
    long selectByIdknowledge();

    List<Knowledge> findKnowledgeByAnnouncement_Idannouncement(long idann);

    @Query("select item from Knowledge item where item.idknowledge =:id ")
    Knowledge getById(@Param("id") long id);

    boolean existsKnowledgeByAnnouncement_IdannouncementAndDescriptionAndBaseScoreAndFinalScore(long idAnnouncement, String description, int baseScore, int finalScore);

}
