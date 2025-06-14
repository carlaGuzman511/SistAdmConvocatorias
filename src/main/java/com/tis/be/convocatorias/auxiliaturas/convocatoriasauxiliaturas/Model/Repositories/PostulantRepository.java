package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Postulant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostulantRepository extends JpaRepository<Postulant, Long>{

    @Query("select item from Postulant item where item.idpostulant = :id_Postulant")
    Optional<Postulant> FindById(@Param("id_Postulant") long id_Postulant);

    @Modifying
    @Transactional
    @Query("update Postulant item set item.status = :Status, item.person = :person, item.career = :career where item.idpostulant = :id_Postulant")
    void UpdateByCi(@Param("id_Postulant") long id_Postulant, @Param("Status") boolean Status, @Param("person") long person, @Param("career") int career);

    @Modifying
    @Transactional
    @Query("delete from Postulant item where item.idpostulant = :id_Postulant")
    void DeleteByCi(@Param("id_Postulant") long id_Postulant);

    List<Postulant> findPostulantesByPerson_Id(long id);

    List<Postulant> findByCareer_CodCareerOrderByPerson(int career);

    List<Postulant> queryByPerson_CiAndCareer_CodCareer(String ci, int codCareer);

    @Query(value = "select MAX(idpostulant) from postulant", nativeQuery = true)
    long selectByIdpostulant();

    @Query("select item from Postulant item where item.idpostulant =:id ")
    Postulant getById(@Param("id") long id);

    @Query("select item from Postulant item inner join ResultOralTeachingEvaluation result on item.idpostulant = result.postulantes.idpostulant and result.score >= 51" +
            "join Label label on item.idpostulant = label.postulantes.idpostulant join LogBook logbook on label.idlabel = logbook.label.idlabel and item.status = true ")
    List<Postulant> listPostulantFromResultOralandLogbookExists();

    @Query("select item from Postulant item join Label label on item.idpostulant = label.postulantes.idpostulant join LogBook logbook on label.idlabel = logbook.label.idlabel " +
            "join item.announcements ann join ann.auxiliarys aux  join aux.thematics them")
    List<Postulant> listPostulantIfLaboratoryOrTeaching();

    @Modifying
    @Transactional
    @Query("update Postulant item set item.status = :status where item.idpostulant = :idPostulant")
    void UpdateStatusPostulant(
            @Param("idPostulant") long idPostulant,
            @Param("status") boolean status
    );


    boolean existsPostulantByCareer_IdcareerAndPerson_IdAndStatus(long idCareer, long idPerson, boolean status);

}
