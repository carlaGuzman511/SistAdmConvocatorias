package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.RequestPostulant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RequestPostulantRepository extends JpaRepository<RequestPostulant, Long> {

    @Query("select item from RequestPostulant item where item.idrequestpostulant = :id")
    Optional<RequestPostulant> FindByCi(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from RequestPostulant item where item.idrequestpostulant = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idrequestpostulant) from request_postulant", nativeQuery = true)
    long selectByIdrequestpostulant();

    @Query("select item from RequestPostulant item where item.idrequestpostulant =:id ")
    RequestPostulant getById(@Param("id") long id);

    List<RequestPostulant> findRequestPostulantByPostulant_Idpostulant(long idPost);

    @Modifying
    @Transactional
    @Query("update RequestPostulant item set item.status = :status, item.observation = :observation where item.idrequestpostulant = :idrequestpostulant")
    void updateRequestPostulantStatus(
            @Param("idrequestpostulant") long idrequestpostulant,
            @Param("status") boolean status,
            @Param("observation") String observation
    );

    boolean existsRequestPostulantByPostulant_IdpostulantAndRequestdetail_IdrequestdetailAndStatus(long idPostulant, long idRequestDetail, boolean status);

}
