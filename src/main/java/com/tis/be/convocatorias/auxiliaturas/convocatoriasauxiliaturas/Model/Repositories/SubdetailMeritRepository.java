package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

//import com.tis.convocatorias.postulant.service.Career.Career;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubdetailMerit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface SubdetailMeritRepository extends JpaRepository<SubdetailMerit, Long>{

    @Query("select item from SubdetailMerit item where item.idsubdetailmerit = :id")
    Optional<SubdetailMerit> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from SubdetailMerit item where item.idsubdetailmerit = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idsubdetailmerit) from subdetail_merit", nativeQuery = true)
    long selectByIdsubdetailmerit();

    @Query("select item from SubdetailMerit item where item.idsubdetailmerit =:id ")
    SubdetailMerit getById(@Param("id") long id);

    List<SubdetailMerit> findSubdetailMeritsByDetailMerit_IddetailmeritOrderByDetailMeritAsc(long id);

    @Query("select item from SubdetailMerit item where item.detailMerit.iddetailmerit = :id order by item.idsubdetailmerit asc")
    List<SubdetailMerit> listByUp(@Param("id") long id);


    boolean existsSubdetailMeritByDescriptionAndPercentageAndDetailMerit_Iddetailmerit(String description, int percentage, long idDetailmerit);

}
