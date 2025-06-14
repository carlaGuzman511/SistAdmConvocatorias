package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories;

//import com.tis.convocatorias.postulant.service.Career.Career;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Role;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    @Query("select item from User item where item.iduser = :id")
    Optional<User> FindById(@Param("id") long id);


    @Query("select item from User item where item.identity = :ci and item.password = :password")
    Optional<User> FindUserByLogin(@Param("ci") String ci, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("delete from User item where item.iduser = :id")
    void DeleteByCi(@Param("id") long id);


    @Query(value = "select MAX(iduser) from user", nativeQuery = true)
    long selectByIdUser();


    @Query("select item from User item where item.iduser =:id ")
    User getById(@Param("id") long id);

    User getUserByIdentity(String ci);

    @Query("select item from User item join item.announcements ann on ann.idannouncement = :idannouncement and (item.role.idrole = 3 or item.role.idrole = 4 or item.role.idrole = 5 or item.role.idrole = 6)")
    List<User> listCourtsByAnnouncement(@Param("idannouncement") long idannouncement);


    boolean existsUserByIdentityAndPassword(String identity, String password);

}
