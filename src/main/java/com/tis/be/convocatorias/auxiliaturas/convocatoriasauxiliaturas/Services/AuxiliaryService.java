package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Auxiliary;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AuxiliaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuxiliaryService {

    @Autowired
    private AuxiliaryRepository auxiliaryRepository;


    public List<Auxiliary> getAllAuxiliary() {
        return auxiliaryRepository.findAll();
    }


    public Auxiliary getAuxiliary(long id) {
        return auxiliaryRepository.FindById(id)
                .orElse(null);

    }

    public List<Auxiliary> getAuxiliaryByIdperson(long id) {
        return auxiliaryRepository.getAuxByPerson(id);
    }

    public long addAuxiliary(Auxiliary auxiliary) {
        auxiliaryRepository.save(auxiliary);
        return auxiliaryRepository.selectByIdauxiliary();
    }


    public void deleteAuxiliary(long id) {
        auxiliaryRepository.DeleteByCi(id);
    }


    public List<Thematic> listThematicByIdAuxiliary(long id){
        return auxiliaryRepository.listThematicByIdAuxiliary(id);
    }

    public List<Auxiliary> listAuxiliarysByIdAreaAcademicUnit(long idarea, long idacademicunit) {
        return auxiliaryRepository.findAuxiliaryByArea_IdareaAndAcademicUnit_Idacademicunit(idarea, idacademicunit);
    }

    public List<Auxiliary> listAuxiliarysByTeaching(String area){
        return auxiliaryRepository.findAuxiliariesByArea_Name(area);
    }
//
//    public List<Auxiliary> listadoByIdAnnouncement(long ci){ return auxiliaryRepository.findAuxiliaryByAnnouncement_Idannouncement(ci); }
//
//    public List<Auxiliary> listadoPorCarrera(int career){
//
//        return auxiliaryRepository.findByCareer_CodCareerOrderByPerson(career);
//    }
//    public List<Auxiliary> QueryCiCodCareer(String ci, int career){
//
//        return auxiliaryRepository.queryByPerson_CiAndCareer_CodCareer(ci,career);
//    }
}
