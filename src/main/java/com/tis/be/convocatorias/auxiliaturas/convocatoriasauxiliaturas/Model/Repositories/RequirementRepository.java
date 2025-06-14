package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    @Query("select item from Requirement item where item.idrequirement = :id")
    Optional<Requirement> FindByCi(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Requirement item where item.idrequirement = :id")
    void DeleteByCi(@Param("id") long id);

    @Query(value = "select MAX(idrequirement) from requirement", nativeQuery = true)
    long selectByIdrequirement();

    @Query("select item from Requirement item where item.idrequirement =:id ")
    Requirement getById(@Param("id") long id);

    List<Requirement> findRequirementByAuxiliary_Idauxiliary(long idAux);

    @Modifying
    @Transactional
    @Query("update Requirement item set item.assignedItems = :assignedItems where item.idrequirement = :idRequirement")
    void UpdateByAssignedItems(
            @Param("idRequirement") long idRequirement,
            @Param("assignedItems") int assignedItems
    );

//    @Modifying
//    @Transactional
//    @Query("update Requirement item set item.itemsQuantity = :itemsQuantity, item.assignedItems = :assignedItems, item.")

    List<Requirement> findRequirementsByAnnouncement_Idannouncement(long id);

    boolean existsRequirementByAnnouncement_IdannouncementAndAuxiliary_IdauxiliaryAndItemsQuantity(long idAnnouncement, long idAuxiliary, int itemsQuantity);

}
