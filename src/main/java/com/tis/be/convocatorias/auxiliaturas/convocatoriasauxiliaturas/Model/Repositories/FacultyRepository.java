package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query("select item from Faculty item where item.idfaculty = :id")
    Optional<Faculty> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Faculty item where item.idfaculty = :id")
    void DeleteByCi(@Param("id") long id);

    @Query("select item from Faculty item where item.idfaculty =:id ")
    Faculty getById(@Param("id") long id);

    boolean existsFacultyByName(String name);

}
