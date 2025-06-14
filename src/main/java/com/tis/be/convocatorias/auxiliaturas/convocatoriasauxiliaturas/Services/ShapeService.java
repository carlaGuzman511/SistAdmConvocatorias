package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Shape;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShapeService {



    @Autowired
    private ShapeRepository shapeRepository;


    public List<Shape> getAllShape() {
        return shapeRepository.findAll();
    }


    public Shape getShape(long id) {
        return shapeRepository.FindById(id)
                .orElse(null);

    }

    public long addShape(Shape shape) {
        shapeRepository.save(shape);
        return shapeRepository.selectByIdshape();
    }

    public void deleteShape(long id) {
        shapeRepository.DeleteByCi(id);
    }


    public List<Shape> listadoByAnnouncement(long id){
        return shapeRepository.findShapesByAnnouncement_Idannouncement(id);
    }

    public long getLastById(){
        return shapeRepository.selectByIdshape();
    }

}
