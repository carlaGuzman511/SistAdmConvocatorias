package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select item from Role item where item.idrole = :id")
    Optional<Role> FindByCi(@Param("id") long id);

    @Query("select item from Role item where item.idrole =:id ")
    Role getById(@Param("id") long id);


    boolean existsRoleByName(String name);

}
