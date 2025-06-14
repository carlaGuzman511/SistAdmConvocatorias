package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query("select item from Authority item where item.idauthority = :id")
    Optional<Authority> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Authority item where item.idauthority = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Authority item where item.idauthority =:id ")
    Authority getById(@Param("id") long id);

    @Query(value = "select MAX(idauthority) from authority", nativeQuery = true)
    long selectByIdAuthority();

    List<Authority> findAuthoritiesByAnnouncement_Idannouncement(long id);

    boolean existsAuthorityByAnnouncement_IdannouncementAndNameAndPosition(long idannouncement, String name, String position);

}
