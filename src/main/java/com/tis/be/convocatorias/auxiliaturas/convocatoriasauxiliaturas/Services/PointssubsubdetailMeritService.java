package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.PointssubsubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PointssubsubdetailMeritRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointssubsubdetailMeritService {

    @Autowired
    private PointssubsubdetailMeritRepository pointssubsubdetailMeritRepository;


    public List<PointssubsubdetailMerit> getAllPointssubsubdetailMerit() {
        return pointssubsubdetailMeritRepository.findAll();
    }


    public PointssubsubdetailMerit getPointssubsubdetailMerit(long id) {
        return pointssubsubdetailMeritRepository.FindById(id)
                .orElse(null);

    }

    public void addPointssubsubdetailMerit(PointssubsubdetailMerit pointssubsubdetailMerit) {
        pointssubsubdetailMeritRepository.save(pointssubsubdetailMerit);
    }

    public long getlastIdPointssubsubdetailMerit(){
        return pointssubsubdetailMeritRepository.selectByIdpointssubsubdetailmerit();
    }


    public void deletePointssubsubdetailMerit(long id) {
        pointssubsubdetailMeritRepository.DeleteByCi(id);
    }


    public List<PointssubsubdetailMerit> listBySubsubdetailmerit(long id){
        return pointssubsubdetailMeritRepository.listByIdUp(id);
    }

}
