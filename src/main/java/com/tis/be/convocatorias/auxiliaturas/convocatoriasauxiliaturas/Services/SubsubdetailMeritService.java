package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubsubdetailMerit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.SubsubdetailMeritRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubsubdetailMeritService {

    @Autowired
    private SubsubdetailMeritRepository subsubdetailMeritRepository;


    public List<SubsubdetailMerit> getAllSubsubdetailMerit() {
        return subsubdetailMeritRepository.findAll();
    }


    public SubsubdetailMerit getSubsubdetailMerit(long id) {
        return subsubdetailMeritRepository.FindById(id)
                .orElse(null);

    }

    public void addSubsubdetailMerit(SubsubdetailMerit subsubdetailMerit) {
        subsubdetailMeritRepository.save(subsubdetailMerit);
    }

    public long getlastIdSubsubdetailMerit(){
        return subsubdetailMeritRepository.selectByIdsubsubdetailmerit();
    }


    public void deleteSubsubdetailMerit(long id) {
        subsubdetailMeritRepository.DeleteByCi(id);
    }


    public List<SubsubdetailMerit> ListBySubdetailmerit(long id){
        return subsubdetailMeritRepository.listByUp(id);
    }
}
