package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Long> {

    @Query("select item from Career item where item.idcareer = :codCareer")
    Optional<Career> FindByCi(@Param("codCareer") int codCareer);

    @Modifying
    @Transactional
    @Query("update Career item set item.name = :name where item.codCareer = :codCareer")
    void UpdateByCi(@Param("codCareer") int codCareer, @Param("name") String name);

    @Modifying
    @Transactional
    @Query("delete from Career item where item.codCareer = :codCareer")
    void DeleteByCi(@Param("codCareer") int id_career);

    @Query("select item from Career item where item.idcareer =:id ")
    Career getById(@Param("id") long id);

    boolean existsCareerByNameAndCodCareer(String name, int codcareer);

}
