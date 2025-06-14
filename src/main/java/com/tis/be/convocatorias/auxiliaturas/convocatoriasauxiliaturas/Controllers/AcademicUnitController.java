package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByAcademicUnitByFacManInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.AcademicUnitInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AcademicUnit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Faculty;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AcademicUnitRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.FacultyRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.AcademicUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AcademicUnitController {

    @Autowired
    private AcademicUnitService academicUnitService;
    @Autowired
    private AcademicUnitRepository academicUnitRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @RequestMapping("/academicunit")
    public List<AcademicUnit> getAllAcademicUnits() {
        return academicUnitService.getAllAcademicUnits();
    }

    @RequestMapping("/academicunit/{id}")
    public AcademicUnit getAcademicUnit(@PathVariable int id) {
        return academicUnitService.getAcademicUnit(id);
    }

    @PostMapping("/academicunit/list/MF")
    public List<AcademicUnit> listByManagementFaculty(@RequestBody ListByAcademicUnitByFacManInput listByAcademicUnitByFacManInput){
        return academicUnitService.listByManagementFaculty(
                listByAcademicUnitByFacManInput.getIdfaculty(),
                listByAcademicUnitByFacManInput.getIdmanagement()
        );
    }

    @RequestMapping(method = RequestMethod.POST, value = "/academicunit")
    public ResponseEntity<Long> addAcademicUnit(@RequestBody AcademicUnitInput academicUnitInput) {
        Faculty faculty = facultyRepository.getById(academicUnitInput.getFaculty());
        if (!academicUnitRepository.existsAcademicUnitByNameAndFaculty_Idfaculty(academicUnitInput.getName(), faculty.getIdfaculty())) {
            AcademicUnit academicUnit = new AcademicUnit();
            academicUnit.setIdacademicunit(999999);
            academicUnit.setName(academicUnitInput.getName());
            academicUnit.setFaculty(faculty);
            academicUnitService.addAcademicUnit(academicUnit);

            return ResponseEntity.status(HttpStatus.OK).body(academicUnitService.addAcademicUnit(academicUnit));
        } else {
            long idauxiliar = -2;
            return ResponseEntity.badRequest().body(idauxiliar);
        }

    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/academicunit/{id}")
    public void deleteAcademicUnit(@PathVariable int id) {
        academicUnitService.deleteAcademicUnit(id);
    }
}
