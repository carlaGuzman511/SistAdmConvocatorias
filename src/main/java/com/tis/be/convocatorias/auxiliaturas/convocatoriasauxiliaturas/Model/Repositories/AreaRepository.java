package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query("select item from Area item where item.idarea = :id")
    Optional<Area> FindByCi(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from Area item where item.idarea = :id")
    void DeleteByCi(@Param("id") int id);

    @Query("select item from Area item where item.idarea =:id ")
    Area getById(@Param("id") long id);


    boolean existsAreaByName(String name);

//    @Query("select item from Area item join Announcement ann on ann.area.idarea = item.idarea and ann.idannouncement = :idannouncement ")
//    List<Area> listByAnnouncement(@Param("idannouncement") long idannouncement);
}
