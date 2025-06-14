package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.ShapeCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.ShapeInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.ShapeCreateInput.ShapeWhitDetailCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Shape;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ShapeRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ShapeController {

    @Autowired
    private ShapeService shapeService;
    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private DetailShapeController detailShapeController;

    @RequestMapping("/shape")
    public List<Shape> getAllShape() {
        return shapeService.getAllShape();
    }

    @RequestMapping("/shape/{id}")
    public Shape getShape(@PathVariable long id) {
        return shapeService.getShape(id);
    }

    @PostMapping("/shape")
    public ResponseEntity<Long> addShape(@RequestBody ShapeInput shapeInput) {
        Announcement announcement = announcementRepository.getById(shapeInput.getAnnouncement());
        if (!shapeRepository.existsShapeByAnnouncement_IdannouncementAndDescription(shapeInput.getAnnouncement(), shapeInput.getDescription())) {
            Shape shape = new Shape();
            shape.setIdshape(999999);
            shape.setDescription(shapeInput.getDescription());
            shape.setAnnouncement(announcement);

            return ResponseEntity.ok().body(shapeService.addShape(shape));

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    public ResponseEntity<Long> addShapeCreate(@RequestBody ShapeCreateInput shapeCreateInput, long id) {
        Announcement announcement = announcementRepository.getById(id);
        if (!shapeRepository.existsShapeByAnnouncement_IdannouncementAndDescription(id, shapeCreateInput.getDescription())) {
            Shape shape = new Shape();
            shape.setIdshape(999999);
            shape.setDescription(shapeCreateInput.getDescription());
            shape.setAnnouncement(announcement);
            shapeService.addShape(shape);
            for (int i = 0; i < shapeCreateInput.getShapes().size(); i++) {
                detailShapeController.addDetailShapeCreate(
                        shapeCreateInput.getShapes().get(i),
                        shapeService.getLastById()
                );
            }
            return ResponseEntity.ok().body(shapeService.getLastById());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/shape/create")
    public ResponseEntity<Long> addShapeCreateSingle(@RequestBody ShapeWhitDetailCreateInput shapeWhitDetailCreateInput) {
        Announcement announcement = announcementRepository.getById(shapeWhitDetailCreateInput.getAnnouncement());
        if (!shapeRepository.existsShapeByAnnouncement_IdannouncementAndDescription(shapeWhitDetailCreateInput.getAnnouncement(), shapeWhitDetailCreateInput.getDescription())) {
        Shape shape = new Shape();
        shape.setIdshape(999999);
        shape.setDescription(shapeWhitDetailCreateInput.getDescription());
        shape.setAnnouncement(announcement);
        shapeService.addShape(shape);
        for (int i = 0; i < shapeWhitDetailCreateInput.getShapes().size(); i++) {
            detailShapeController.addDetailShapeCreate(
                    shapeWhitDetailCreateInput.getShapes().get(i),
                    shapeService.getLastById()
            );
        }
        return ResponseEntity.ok().body(shapeService.getLastById());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/shape/{id}")
    public void deleteShape(@PathVariable long id) {
        shapeService.deleteShape(id);
    }

    @RequestMapping("/shape/announcement/{id}")
    public List<Shape> listadoByAnnouncement(@PathVariable("id") long id) {
        return shapeService.listadoByAnnouncement(id);
    }
}
