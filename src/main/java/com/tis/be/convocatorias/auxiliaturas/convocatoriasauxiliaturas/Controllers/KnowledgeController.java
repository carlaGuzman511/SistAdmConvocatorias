package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.KnowledgeInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Knowledge;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;

    @RequestMapping("/knowledge")
    public List<Knowledge> getAllKnowledge() {
        return knowledgeService.getAllKnowledge();
    }

    @RequestMapping("/knowledge/{id}")
    public Knowledge getKnowledge(@PathVariable long id) {
        return knowledgeService.getKnowledge(id);
    }

    @PostMapping("/knowledge")
    public ResponseEntity<Long> addKnowledge(@RequestBody KnowledgeInput knowledgeInput) {
        Announcement announcement = announcementRepository.getById(knowledgeInput.getAnnouncement());
        if (!knowledgeRepository.existsKnowledgeByAnnouncement_IdannouncementAndDescriptionAndBaseScoreAndFinalScore(knowledgeInput.getAnnouncement(), knowledgeInput.getDescription(), knowledgeInput.getBaseScore(), knowledgeInput.getFinalScore())){
        Knowledge knowledge = new Knowledge();
        knowledge.setIdknowledge(999999);
        knowledge.setDescription(knowledgeInput.getDescription());
        knowledge.setBaseScore(knowledgeInput.getBaseScore());
        knowledge.setFinalScore(knowledgeInput.getFinalScore());
        knowledge.setAnnouncement(announcement);

        return ResponseEntity.ok().body(knowledgeService.addKnowledge(knowledge));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/knowledge/{id}")
    public void deleteKnowledge(@PathVariable long id) {
        knowledgeService.deleteKnowledge(id);
    }

    @RequestMapping("/knowledge/announcement/{id}")
    public List<Knowledge> ListByAnnouncement(@PathVariable("id") long id) {
        return knowledgeService.ListByAnnouncement(id);
    }

}
