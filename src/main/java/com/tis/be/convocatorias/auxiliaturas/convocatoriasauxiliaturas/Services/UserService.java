package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.User;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public User getUser(long id) {
        return userRepository.FindById(id)
                .orElse(null);

    }

    public long addUser(User user) {
        userRepository.save(user);
        return userRepository.selectByIdUser();
    }

    public void deleteUser(long id) {
        userRepository.DeleteByCi(id);
    }
    
    public User getUserByLogin(String ci, String password) {
        return userRepository.FindUserByLogin(ci, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Username or Password Not Found on Database"));

    }

    public User VerifyUser(String ci){
        return userRepository.getUserByIdentity(ci);
    }

    public void updateUserAnnouncements(long idannouncement){
        long idaux = 1;
        User user = userRepository.getOne(idaux);
        user.getAnnouncements().add(announcementRepository.getById(idannouncement));
        userRepository.save(user);

        idaux = 2;
        user = userRepository.getOne(idaux);
        user.getAnnouncements().add(announcementRepository.getById(idannouncement));
        userRepository.save(user);
    }

    public List<User> listCourtsByAnnouncement(long idannouncement){
        return userRepository.listCourtsByAnnouncement(idannouncement);
    }

}
