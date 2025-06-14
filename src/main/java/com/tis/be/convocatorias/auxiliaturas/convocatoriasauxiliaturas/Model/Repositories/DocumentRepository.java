package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select item from Document item where item.iddocument = :id")
    Optional<Document> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Document item where item.iddocument = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Document item where item.iddocument =:id ")
    Document getById(@Param("id") long id);

    @Query(value = "select MAX(iddocument) from document", nativeQuery = true)
    long selectByIdDocument();

    List<Document> findDocumentsByAnnouncement_Idannouncement(long id);

    boolean existsDocumentByAnnouncement_IdannouncementAndNameAndNote(long idannouncement, String name, String note);

}
