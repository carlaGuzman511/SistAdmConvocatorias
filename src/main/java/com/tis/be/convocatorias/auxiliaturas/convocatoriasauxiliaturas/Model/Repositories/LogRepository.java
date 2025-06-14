package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query("select item from Log item where item.idlog = :id")
    Optional<Log> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Log item where item.idlog = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Log item where item.idlog =:id ")
    Log getById(@Param("id") long id);

    @Query(value = "select MAX(item.idlog) from Log item")
    long selectByIdLog();
}
