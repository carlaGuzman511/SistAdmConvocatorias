package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Request;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Thematic;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ThematicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public List<Request> getAllRequest() {
        return requestRepository.findAll();
    }

    public Request getRequest(long ci) {
        return requestRepository.FindByCi(ci)
                .orElse(null);
    }


    public long addRequest(Request request) {

        requestRepository.save(request);
        return requestRepository.selectByIdrequest();
    }

    public void deleteRequest(long ci) {
        requestRepository.DeleteByCi(ci);
    }

    public long getLastById(){
        return requestRepository.selectByIdrequest();
    }

    public List<Request> ListadoByAnnouncement(long id){
        return requestRepository.findRequestByAnnouncement_Idannouncement(id);
    }
}
