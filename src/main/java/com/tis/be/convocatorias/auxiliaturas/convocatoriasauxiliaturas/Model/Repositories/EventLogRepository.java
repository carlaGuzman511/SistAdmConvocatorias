package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    @Query("select item from EventLog item where item.ideventlog = :id")
    Optional<EventLog> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from EventLog item where item.ideventlog = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from EventLog item where item.ideventlog =:id ")
    EventLog getById(@Param("id") long id);

    @Query(value = "select MAX(item.ideventlog ) from EventLog item")
    long selectByIdEventLog();

    boolean existsEventLogByDescription(String description);

}
