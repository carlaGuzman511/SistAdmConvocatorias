package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DetailShapeCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.DetailShapeInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DetailShapeRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ShapeRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.DetailShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DetailShapeController {

    @Autowired
    private DetailShapeService detailShapeService;
    @Autowired
    private DetailShapeRepository detailShapeRepository;
    @Autowired
    private ShapeRepository shapeRepository;


    @RequestMapping("/detailshape")
    public List<DetailShape> getAllDetailShape() {
        return detailShapeService.getAllDetailShape();
    }

    @RequestMapping("/detailshape/{id}")
    public DetailShape getDetailShape(@PathVariable long id) {
        return detailShapeService.getDetailShape(id);
    }

    @PostMapping("/detailshape")
    public ResponseEntity<Long> addDetailShape(@RequestBody DetailShapeInput detailShapeInput) {
        Shape shape = shapeRepository.getById(detailShapeInput.getShape());
        if (!detailShapeRepository.existsDetailShapeByDescriptionAndShape_Idshape(detailShapeInput.getDescription(), detailShapeInput.getShape())) {
            DetailShape detailShape = new DetailShape();
            detailShape.setIddetailshape(999999);
            detailShape.setDescription(detailShapeInput.getDescription());
            detailShape.setShape(shape);

            return ResponseEntity.ok().body(detailShapeService.addDetailShape(detailShape));
        }else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    public ResponseEntity<Long> addDetailShapeCreate(@RequestBody DetailShapeCreateInput detailShapeCreateInput, long id){
        Shape shape = shapeRepository.getById(id);
        if (!detailShapeRepository.existsDetailShapeByDescriptionAndShape_Idshape(detailShapeCreateInput.getShapex(), id)) {
        DetailShape detailShape = new DetailShape();
        detailShape.setIddetailshape(999999);
        detailShape.setDescription(detailShapeCreateInput.getShapex());
        detailShape.setShape(shape);

        return ResponseEntity.ok().body(detailShapeService.addDetailShape(detailShape));
    }else {
        long idrepetitivo = -2;
        return ResponseEntity.badRequest().body(idrepetitivo);
    }
    }

    @DeleteMapping("/detailshape/{id}")
    public void deleteDetailShape(@PathVariable long id) {
        detailShapeService.deleteDetailShape(id);
    }

    @RequestMapping("/detailshape/shape/{id}")
    public List<DetailShape> ListByShape(@PathVariable("id") long id){
        return detailShapeService.ListByShape(id);
    }

}
