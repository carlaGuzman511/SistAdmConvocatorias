package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select item from Event item where item.idevent = :id")
    Optional<Event> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Event item where item.idevent = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Event item where item.idevent =:id ")
    Event getById(@Param("id") long id);

    @Query(value = "select MAX(idevent) from event", nativeQuery = true)
    long selectByIdEvent();

    List<Event> findEventsBySchedule_Idschedule(long id);

    boolean existsEventByNameAndPlaceAndDateEventAndTimeEventAndSchedule_Idschedule(String name, String place, Date dateEvent, Date timeEvent, long idSchedule);

}
