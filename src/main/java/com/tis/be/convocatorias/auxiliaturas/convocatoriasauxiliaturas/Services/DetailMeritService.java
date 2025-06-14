package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DetailMeritRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailMeritService {

    @Autowired
    private DetailMeritRepository detailMeritRepository;


    public List<DetailMerit> getAllDetailMerit() {
        return detailMeritRepository.findAll();
    }


    public DetailMerit getDetailMerit(long id) {
        return detailMeritRepository.FindById(id)
                .orElse(null);

    }

    public void addDetailMerit(DetailMerit detailMerit) {
        detailMeritRepository.save(detailMerit);
    }

    public long getlastIdDetailMerit(){

        return detailMeritRepository.selectByIddetailmerit();
    }


    public void deleteDetailMerit(long id) {
        detailMeritRepository.DeleteByCi(id);
    }


    public List<DetailMerit> listByMerit(long id){
        return detailMeritRepository.listByUp(id);
    }

}
