package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Deadline;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {

    @Query("select item from Deadline item where item.iddeadline = :id")
    Optional<Deadline> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Deadline item where item.iddeadline = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Deadline item where item.iddeadline =:id ")
    Deadline getById(@Param("id") long id);

    @Query("select MAX(item.iddeadline) from Deadline item")
    long selectByIdDeadline();

    List<Deadline> findDeadlinesByAnnouncement_Idannouncement(long id);

    boolean existsDeadlineByAnnouncement_IdannouncementAndDeliveryPlaceAndDeliveryDateAndDeliveryTimeAndDescription(long idannouncement, String deliveryPlace, Date deliveryDate, Date deliveryTime, String description);

}
