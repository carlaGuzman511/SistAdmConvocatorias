package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ThematicInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ThematicRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ThematicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ThematicController {

    @Autowired
    private ThematicService thematicService;
    @Autowired
    private ThematicRepository thematicRepository;

    @RequestMapping("/thematic")
    public List<Thematic> getAllThematic() {
        return thematicService.getAllThematic();
    }

    @RequestMapping("/thematic/{id}")
    public Thematic getThematic(@PathVariable int id) {
        return thematicService.getThematic(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/thematic")
    public ResponseEntity<Long> addThematic(@RequestBody ThematicInput thematicInput) {
        if (!thematicRepository.existsThematicByName(thematicInput.getName())) {
            Thematic thematic = new Thematic();
            thematic.setIdthematic(999999);
            thematic.setName(thematicInput.getName());

            return ResponseEntity.ok().body(thematicService.addThematic(thematic));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/thematic/{id}")
    public void deleteThematic(@PathVariable int id) {
        thematicService.deleteThematic(id);
    }

    @RequestMapping("/thematic/announcement/{id}")
    public List<Thematic> listByIdAnnouncement(@PathVariable("id") long id) {
        return thematicService.listByIdAnnouncement(id);
    }

}
