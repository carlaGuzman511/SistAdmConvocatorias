package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput.SubdetailmeritcomplexInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.SubdetailmeritInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.SubdetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.SubdetailMeritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SubdetailMeritController {

    @Autowired
    private SubdetailMeritService subdetailMeritService;
    @Autowired
    private SubdetailMeritRepository subdetailMeritRepository;
    @Autowired
    private DetailMeritRepository detailMeritRepository;
    @Autowired
    private SubsubdetailMeritController subsubdetailMeritController;

    @RequestMapping("/subdetailmerit")
    public List<SubdetailMerit> getAllSubdetailMerit() {
        return subdetailMeritService.getAllSubdetailMerit();
    }

    @RequestMapping("/subdetailmerit/{id}")
    public SubdetailMerit getSubdetailMerit(@PathVariable long id) {
        return subdetailMeritService.getSubdetailMerit(id);
    }

    @PostMapping("/subdetailmerit")
    public ResponseEntity<Long> addSubdetailMerit(@RequestBody SubdetailmeritInput subdetailmeritInput) {
        DetailMerit detailMerit = detailMeritRepository.getById(subdetailmeritInput.getDetailmerit());
        if (!subdetailMeritRepository.existsSubdetailMeritByDescriptionAndPercentageAndDetailMerit_Iddetailmerit(
                subdetailmeritInput.getDescription(), subdetailmeritInput.getPercentage(), subdetailmeritInput.getDetailmerit())) {
            SubdetailMerit subdetailMerit = new SubdetailMerit();
            subdetailMerit.setIdsubdetailmerit(999999);
            subdetailMerit.setDescription(subdetailmeritInput.getDescription());
            subdetailMerit.setPercentage(subdetailmeritInput.getPercentage());
            subdetailMerit.setDetailMerit(detailMerit);
            subdetailMeritService.addSubdetailMerit(subdetailMerit);
            return ResponseEntity.ok().body(subdetailMeritService.getlastIdSubdetailMerit());

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/subdetailmerit/complex")
    public ResponseEntity<Long> addComplexSubdetailMerit(@RequestBody SubdetailmeritcomplexInput subdetailmeritcomplexInput, long id) {
        DetailMerit detailMerit = detailMeritRepository.getById(id);
        if (!subdetailMeritRepository.existsSubdetailMeritByDescriptionAndPercentageAndDetailMerit_Iddetailmerit(
                subdetailmeritcomplexInput.getDetail(), subdetailmeritcomplexInput.getPercent(), id)) {
            SubdetailMerit subdetailMerit = new SubdetailMerit();
            subdetailMerit.setIdsubdetailmerit(999999);
            subdetailMerit.setDescription(subdetailmeritcomplexInput.getDetail());
            subdetailMerit.setPercentage(subdetailmeritcomplexInput.getPercent());
            subdetailMerit.setDetailMerit(detailMerit);
            subdetailMeritService.addSubdetailMerit(subdetailMerit);
            for (int k = 0; k < subdetailmeritcomplexInput.getSubdetails().size(); k++) {
                subsubdetailMeritController.addComplexSubsubdetailMerit(subdetailmeritcomplexInput.getSubdetails().get(k), subdetailMeritService.getlastIdSubdetailMerit());
            }
            return ResponseEntity.ok().body(subdetailMeritService.getlastIdSubdetailMerit());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/subdetailmerit/{id}")
    public void deleteSubdetailMerit(@PathVariable long id) {
        subdetailMeritService.deleteSubdetailMerit(id);
    }

    @RequestMapping("/subdetailmerit/detailmerit/{id}")
    public List<SubdetailMerit> listByDetailmerit(@PathVariable("id") long id) {
        return subdetailMeritService.listByDetailmerit(id);
    }

}
