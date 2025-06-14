package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select item from Person item where item.ci = :ci")
    Optional<Person> FindByCi(@Param("ci") String ci);

    @Modifying
    @Transactional
    @Query("update Person item set item.name = :name, item.lastName = :lastname, item.address = :address, item.phoneNumber = :phoneNumber, item.email = :email where item.ci = :ci")
    void UpdateByCi(@Param("ci") String ci, @Param("name") String name, @Param("lastname") String lastname, @Param("address") String address, @Param("phoneNumber") int phoneNumber, @Param("email") String email);

    @Modifying
    @Transactional
    @Query("delete from Person item where item.ci = :ci")
    void DeleteByCi(@Param("ci") String ci);

    @Query(value = "select ci from Person where Person.name =:ci ", nativeQuery = true)
    Optional<Object> encontrarci(@Param("ci") String ci);

    @Query("select item from Person item where item.id =:id ")
    Person getById(@Param("id") long id);

    boolean existsPersonByCi(String ci);

}
