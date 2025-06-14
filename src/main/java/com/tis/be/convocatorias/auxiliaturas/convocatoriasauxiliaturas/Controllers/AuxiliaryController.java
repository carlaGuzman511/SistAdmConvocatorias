package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.AuxiliaryInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByAreaAcademicUnitInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AcademicUnit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Area;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Auxiliary;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AcademicUnitRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AreaRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.AuxiliaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class AuxiliaryController {

    @Autowired
    private AuxiliaryService auxiliaryService;
    @Autowired
    private AuxiliaryRepository auxiliaryRepository;
    @Autowired
    private AcademicUnitRepository academicUnitRepository;
    @Autowired
    private AreaRepository areaRepository;

    @RequestMapping("/auxiliary")
    public List<Auxiliary> getAllAuxiliary() {
        return auxiliaryService.getAllAuxiliary();
    }

    @RequestMapping("/auxiliary/{id}")
    public Auxiliary getAuxiliary(@PathVariable long id) {
        return auxiliaryService.getAuxiliary(id);
    }

    @RequestMapping("/auxiliary/person/{id}")
    public List<Auxiliary> getAuxiliaryByIdperson(@PathVariable long id) {
        return auxiliaryService.getAuxiliaryByIdperson(id);
    }

    @PostMapping("/auxiliary")
    public ResponseEntity<Long> addAuxiliary(@RequestBody AuxiliaryInput auxiliaryInput) {
        Area area = areaRepository.getById(auxiliaryInput.getArea());
        AcademicUnit academicUnit = academicUnitRepository.getById(auxiliaryInput.getAcademinUnit());
        if (!auxiliaryRepository.existsAuxiliaryByAcademicUnit_IdacademicunitAndArea_IdareaAndCodeAndName(academicUnit.getIdacademicunit(), area.getIdarea(), auxiliaryInput.getCode(), auxiliaryInput.getName())) {
            Auxiliary auxiliary = new Auxiliary();
            auxiliary.setIdauxiliary(999999);
            auxiliary.setName(auxiliaryInput.getName());
            auxiliary.setCode(auxiliaryInput.getCode());
            auxiliary.setAcademicHours(auxiliaryInput.getAcademicHours());
            auxiliary.setArea(area);
            auxiliary.setAcademicUnit(academicUnit);

            return ResponseEntity.ok().body(auxiliaryService.addAuxiliary(auxiliary));
        }else{
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/auxiliary/{id}")
    public void deleteAuxiliary(@PathVariable long id) {
        auxiliaryService.deleteAuxiliary(id);
    }

    @RequestMapping("/auxiliary/thematic/{id}")
    public List<Thematic> listThematicByIdAuxiliary(@PathVariable("id") long id) {
        return auxiliaryService.listThematicByIdAuxiliary(id);
    }

    @PostMapping("/auxiliary/AAU")
    public List<Auxiliary> listAuxiliarysByAreaAcademicUnit(@RequestBody ListByAreaAcademicUnitInput listByAreaAcademicUnitInput){
        return auxiliaryService.listAuxiliarysByIdAreaAcademicUnit(
                listByAreaAcademicUnitInput.getIdarea(),
                listByAreaAcademicUnitInput.getIdacademicunit()
        );
    }

    @RequestMapping("/auxiliary/teaching")
    public List<Auxiliary> listAuxiliarysByTeaching(){

        return auxiliaryService.listAuxiliarysByTeaching("Docencia");
    }

}
