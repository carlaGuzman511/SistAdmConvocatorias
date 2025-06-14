package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.LabelInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.LabelTeachingInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class LabelController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;
    @Autowired
    private PersonController personController;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AnnouncementController announcementController;

    @RequestMapping("/label")
    public List<Label> getAllLabel() {
        return labelService.getAllLabel();
    }

    @RequestMapping("/label/{id}")
    public Label getLabel(@PathVariable long id) {
        return labelService.getLabel(id);
    }

    @PostMapping("/label")
    public ResponseEntity<Long> addLabel(@RequestBody LabelInput labelInput) {
        Postulant postulant = postulantRepository.getById(labelInput.getPostulant());
        Auxiliary auxiliary = auxiliaryRepository.getById(labelInput.getAuxiliary());
        if (!labelRepository.existsLabelByAnnouncement_IdannouncementAndAuxiliary_IdauxiliaryAndPostulantes_Idpostulant(labelInput.getAnnouncement(), labelInput.getAuxiliary(), labelInput.getPostulant())){
        Announcement announcement = announcementRepository.getById(labelInput.getAnnouncement());
        Label label = new Label();
        label.setIdlabel(999999);
        label.setPostulantes(postulant);
        label.setAuxiliary(auxiliary);
        label.setAnnouncement(announcement);
        announcementController.updateAnnouncementPostulants(labelInput.getAnnouncement(), postulant);

        return ResponseEntity.ok().body(labelService.addLabel(label));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/labelTeaching")
    public List<Long> addLabelTeaching(@RequestBody LabelTeachingInput labelTeachingInput) {
        List<Long> ret = new ArrayList<>();
        ResponseEntity<Long> idPerson = personController.addPerson(labelTeachingInput.getPerson());
        Person person = personRepository.getById(idPerson.getBody());
        Career career = careerRepository.getById(labelTeachingInput.getCareer());
        Announcement announcement = announcementRepository.getById(labelTeachingInput.getAnnouncement());
        Postulant postulant = new Postulant();
        for (int i = 0; i < labelTeachingInput.getAuxiliary().size(); i++) {
            Auxiliary auxiliary = auxiliaryRepository.getById(labelTeachingInput.getAuxiliary().get(i));

            postulant.setIdpostulant(9999999);
            postulant.setStatus(false);
            postulant.setPerson(person);
            postulant.setCareer(career);
            postulantRepository.save(postulant);
            long idaux = postulantRepository.selectByIdpostulant();

            Postulant postulantNuevo = postulantRepository.getById(idaux);

            Label label = new Label();
            label.setIdlabel(999999);
            label.setPostulantes(postulantNuevo);
            label.setAuxiliary(auxiliary);
            label.setAnnouncement(announcement);

            ret.add(labelService.addLabel(label));

            announcementController.updateAnnouncementPostulants(labelTeachingInput.getAnnouncement(), postulantNuevo);

        }
        return ret;
    }

    @DeleteMapping("/label/{id}")
    public void deleteLabel(@PathVariable long id) {
        labelService.deleteLabel(id);
    }

    @PostMapping("/label/searchByText")
    public List<Label> searchPostulants(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        String searchTerm2 = "";
        String idAcad = body.get("idacademicunit");
        String idAnnoun = body.get("announcement");
        String idMan = labelService.lastManagement();
        String[] currencies = searchTerm.split(" ");
        if (currencies.length > 1) {
            searchTerm = currencies[0];
            searchTerm2 = currencies[1];
            List<Label> combo = new ArrayList<>();
            combo.addAll(labelService.searcherByTextAcadMan(searchTerm, searchTerm, idAcad, idMan, idAnnoun));
            combo.addAll(labelService.searcherByTextAcadMan(searchTerm2, searchTerm2, idAcad, idMan, idAnnoun));
            LinkedHashSet<Label> hashSet = new LinkedHashSet<>(combo);
            ArrayList<Label> listWithoutDuplicates = new ArrayList<>(hashSet);
            return listWithoutDuplicates;
        }
        
        return labelService.searcherByTextAcadMan(searchTerm, searchTerm, idAcad, idMan, idAnnoun);
    }
    
    @PostMapping("/label/searchByText/statusTrue")
    public List<Label> searchPostulantsByStatus(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        String searchTerm2 = "";
        String idAnnoun = body.get("idannouncement");
        String[] currencies = searchTerm.split(" ");
        if (currencies.length > 1) {
            searchTerm = currencies[0];
            searchTerm2 = currencies[1];
            List<Label> combo = new ArrayList<>();
            combo.addAll(labelService.searcherByTextAnnounAndStatusTrue(searchTerm, searchTerm, idAnnoun));
            combo.addAll(labelService.searcherByTextAnnounAndStatusTrue(searchTerm2, searchTerm2, idAnnoun));
            LinkedHashSet<Label> hashSet = new LinkedHashSet<>(combo);
            ArrayList<Label> listWithoutDuplicates = new ArrayList<>(hashSet);
            return listWithoutDuplicates;
        }

        return labelService.searcherByTextAnnounAndStatusTrue(searchTerm, searchTerm, idAnnoun);
    }

    @PostMapping("/label/searchByText/Announcement")
    public List<Label> searchPostulantsByAnnouncement(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        String searchTerm2 = "";
        String idAnnoun = body.get("idannouncement");
        String[] currencies = searchTerm.split(" ");
        if (currencies.length > 1) {
            searchTerm = currencies[0];
            searchTerm2 = currencies[1];
            List<Label> combo = new ArrayList<>();
            combo.addAll(labelService.searcherByTextAnnoun(searchTerm, searchTerm, idAnnoun));
            combo.addAll(labelService.searcherByTextAnnoun(searchTerm2, searchTerm2, idAnnoun));
            LinkedHashSet<Label> hashSet = new LinkedHashSet<>(combo);
            ArrayList<Label> listWithoutDuplicates = new ArrayList<>(hashSet);
            return listWithoutDuplicates;
        }
        
        return labelService.searcherByTextAnnoun(searchTerm, searchTerm, idAnnoun);
    }

    @RequestMapping("/label/person/{id}")
    public List<Label> listByIdPerson(@PathVariable("id") long id){
        return labelService.listLabelByIdPerson(id);
    }
}
