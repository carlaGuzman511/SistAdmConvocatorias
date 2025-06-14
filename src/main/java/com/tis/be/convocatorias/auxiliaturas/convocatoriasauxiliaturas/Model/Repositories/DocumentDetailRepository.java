package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DocumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DocumentDetailRepository extends JpaRepository<DocumentDetail, Long> {

    @Query("select item from DocumentDetail item where item.iddocumentdetail = :id")
    Optional<DocumentDetail> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from DocumentDetail item where item.iddocumentdetail = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from DocumentDetail item where item.iddocumentdetail =:id ")
    DocumentDetail getById(@Param("id") long id);

    @Query("select MAX(item.iddocumentdetail) from DocumentDetail item")
    long selectByIdDocumentDetail();

    List<DocumentDetail> findDocumentDetailsByDocument_Iddocument(long id);

    boolean existsDocumentDetailByDetailAndDocument_Iddocument(String detail, long idDocument);

}
