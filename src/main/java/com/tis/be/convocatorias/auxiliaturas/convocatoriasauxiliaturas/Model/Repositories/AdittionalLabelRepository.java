package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AdittionalLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AdittionalLabelRepository extends JpaRepository<AdittionalLabel, Long> {

    @Query("select item from AdittionalLabel item where item.idadittionallabel = :id")
    Optional<AdittionalLabel> FindByCi(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from AdittionalLabel item where item.idadittionallabel = :id")
    void DeleteByCi(@Param("id") int id);

    @Query(value = "select MAX(idadittionallabel) from adittional_label", nativeQuery = true)
    long selectByIdadditionallabel();

    @Query("select item from AdittionalLabel item where item.idadittionallabel =:id ")
    AdittionalLabel getById(@Param("id") long id);

    boolean existsAdittionalLabelByLabel_IdlabelAndField(long idlabel, String field);

}
