package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.PointssubsubdetailMerit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PointssubsubdetailMeritRepository extends JpaRepository<PointssubsubdetailMerit, Long>{

    @Query("select item from PointssubsubdetailMerit item where item.idpointssubsubdetailmerit = :id")
    Optional<PointssubsubdetailMerit> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from PointssubsubdetailMerit item where item.idpointssubsubdetailmerit = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idpointssubsubdetailmerit) from pointssubsubdetail_merit", nativeQuery = true)
    long selectByIdpointssubsubdetailmerit();

    @Query("select item from PointssubsubdetailMerit item where item.idpointssubsubdetailmerit =:id ")
    PointssubsubdetailMerit getById(@Param("id") long id);

    List<PointssubsubdetailMerit> findPointssubsubdetailMeritsBySubsubdetailMerit_IdsubsubdetailmeritOrderBySubsubdetailMeritAsc(long id);

    @Query("select item from PointssubsubdetailMerit item where item.subsubdetailMerit.idsubsubdetailmerit = :id order by item.idpointssubsubdetailmerit asc")
    List<PointssubsubdetailMerit> listByIdUp(@Param("id") long id);

    boolean existsPointssubsubdetailMeritByDescriptionAndPointsAndSubsubdetailMerit_Idsubsubdetailmerit(String description, double points, long idSubsub);

}
