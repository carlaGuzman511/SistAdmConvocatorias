package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ManagementRepository extends JpaRepository<Management, Long> {

    @Query("select item from Management item where item.idmanagement = :id")
    Optional<Management> FindByCi(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from Management item where item.idmanagement = :id")
    void DeleteByCi(@Param("id") int id);

    @Query("select item from Management item where item.idmanagement =:id ")
    Management getById(@Param("id") long id);

    boolean existsByPeriod(String period);

    Management findManagementByPeriod(String period);

    boolean existsManagementByPeriod(String period);

}
