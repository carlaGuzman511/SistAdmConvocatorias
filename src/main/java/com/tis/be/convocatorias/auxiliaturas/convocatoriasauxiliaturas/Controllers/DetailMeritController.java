package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.DetailMeritInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput.DetailmeritcomplexInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Merit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.MeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.DetailMeritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class DetailMeritController {

    @Autowired
    private DetailMeritService detailMeritService;
    @Autowired
    private DetailMeritRepository detailMeritRepository;
    @Autowired
    private MeritRepository meritRepository;
    @Autowired
    private SubdetailMeritController subdetailMeritController;

    @RequestMapping("/detailmerit")
    public List<DetailMerit> getAllDetailMerit() {
        return detailMeritService.getAllDetailMerit();
    }

    @RequestMapping("/detailmerit/{id}")
    public DetailMerit getDetailMerit(@PathVariable long id) {
        return detailMeritService.getDetailMerit(id);
    }

    @PostMapping("/detailmerit")
    public ResponseEntity<Long> addDetailMerit(@RequestBody DetailMeritInput detailMeritInput) {
        Merit merit = meritRepository.getById(detailMeritInput.getMerit());
        if (!detailMeritRepository.existsDetailMeritByDescriptionAndPercentageAndMerit_Idmerit(detailMeritInput.getDescription(), detailMeritInput.getPercentage(), detailMeritInput.getMerit())) {
            DetailMerit detailMerit = new DetailMerit();
            detailMerit.setIddetailmerit(999999);
            detailMerit.setDescription(detailMeritInput.getDescription());
            detailMerit.setPercentage(detailMeritInput.getPercentage());
            detailMerit.setMerit(merit);
            detailMeritService.addDetailMerit(detailMerit);
            return ResponseEntity.ok().body(detailMeritService.getlastIdDetailMerit());
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/detailmerit/complex")
    public ResponseEntity<Long>addComplexDetailMerit(@RequestBody DetailmeritcomplexInput detailmeritcomplexInput, long id) {
        Merit merit = meritRepository.getById(id);
        if (!detailMeritRepository.existsDetailMeritByDescriptionAndPercentageAndMerit_Idmerit(detailmeritcomplexInput.getName(), detailmeritcomplexInput.getPercentage(),id)) {
        DetailMerit detailMerit = new DetailMerit();
        detailMerit.setIddetailmerit(999999);
        detailMerit.setDescription(detailmeritcomplexInput.getMerit());
        detailMerit.setPercentage(detailmeritcomplexInput.getPercentage());
        detailMerit.setMerit(merit);
        detailMeritService.addDetailMerit(detailMerit);
        for (int j=0; j< detailmeritcomplexInput.getDetails().size(); j++){
            subdetailMeritController.addComplexSubdetailMerit(detailmeritcomplexInput.getDetails().get(j),detailMeritService.getlastIdDetailMerit());
        }
        return ResponseEntity.ok().body(detailMeritService.getlastIdDetailMerit());
    }else {
        long idrepetitivo = -2;
        return ResponseEntity.badRequest().body(idrepetitivo);
    }
    }

    @DeleteMapping("/detailmerit/{id}")
    public void deleteDetailMerit(@PathVariable long id) {
        detailMeritService.deleteDetailMerit(id);
    }

    @RequestMapping("/detailmerit/merit/{id}")
    public List<DetailMerit> listByMerit(@PathVariable("id") long id) {
        return detailMeritService.listByMerit(id);
    }

}
