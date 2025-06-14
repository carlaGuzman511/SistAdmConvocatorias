package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DetailShapeRepository extends JpaRepository<DetailShape, Long> {

    @Query("select item from DetailShape item where item.iddetailshape = :id")
    Optional<DetailShape> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from DetailShape item where item.iddetailshape = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from DetailShape item where item.iddetailshape =:id ")
    DetailShape getById(@Param("id") long id);

    @Query(value = "select MAX(iddetailshape) from detail_shape", nativeQuery = true)
    long selectByIdDetailShape();

    List<DetailShape> findDetailShapesByShape_Idshape(long id);

    boolean existsDetailShapeByDescriptionAndShape_Idshape(String description, long idshape);

}
