package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput.MeritComplexInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.MeritInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Merit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.MeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.MeritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MeritController {

    @Autowired
    private MeritService meritService;
    @Autowired
    private MeritRepository meritRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private DetailMeritController detailMeritController;

    @RequestMapping("/merit")
    public List<Merit> getAllMerit() {
        return meritService.getAllMerit();
    }

    @RequestMapping("/merit/{id}")
    public Merit getMerit(@PathVariable long id) {
        return meritService.getMerit(id);
    }

    @PostMapping("/merit")
    public ResponseEntity<Long> addMerit(@RequestBody MeritInput meritInput) {
        Announcement announcement = announcementRepository.getById(meritInput.getAnnouncement());
        if (!meritRepository.existsMeritByAnnouncement_IdannouncementAndDescriptionAndBaseScoreAndFinalScore(
                meritInput.getAnnouncement(), meritInput.getDescription(), meritInput.getBaseScore(), meritInput.getFinalScore())) {
            Merit merit = new Merit();
            merit.setIdmerit(999999);
            merit.setDescription(meritInput.getDescription());
            merit.setBaseScore(meritInput.getBaseScore());
            merit.setFinalScore(meritInput.getFinalScore());
            merit.setNote(meritInput.getNote());
            merit.setAnnouncement(announcement);
            meritService.addMerit(merit);

            return ResponseEntity.ok().body(meritService.getlastIdMerit());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/merit/complex")
    public ResponseEntity<Long> addMeritComplex(@RequestBody MeritComplexInput meritComplexInput) {
        Announcement announcement = announcementRepository.getById(meritComplexInput.getIdannouncement());
        if (!meritRepository.existsMeritByAnnouncement_IdannouncementAndDescriptionAndBaseScoreAndFinalScore(
                meritComplexInput.getIdannouncement(), meritComplexInput.getDescription(), meritComplexInput.getBaseScore(), meritComplexInput.getFinalScore())) {
            Merit merit = new Merit();
            merit.setIdmerit(999999);
            merit.setDescription(meritComplexInput.getDescription());
            merit.setBaseScore(meritComplexInput.getBaseScore());
            merit.setFinalScore(meritComplexInput.getFinalScore());
            merit.setNote(meritComplexInput.getNote());
            merit.setAnnouncement(announcement);
            meritService.addMerit(merit);
            for (int i = 0; i < meritComplexInput.getMerits().size(); i++) {

                detailMeritController.addComplexDetailMerit(meritComplexInput.getMerits().get(i), meritService.getlastIdMerit());
            }
            return ResponseEntity.ok().body(meritService.getlastIdMerit());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    @RequestMapping("/merit/list/complex")

    @DeleteMapping("/merit/{id}")
    public void deleteMerit(@PathVariable long id) {
        meritService.deleteMerit(id);
    }

    @RequestMapping("/merit/announcement/{id}")
    public List<Merit> listByAnnouncement(@PathVariable("id") long id) {
        return meritService.listByAnnouncement(id);
    }


}
