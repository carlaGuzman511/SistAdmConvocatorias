package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.CourtsInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Courts;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.CourtsRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.CourtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class CourtsController {

    @Autowired
    private CourtsService courtsService;
    @Autowired
    private CourtsRepository courtsRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;


    @RequestMapping("/courts")
    public List<Courts> getAllCourts() {
        return courtsService.getAllCourts();
    }

    @RequestMapping("/courts/{id}")
    public Courts getCourts(@PathVariable long id) {
        return courtsService.getCourts(id);
    }

    @PostMapping("/courts")
    public ResponseEntity<Long> addCourts(@RequestBody CourtsInput courtsInput) {
        Announcement announcement = announcementRepository.getById(courtsInput.getIdannouncement());
        if (!courtsRepository.existsCourtsByAnnouncement_IdannouncementAndDescription(courtsInput.getIdannouncement(), courtsInput.getDescription())) {
            Courts courts = new Courts();
            courts.setIdcourts(999999);
            courts.setDescription(courtsInput.getDescription());
            courts.setMeritsCourts(courtsInput.getMeritsCourts());
            courts.setKnowledgeCourts(courtsInput.getKnowledgeCourts());
            courts.setStudentDelegateMerit(courtsInput.getStudentDelegateMerit());
            courts.setStudentDelegateKnowledge(courtsInput.getStudentDelegateKnowledge());
            courts.setNumberMeritCourts(courtsInput.getNumberMeritCourts());
            courts.setNumberKnowledgeCourts(courtsInput.getNumberKnowledgeCourts());
            courts.setNumberKnowledgeStudentDelegate(courtsInput.getNumberKnowledgeStudentDelegate());
            courts.setNumberMeritStudentDelegate(courtsInput.getNumberMeritStudentDelegate());
            courts.setAnnouncement(announcement);

            return ResponseEntity.ok().body(courtsService.addCourts(courts));
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/courts/{id}")
    public void deleteCourts(@PathVariable long id) {
        courtsService.deleteCourts(id);
    }

    @RequestMapping("/courts/description/{id}")
    public Courts courtsDescription(@PathVariable long id) {
        return courtsService.getDescription(id);
    }

}
