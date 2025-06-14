package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select item from Request item where item.idrequest = :id")
    Optional<Request> FindByCi(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Request item where item.idrequest = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idrequest) from request", nativeQuery = true)
    long selectByIdrequest();

    @Query("select item from Request item where item.idrequest =:id ")
    Request getById(@Param("id") long id);

    List<Request> findRequestByAnnouncement_Idannouncement(long idAnnoun);

    boolean existsRequestByAnnouncement_IdannouncementAndDescriptionAndNote(long idAnnouncement, String description, String note);

}
