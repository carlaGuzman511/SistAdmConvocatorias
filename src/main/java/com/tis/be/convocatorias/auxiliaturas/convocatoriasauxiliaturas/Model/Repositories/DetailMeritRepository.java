package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailMerit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DetailMeritRepository extends JpaRepository<DetailMerit, Long>{

    @Query("select item from DetailMerit item where item.iddetailmerit = :id")
    Optional<DetailMerit> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from DetailMerit item where item.iddetailmerit = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(iddetailmerit) from detail_merit", nativeQuery = true)
    long selectByIddetailmerit();

    @Query("select item from DetailMerit item where item.iddetailmerit =:id ")
    DetailMerit getById(@Param("id") long id);

    List<DetailMerit> findDetailMeritsByMerit_IdmeritOrderByMeritAsc(long id);

    @Query("select item from DetailMerit item where item.merit.idmerit = :id order by item.iddetailmerit asc")
    List<DetailMerit> listByUp(@Param("id") long id);

    boolean existsDetailMeritByDescriptionAndPercentageAndMerit_Idmerit(String description, int percentage, long idMerit);

}
