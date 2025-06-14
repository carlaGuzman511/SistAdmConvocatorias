package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailShape;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DetailShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailShapeService {

    @Autowired
    private DetailShapeRepository detailShapeRepository;


    public List<DetailShape> getAllDetailShape() {
        return detailShapeRepository.findAll();
    }


    public DetailShape getDetailShape(long id) {
        return detailShapeRepository.FindById(id)
                .orElse(null);

    }

    public long addDetailShape(DetailShape detailShape) {
        detailShapeRepository.save(detailShape);
        return detailShapeRepository.selectByIdDetailShape();
    }

    public void deleteDetailShape(long id) {
        detailShapeRepository.DeleteByCi(id);
    }

    public List<DetailShape> ListByShape(long id){
        return detailShapeRepository.findDetailShapesByShape_Idshape(id);
    }


}
