package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Merit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.MeritRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeritService {

    @Autowired
    private MeritRepository meritRepository;


    public List<Merit> getAllMerit() {
        return meritRepository.findAll();
    }


    public Merit getMerit(long id) {
        return meritRepository.FindById(id)
                .orElse(null);

    }

    public void addMerit(Merit merit) {
        meritRepository.save(merit);
    }
    public long getlastIdMerit(){

        return meritRepository.selectByIdmerit();
    }


    public void deleteMerit(long id) {
        meritRepository.DeleteByCi(id);
    }


    public List<Merit> listByAnnouncement(long id){
        return meritRepository.listByUp(id);
    }

}
