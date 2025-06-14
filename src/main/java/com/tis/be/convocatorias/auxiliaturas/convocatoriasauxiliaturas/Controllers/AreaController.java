package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Area;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AreaRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AreaController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private AreaRepository areaRepository;

    @RequestMapping("/area")
    public List<Area> getAllAreas() {
        return areaService.getAllAreas();
    }

    @RequestMapping("/area/{id}")
    public Area getArea(@PathVariable int id) {
        return areaService.getArea(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/area")
    public ResponseEntity<Long> addArea(@RequestBody Area area) {
        if (!areaRepository.existsAreaByName(area.getName())){
        areaService.addArea(area);
        return ResponseEntity.ok().body(area.getIdarea());
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/area/{id}")
    public void deleteArea(@PathVariable int id) {
        areaService.deleteArea(id);
    }
}
