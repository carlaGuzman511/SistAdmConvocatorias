package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Career;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.CareerRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CareerController {

    @Autowired
    private CareerService careerService;
    @Autowired
    private CareerRepository careerRepository;

    @RequestMapping("/career")
    public List<Career> getAllCareer() {
        return careerService.getAllCareer();
    }

    @RequestMapping("/career/{id}")
    public Career getCareer(@PathVariable int id) {
        return careerService.getCareer(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/career")
    public ResponseEntity<Long> addCareer(@RequestBody Career career) {
        if (!careerRepository.existsCareerByNameAndCodCareer(career.getName(), career.getCodCareer())) {
            careerService.addCareer(career);
            return ResponseEntity.ok().body(career.getIdcareer());
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/career/{id}")
    public void updateCareer(@RequestBody Career career, @PathVariable int id) {
        careerService.updateCareer(id, career);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/career/{id}")
    public void deleteCareer(@PathVariable int id) {
        careerService.deleteCareer(id);
    }
}
