package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Label;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> getAllLabel() {
        return  labelRepository.findAll();
    }

    public Label getLabel(long id) {
        return labelRepository.FindById(id).orElse(null);

    }

    public long addLabel(Label label) {
        labelRepository.save(label);
        return labelRepository.selectByIdlabel();
    }

    public void deleteLabel(long id) {
        labelRepository.DeleteByCi(id);
    }

    public List<Label> searcherByTextAcadMan(String textBeta, String textAlfa, String idAcad, String idMan, String idAnnoun) {
        return labelRepository.searcherByTextAcadManAnnoun(textAlfa, textBeta, idAcad, idMan, idAnnoun);
    }

    public List<Label> searcherByTextAnnoun(String textBeta, String textAlfa, String idAnnoun) {
        return labelRepository.searcherByTextAnnoun(textAlfa, textBeta, idAnnoun);
    }

    public List<Label> searcherByTextAnnounAndStatusTrue(String textBeta, String textAlfa, String idAnnoun) {
        return labelRepository.searcherByTextAnnounAndStatusTrue(textAlfa, textBeta, idAnnoun);
    }

    public String lastManagement() {
        return labelRepository.selectByIdmanagement();
    }

    public List<Label> listLabelByIdPerson(long idperson){
        return labelRepository.findLabelByPostulantes_Person_Id(idperson);
    }
}
