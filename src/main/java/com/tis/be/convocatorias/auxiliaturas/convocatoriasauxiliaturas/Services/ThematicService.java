package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ThematicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThematicService {

    @Autowired
    private ThematicRepository thematicRepository;

    public List<Thematic> getAllThematic() {
        return thematicRepository.findAll();
    }

    public Thematic getThematic(int ci) {
        return thematicRepository.FindByCi(ci)
                .orElse(null);
    }


    public long addThematic(Thematic thematic) {

        thematicRepository.save(thematic);
        return thematicRepository.selectByIdthematic();
    }

    public void deleteThematic(int ci) {
        thematicRepository.DeleteByCi(ci);
    }

    public List<Thematic> listByIdAnnouncement(long id){
        return thematicRepository.listByIdAnnouncement(id);
    }

}
