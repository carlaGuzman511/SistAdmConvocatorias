package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select item from Schedule item where item.idschedule = :id")
    Optional<Schedule> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Schedule item where item.idschedule = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Schedule item where item.idschedule =:id ")
    Schedule getById(@Param("id") long id);

    @Query(value = "select MAX(idschedule) from schedule", nativeQuery = true)
    long selectByIdSchedule();

    List<Schedule> findSchedulesByAnnouncement_Idannouncement(long id);


    boolean existsScheduleByAnnouncement_IdannouncementAndDescriptionAndNote(long idAnnouncement, String description, String note);

}
