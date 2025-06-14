package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

//import com.tis.convocatorias.postulant.service.Career.Career;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubsubdetailMerit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface SubsubdetailMeritRepository extends JpaRepository<SubsubdetailMerit, Long>{

    @Query("select item from SubsubdetailMerit item where item.idsubsubdetailmerit = :id")
    Optional<SubsubdetailMerit> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from SubsubdetailMerit item where item.idsubsubdetailmerit = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idsubsubdetailmerit) from subsubdetail_merit", nativeQuery = true)
    long selectByIdsubsubdetailmerit();

    @Query("select item from SubsubdetailMerit item where item.idsubsubdetailmerit =:id ")
    SubsubdetailMerit getById(@Param("id") long id);

    List<SubsubdetailMerit> findSubsubdetailMeritsBySubdetailMerit_IdsubdetailmeritOrderBySubdetailMeritAsc(long id);

    @Query("select item from SubsubdetailMerit item where item.subdetailMerit.idsubdetailmerit = :id order by item.idsubsubdetailmerit asc")
    List<SubsubdetailMerit> listByUp(@Param("id") long id);



    boolean existsSubsubdetailMeritByDescriptionAndPercentageAndSubdetailMerit_Idsubdetailmerit(String description, int percentage, long idSubdetail);

}
