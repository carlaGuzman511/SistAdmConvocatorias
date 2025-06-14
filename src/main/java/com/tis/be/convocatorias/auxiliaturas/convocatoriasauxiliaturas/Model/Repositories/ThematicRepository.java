package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ThematicRepository extends JpaRepository<Thematic, Long> {

    @Query("select item from Thematic item where item.idthematic = :id")
    Optional<Thematic> FindByCi(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from Thematic item where item.idthematic = :id")
    void DeleteByCi(@Param("id") int id);

    @Query(value = "select MAX(idthematic) from thematic", nativeQuery = true)
    long selectByIdthematic();

    @Query("select item from Thematic item where item.idthematic =:id ")
    Thematic getById(@Param("id") long id);

    @Query("select item from Thematic item join item.auxiliarys auxthem join Auxiliary aux on aux.idauxiliary = auxthem.idauxiliary " +
            "join aux.announcements auxann on auxann.idannouncement = :idannouncement group by item")
    List<Thematic> listByIdAnnouncement(@Param("idannouncement") long id);


    boolean existsThematicByName(String name);

}
