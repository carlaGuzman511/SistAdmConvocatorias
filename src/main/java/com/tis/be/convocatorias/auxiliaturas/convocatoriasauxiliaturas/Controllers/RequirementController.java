package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.RequirementInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.RequirementUpdateAssignedItemsInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Auxiliary;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Requirement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequirementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RequirementController {

    @Autowired
    private RequirementService requirementService;
    @Autowired
    private RequirementRepository requirementRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;

    @RequestMapping("/requirement")
    public List<Requirement> getAllRequirement() {
        return requirementService.getAllRequirement();
    }

    @RequestMapping("/requirement/{id}")
    public Requirement getRequirement(@PathVariable int id) {
        return requirementService.getRequirement(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/requirement")
    public ResponseEntity<Long> addRequirement(@RequestBody RequirementInput requirementInput) {
        if (!requirementRepository.existsRequirementByAnnouncement_IdannouncementAndAuxiliary_IdauxiliaryAndItemsQuantity(
                requirementInput.getAnnouncement(), requirementInput.getAuxiliary(), requirementInput.getItemsQuantity())) {
            
            Announcement announcement = announcementRepository.getOne(requirementInput.getAnnouncement());
            Auxiliary auxiliary = auxiliaryRepository.getById(requirementInput.getAuxiliary());
            announcement.getAuxiliarys().add(auxiliary);
            announcementRepository.save(announcement);
            Requirement requirement = new Requirement();
            requirement.setIdrequirement(999999);
            requirement.setItemsQuantity(requirementInput.getItemsQuantity());
            requirement.setAssignedItems(requirementInput.getAssignedItems());
            requirement.setAnnouncement(announcement);
            requirement.setAuxiliary(auxiliary);

            return ResponseEntity.ok().body(requirementService.addRequirement(requirement));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/requirement/{id}")
    public void deleteRequirement(@PathVariable int id) {
        requirementService.deleteRequirement(id);
    }

    @RequestMapping("/requirement/auxiliary/{id}")
    public List<Requirement> listadoByAuxiliary(@PathVariable("id") long id) {
        return requirementService.ListadoByAuxiliary(id);
    }

    @RequestMapping("/requirement/announcement/{id}")
    public List<Requirement> listadoByAnnouncement(@PathVariable("id") long id) {
        return requirementService.ListadoByAnnouncement(id);
    }


    @PutMapping("/requirement/update/assigneditems")
    public void updateRequirementAssignedItems(@RequestBody RequirementUpdateAssignedItemsInput updateAssignedItems) {
        requirementService.updateRequirementAssignedItems(
                updateAssignedItems.getIdRequirement(),
                updateAssignedItems.getAssignedItems()
        );
    }

}
