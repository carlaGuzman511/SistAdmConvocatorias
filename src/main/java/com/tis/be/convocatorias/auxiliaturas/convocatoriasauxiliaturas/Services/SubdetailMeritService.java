package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.SubdetailMeritRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubdetailMeritService {

    @Autowired
    private SubdetailMeritRepository subdetailMeritRepository;


    public List<SubdetailMerit> getAllSubdetailMerit() {
        return subdetailMeritRepository.findAll();
    }


    public SubdetailMerit getSubdetailMerit(long id) {
        return subdetailMeritRepository.FindById(id)
                .orElse(null);

    }

    public void addSubdetailMerit(SubdetailMerit subdetailMerit) {
        subdetailMeritRepository.save(subdetailMerit);
    }

    public long getlastIdSubdetailMerit(){
        return subdetailMeritRepository.selectByIdsubdetailmerit();
    }


    public void deleteSubdetailMerit(long id) {
        subdetailMeritRepository.DeleteByCi(id);
    }


    public List<SubdetailMerit> listByDetailmerit(long id){
        return subdetailMeritRepository.listByUp(id);
    }

}
