package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput.PointssubsubdetailmeritcomplexInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.PointssubsubdetailmeritInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.PointssubsubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubsubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PointssubsubdetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.SubsubdetailMeritRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.PointssubsubdetailMeritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PointssubsubdetailMeritController {

    @Autowired
    private PointssubsubdetailMeritService pointssubsubdetailMeritService;
    @Autowired
    private PointssubsubdetailMeritRepository pointssubsubdetailMeritRepository;
    @Autowired
    private SubsubdetailMeritRepository subsubdetailMeritRepository;

    @RequestMapping("/pointssubsubdetailmerit")
    public List<PointssubsubdetailMerit> getAllPointssubsubdetailMerit() {
        return pointssubsubdetailMeritService.getAllPointssubsubdetailMerit();
    }

    @RequestMapping("/pointssubsubdetailmerit/{id}")
    public PointssubsubdetailMerit getPointssubsubdetailMerit(@PathVariable long id) {
        return pointssubsubdetailMeritService.getPointssubsubdetailMerit(id);
    }

    @PostMapping("/pointssubsubdetailmerit")
    public ResponseEntity<Long> addPointssubsubdetailMerit(@RequestBody PointssubsubdetailmeritInput pointssubsubdetailmeritInput) {
        SubsubdetailMerit subsubdetailMerit = subsubdetailMeritRepository.getById(pointssubsubdetailmeritInput.getSubsubdetailmerit());
        if (!pointssubsubdetailMeritRepository.existsPointssubsubdetailMeritByDescriptionAndPointsAndSubsubdetailMerit_Idsubsubdetailmerit(
                pointssubsubdetailmeritInput.getDescription(), pointssubsubdetailmeritInput.getPoints(), pointssubsubdetailmeritInput.getSubsubdetailmerit())) {
            PointssubsubdetailMerit pointssubsubdetailMerit = new PointssubsubdetailMerit();
            pointssubsubdetailMerit.setIdpointssubsubdetailmerit(999999);
            pointssubsubdetailMerit.setDescription(pointssubsubdetailmeritInput.getDescription());
            pointssubsubdetailMerit.setPoints(pointssubsubdetailmeritInput.getPoints());
            pointssubsubdetailMerit.setSubsubdetailMerit(subsubdetailMerit);
            pointssubsubdetailMeritService.addPointssubsubdetailMerit(pointssubsubdetailMerit);

            return ResponseEntity.ok().body(pointssubsubdetailMeritService.getlastIdPointssubsubdetailMerit());

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }

    }

    @PostMapping("/pointssubsubdetailmerit/complex")
    public ResponseEntity<Long> addComplexPointssubsubdetailMerit(@RequestBody PointssubsubdetailmeritcomplexInput pointssubsubdetailmeritcomplexInput, long id) {
        SubsubdetailMerit subsubdetailMerit = subsubdetailMeritRepository.getById(id);
        if (!pointssubsubdetailMeritRepository.existsPointssubsubdetailMeritByDescriptionAndPointsAndSubsubdetailMerit_Idsubsubdetailmerit(
                pointssubsubdetailmeritcomplexInput.getDescription(), pointssubsubdetailmeritcomplexInput.getPoints(), id)) {
        PointssubsubdetailMerit pointssubsubdetailMerit = new PointssubsubdetailMerit();
        pointssubsubdetailMerit.setIdpointssubsubdetailmerit(999999);
        pointssubsubdetailMerit.setDescription(pointssubsubdetailmeritcomplexInput.getDescription());
        pointssubsubdetailMerit.setPoints(pointssubsubdetailmeritcomplexInput.getPoints());
        pointssubsubdetailMerit.setSubsubdetailMerit(subsubdetailMerit);
        pointssubsubdetailMeritService.addPointssubsubdetailMerit(pointssubsubdetailMerit);

        return ResponseEntity.ok().body(pointssubsubdetailMeritService.getlastIdPointssubsubdetailMerit());

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/pointssubsubdetailmerit/{id}")
    public void deletePointssubsubdetailMerit(@PathVariable long id) {
        pointssubsubdetailMeritService.deletePointssubsubdetailMerit(id);
    }

    @RequestMapping("/pointssubsubdetailmerit/subsubdetailmerit/{id}")
    public List<PointssubsubdetailMerit> ListBySubsubdetailmerit(@PathVariable("id") long id) {
        return pointssubsubdetailMeritService.listBySubsubdetailmerit(id);
    }


}
