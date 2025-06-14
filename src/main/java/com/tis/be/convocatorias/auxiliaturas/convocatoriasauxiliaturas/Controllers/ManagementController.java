package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Management;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ManagementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ManagementController {

    @Autowired
    private ManagementService managementService;
    @Autowired
    private ManagementRepository managementRepository;

    @RequestMapping("/management")
    public List<Management> getAllManagement() {
        return managementService.getAllManagements();
    }

    @RequestMapping("/management/{id}")
    public Management getManagement(@PathVariable int id) {
        return managementService.getManagement(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/management")
    public ResponseEntity<Long> addManagement(@RequestBody Management management) {
        if (!managementRepository.existsByPeriod(management.getPeriod())){
        managementService.addManagement(management);
        return ResponseEntity.ok().body(management.getIdmanagement());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/management/{id}")
    public void deleteManagement(@PathVariable int id) {
        managementService.deleteManagement(id);
    }

}
