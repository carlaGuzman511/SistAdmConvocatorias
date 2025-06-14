package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput.SubsubdetailmeritcomplexInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.SubsubdetailmeritInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubsubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.SubdetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.SubsubdetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.SubsubdetailMeritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SubsubdetailMeritController {

    @Autowired
    private SubsubdetailMeritService subsubdetailMeritService;
    @Autowired
    private SubsubdetailMeritRepository subsubdetailMeritRepository;
    @Autowired
    private SubdetailMeritRepository subdetailMeritRepository;
    @Autowired
    private PointssubsubdetailMeritController pointssubsubdetailMeritController;

    @RequestMapping("/subsubdetailmerit")
    public List<SubsubdetailMerit> getAllSubsubdetailMerit() {
        return subsubdetailMeritService.getAllSubsubdetailMerit();
    }

    @RequestMapping("/subsubdetailmerit/{id}")
    public SubsubdetailMerit getSubsubdetailMerit(@PathVariable long id) {
        return subsubdetailMeritService.getSubsubdetailMerit(id);
    }

    @PostMapping("/subsubdetailmerit")
    public ResponseEntity<Long> addSubsubdetailMerit(@RequestBody SubsubdetailmeritInput subsubdetailmeritInput) {
        SubdetailMerit subdetailMerit = subdetailMeritRepository.getById(subsubdetailmeritInput.getSubdetailmerit());
        if (!subsubdetailMeritRepository.existsSubsubdetailMeritByDescriptionAndPercentageAndSubdetailMerit_Idsubdetailmerit(
                subsubdetailmeritInput.getDescription(), subsubdetailmeritInput.getPercentage(), subsubdetailmeritInput.getSubdetailmerit())) {
            SubsubdetailMerit subsubdetailMerit = new SubsubdetailMerit();
            subsubdetailMerit.setIdsubsubdetailmerit(999999);
            subsubdetailMerit.setDescription(subsubdetailmeritInput.getDescription());
            subsubdetailMerit.setPercentage(subsubdetailmeritInput.getPercentage());
            subsubdetailMerit.setSubdetailMerit(subdetailMerit);
            subsubdetailMeritService.addSubsubdetailMerit(subsubdetailMerit);
            return ResponseEntity.ok().body(subsubdetailMeritService.getlastIdSubsubdetailMerit());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/subsubdetailmerit/complex")
    public ResponseEntity<Long> addComplexSubsubdetailMerit(@RequestBody SubsubdetailmeritcomplexInput subsubdetailmeritcomplexInput, long id) {
        SubdetailMerit subdetailMerit = subdetailMeritRepository.getById(id);
        if (!subsubdetailMeritRepository.existsSubsubdetailMeritByDescriptionAndPercentageAndSubdetailMerit_Idsubdetailmerit(
                subsubdetailmeritcomplexInput.getSubdetail(), subsubdetailmeritcomplexInput.getPercent(), id)) {
            SubsubdetailMerit subsubdetailMerit = new SubsubdetailMerit();
            subsubdetailMerit.setIdsubsubdetailmerit(999999);
            subsubdetailMerit.setDescription(subsubdetailmeritcomplexInput.getSubdetail());
            subsubdetailMerit.setPercentage(subsubdetailmeritcomplexInput.getPercent());
            subsubdetailMerit.setSubdetailMerit(subdetailMerit);
            subsubdetailMeritService.addSubsubdetailMerit(subsubdetailMerit);
            for (int l = 0; l < subsubdetailmeritcomplexInput.getPointDetail().size(); l++) {
                pointssubsubdetailMeritController.addComplexPointssubsubdetailMerit(subsubdetailmeritcomplexInput.getPointDetail().get(l), subsubdetailMeritService.getlastIdSubsubdetailMerit());
            }
            return ResponseEntity.ok().body(subsubdetailMeritService.getlastIdSubsubdetailMerit());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    @DeleteMapping("/subsubdetailmerit/{id}")
    public void deleteSubsubdetailMerit(@PathVariable long id) {
        subsubdetailMeritService.deleteSubsubdetailMerit(id);
    }

    @RequestMapping("/subsubdetailmerit/subdetailmerit/{id}")
    public List<SubsubdetailMerit> ListBySubdetailmerit(@PathVariable("id") long id) {
        return subsubdetailMeritService.ListBySubdetailmerit(id);
    }
}
