package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.RequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RequestDetailRepository extends JpaRepository<RequestDetail, Long> {

    @Query("select item from RequestDetail item where item.idrequestdetail = :id")
    Optional<RequestDetail> FindByCi(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from RequestDetail item where item.idrequestdetail = :id")
    void DeleteById(@Param("id") long id);

    @Query(value = "select MAX(idrequestdetail) from request_detail", nativeQuery = true)
    long selectByIdrequest();

    @Query("select item from RequestDetail item where item.idrequestdetail =:id ")
    RequestDetail getById(@Param("id") long id);

    @Query("select item from RequestDetail item join Request request on item.request.idrequest = request.idrequest and request.announcement.idannouncement = :id")
    List<RequestDetail> getByIdAnnouncement(@Param("id") long id);

    List<RequestDetail> findRequestDetailsByRequest_Idrequest(long id);

    boolean existsRequestDetailByDescriptionAndRequest_Idrequest(String description, long idRequest);

}
