package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AdittionalLabel;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AdittionalLabelRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ThematicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdittionalLabelService {

    @Autowired
    private AdittionalLabelRepository adittionalLabelRepository;

    public List<AdittionalLabel> getAllAdittionalLabel() {
        return adittionalLabelRepository.findAll();
    }

    public AdittionalLabel getAdittionalLabel(long ci) {
        return adittionalLabelRepository.FindByCi(ci)
                .orElse(null);
    }


    public long addAdittionalLabel(AdittionalLabel adittionalLabel) {

        adittionalLabelRepository.save(adittionalLabel);
        return adittionalLabelRepository.selectByIdadditionallabel();
    }

    public void deleteAdittionalLabel(int ci) {
        adittionalLabelRepository.DeleteByCi(ci);
    }
}
