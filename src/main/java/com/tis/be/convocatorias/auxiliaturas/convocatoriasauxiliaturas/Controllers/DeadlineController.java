package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DeadlineCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.DeadlineInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DeadlineRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DeadlineController {

    @Autowired
    private DeadlineService deadlineService;
    @Autowired
    private DeadlineRepository deadlineRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;


    @RequestMapping("/deadline")
    public List<Deadline> getAllDeadline() {
        return deadlineService.getAllDeadline();
    }

    @RequestMapping("/deadline/{id}")
    public Deadline getDeadline(@PathVariable long id) {
        return deadlineService.getDeadline(id);
    }

    @PostMapping("/deadline")
    public ResponseEntity<Long> addDeadline(@RequestBody DeadlineInput deadlineInput) {
        Announcement announcement = announcementRepository.getById(deadlineInput.getAnnouncement());
        if (!deadlineRepository.existsDeadlineByAnnouncement_IdannouncementAndDeliveryPlaceAndDeliveryDateAndDeliveryTimeAndDescription(
                deadlineInput.getAnnouncement(), deadlineInput.getDeliveryPlace(),deadlineInput.getDeliveryDate(), deadlineInput.getDeliveryTime(), deadlineInput.getDescription())) {
            Deadline deadline = new Deadline();
            deadline.setIddeadline(999999);
            deadline.setDescription(deadlineInput.getDescription());
            deadline.setDeliveryDate(deadlineInput.getDeliveryDate());
            deadline.setDeliveryTime(deadlineInput.getDeliveryTime());
            deadline.setDeliveryPlace(deadlineInput.getDeliveryPlace());
            deadline.setAnnouncement(announcement);

            return ResponseEntity.ok().body(deadlineService.addDeadline(deadline));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    public long addDeadlineCreate(@RequestBody DeadlineCreateInput deadlineCreateInput, long id){
        Announcement announcement = announcementRepository.getById(id);
        Deadline deadline = new Deadline();
        deadline.setIddeadline(999999);
        deadline.setDeliveryPlace(deadlineCreateInput.getPlace());
        deadline.setDeliveryTime(deadlineCreateInput.getTime());
        deadline.setDeliveryDate(deadlineCreateInput.getDate());
        deadline.setDescription(deadlineCreateInput.getDescription());
        deadline.setAnnouncement(announcement);

        return deadlineService.addDeadline(deadline);
    }

    @DeleteMapping("/deadline/{id}")
    public void deleteDeadline(@PathVariable long id) {
        deadlineService.deleteDeadline(id);
    }

    @RequestMapping("/deadline/announcement/{id}")
    public List<Deadline> ListByAnnouncement(@PathVariable("id") long id){
        return deadlineService.ListByAnnouncement(id);
    }

}
