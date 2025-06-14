package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.RequestPostulant;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestPostulantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestPostulantService {

    @Autowired
    private RequestPostulantRepository requestPostulantRepository;

    public List<RequestPostulant> getAllRequestPostulant() {
        return requestPostulantRepository.findAll();
    }

    public RequestPostulant getRequestPostulant(long ci) {
        return requestPostulantRepository.FindByCi(ci)
                .orElse(null);
    }


    public long addRequestPostulant(RequestPostulant requestPostulant) {

        requestPostulantRepository.save(requestPostulant);
        return requestPostulantRepository.selectByIdrequestpostulant();
    }

    public void deleteRequestPostulant(long ci) {
        requestPostulantRepository.DeleteByCi(ci);
    }

    public List<RequestPostulant> ListadoByPostulant(long id){
        return requestPostulantRepository.findRequestPostulantByPostulant_Idpostulant(id);
    }

    public void updateRequestPostulantStatus(long id, boolean status, String observation){
        requestPostulantRepository.updateRequestPostulantStatus(
                id,
                status,
                observation
        );
    }
}
