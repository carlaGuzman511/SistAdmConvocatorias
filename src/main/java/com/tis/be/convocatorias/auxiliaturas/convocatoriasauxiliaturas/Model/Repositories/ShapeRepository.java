package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

//import com.tis.convocatorias.postulant.service.Career.Career;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Long>{

    @Query("select item from Shape item where item.idshape = :id")
    Optional<Shape> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Shape item where item.idshape = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idshape) from shape", nativeQuery = true)
    long selectByIdshape();

    @Query("select item from Shape item where item.idshape =:id ")
    Shape getById(@Param("id") long id);

    List<Shape> findShapesByAnnouncement_Idannouncement(long id);


    boolean existsShapeByAnnouncement_IdannouncementAndDescription(long idAnnouncement, String description);

}
