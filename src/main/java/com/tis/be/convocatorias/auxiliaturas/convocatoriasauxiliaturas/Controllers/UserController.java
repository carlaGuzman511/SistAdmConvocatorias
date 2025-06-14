package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.UserInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.LoginUserInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.UserSingleInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.UserVerificationInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.CourtsService;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonController personController;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;
    @Autowired
    private ThematicRepository thematicRepository;
    @Autowired
    private CourtsService courtsService;


    @RequestMapping("/user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    public ResponseEntity<Long> addUser(@RequestBody UserInput userInput) {
        if (!userRepository.existsUserByIdentityAndPassword(userInput.getCi(), userInput.getPassword())) {
            Set<Auxiliary> auxiliary = new HashSet<>();
            for (int i = 0; i < userInput.getAuxiliarys().size(); i++) {
                auxiliary.add(auxiliaryRepository.getById(userInput.getAuxiliarys().get(i)));
            }
            Set<Thematic> thematic = new HashSet<>();
            for (int i = 0; i < userInput.getThematics().size(); i++) {
                thematic.add(thematicRepository.getById(userInput.getThematics().get(i)));
            }
            Role role = roleRepository.getById(userInput.getRole());
            ResponseEntity<Long> idPerson = personController.addPerson(userInput.getPerson());
            Person person = personRepository.getById(idPerson.getBody());
            User user = new User();
            user.setIduser(999999);
            user.setIdentity(userInput.getCi());
            user.setPassword(userInput.getPassword());
            user.setEnable(userInput.isEnable());
            user.setStartDate(userInput.getStartDate());
            user.setEndDate(userInput.getEndDate());
            user.setStartHour(userInput.getStartHour());
            user.setEndHour(userInput.getEndHour());
            user.setRole(role);
            user.setPerson(person);
            Set<Announcement> announcement = new HashSet<>();
            for (int i = 0; i < userInput.getAnnouncements().size(); i++) {
                announcement.add(announcementRepository.getById(userInput.getAnnouncements().get(i)));
                long idannounaux = announcementRepository.getById(userInput.getAnnouncements().get(i)).getIdannouncement();
                courtsService.countingCourts(user.getRole().getName(), idannounaux);
            }
            user.setAnnouncements(announcement);
            user.setAuxiliarys(auxiliary);
            user.setThematics(thematic);

            return ResponseEntity.ok().body(userService.addUser(user));

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/user/single")
    public ResponseEntity<Long> addUserSingle(@RequestBody UserSingleInput userInput) {
        if (!userRepository.existsUserByIdentityAndPassword(userInput.getCi(), userInput.getPassword())) {
            Role role = roleRepository.getById(userInput.getRole());
            ResponseEntity<Long> idPerson = personController.addPerson(userInput.getPerson());
            Person person = personRepository.getById(idPerson.getBody());
            User user = new User();
            user.setIduser(999999);
            user.setIdentity(userInput.getCi());
            user.setPassword(userInput.getPassword());
            user.setEnable(userInput.isEnable());
            user.setStartDate(userInput.getStartDate());
            user.setEndDate(userInput.getEndDate());
            user.setStartHour(userInput.getStartHour());
            user.setEndHour(userInput.getEndHour());
            user.setRole(role);
            user.setPerson(person);

            return ResponseEntity.ok().body(userService.addUser(user));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    @DeleteMapping("/user/{id}")
    public void deleteAnnouncement(@PathVariable long id) {
        userService.deleteUser(id);
    }


    @PostMapping("/user/login")
    public User getUserByLogin(@RequestBody LoginUserInput input) {
        return userService.getUserByLogin(input.getCi(), input.getPassword());

    }

    @PostMapping("/user/verification")
    public User UserVerification(@RequestBody UserVerificationInput userVerificationInput) {

        return userService.VerifyUser(userVerificationInput.getCi());
    }

    @RequestMapping("/user/courts/announcement/{id}")
    public List<User> listCourtsByAnnouncement(@PathVariable long id) {
        return userService.listCourtsByAnnouncement(id);
    }
}
