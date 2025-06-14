package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AcademicUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface AcademicUnitRepository extends JpaRepository<AcademicUnit, Long> {

    @Query("select item from AcademicUnit item where item.idacademicunit = :id")
    Optional<AcademicUnit> FindByCi(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from AcademicUnit item where item.idacademicunit = :id")
    void DeleteByCi(@Param("id") int id);

    @Query("select item.academicUnit from Announcement item where item.academicUnit.faculty.idfaculty = :idFac and item.management.idmanagement = :idMan group by item.academicUnit ")
    List<AcademicUnit> listByManFac( @Param("idFac") long idFac, @Param("idMan") long idMan);

    @Query("select item from AcademicUnit item where item.idacademicunit =:id ")
    AcademicUnit getById(@Param("id") long id);

    @Query(value = "select MAX(item.idacademicunit) from AcademicUnit item")
    long selectByIdAcademicUnit();

    boolean existsAcademicUnitByNameAndFaculty_Idfaculty(String name, long idfaculty);

}
