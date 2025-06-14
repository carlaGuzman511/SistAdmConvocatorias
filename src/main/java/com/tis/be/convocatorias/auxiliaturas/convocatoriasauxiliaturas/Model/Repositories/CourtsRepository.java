package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Courts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CourtsRepository extends JpaRepository<Courts, Long>{

    @Query("select item from Courts item where item.idcourts = :idcourts")
    Optional<Courts> FindById(@Param("idcourts") long idCourts);

    @Modifying
    @Transactional
    @Query("delete from Courts item where item.idcourts = :idcourts")
    void DeleteByCi(@Param("idcourts") long idCourts);


    @Query("select MAX(item.idcourts) from Courts item")
    long selectByIdCourts();

    @Query("select item from Courts item where item.idcourts =:id ")
    Courts getById(@Param("id") long id);

    @Query("select item from Courts item where item.announcement.idannouncement = :idannouncement")
    Courts getByIdAnnouncement(@Param("idannouncement") long idannouncement);
    
    boolean existsCourtsByAnnouncement_IdannouncementAndDescription(long idannouncement, String description);
    
}
