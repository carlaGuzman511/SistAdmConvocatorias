package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Auxiliary;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AuxiliaryRepository extends JpaRepository<Auxiliary, Long>{

    @Query("select item from Auxiliary item where item.idauxiliary = :id")
    Optional<Auxiliary> FindById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Auxiliary item where item.idauxiliary = :id")
    void DeleteByCi(@Param("id") long id);


    @Query(value = "select MAX(idauxiliary) from auxiliary", nativeQuery = true)
    long selectByIdauxiliary();

    @Query("select item from Auxiliary item join Label label on item.idauxiliary = label.auxiliary.idauxiliary " +
            "join Postulant postulant on label.postulantes.idpostulant = postulant.idpostulant where postulant.person.id = :idperson")
    List<Auxiliary> getAuxByPerson(@Param("idperson") long idperson);

    @Query("select item from Auxiliary item where item.idauxiliary =:id ")
    Auxiliary getById(@Param("id") long id);

    @Query("select item.thematics from Auxiliary item where item.idauxiliary = :id")
    List<Thematic> listThematicByIdAuxiliary(@Param("id") long id);

    List<Auxiliary> findAuxiliariesByArea_Name(String area);

    List<Auxiliary> findAuxiliaryByArea_IdareaAndAcademicUnit_Idacademicunit(long idarea, long idacademicunit);

    boolean existsAuxiliaryByAcademicUnit_IdacademicunitAndArea_IdareaAndCodeAndName(long idacademicunit, long idarea, String code, String name);

}
