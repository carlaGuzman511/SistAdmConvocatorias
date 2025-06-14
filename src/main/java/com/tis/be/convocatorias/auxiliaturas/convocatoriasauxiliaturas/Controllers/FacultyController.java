package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Faculty;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.FacultyRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class FacultyController {

    @Autowired
    private FacultyService facultyService;
    @Autowired
    private FacultyRepository facultyRepository;

    @RequestMapping("/faculty")
    public List<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    @RequestMapping("/faculty/{id}")
    public Faculty getFaculty(@PathVariable long id) {
        return facultyService.getFaculty(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/faculty")
    public ResponseEntity<Long> addFaculty(@RequestBody Faculty faculty) {
        if (!facultyRepository.existsFacultyByName(faculty.getName())){
        facultyService.addFaculty(faculty);
        return ResponseEntity.ok().body(faculty.getIdfaculty());
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }
}
