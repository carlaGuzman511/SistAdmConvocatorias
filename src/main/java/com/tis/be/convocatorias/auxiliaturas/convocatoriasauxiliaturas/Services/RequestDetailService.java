package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Request;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.RequestDetail;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestDetailRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestDetailService {

    @Autowired
    private RequestDetailRepository requestDetailRepository;

    public List<RequestDetail> getAllRequestDetail() {
        return requestDetailRepository.findAll();
    }

    public RequestDetail getRequestDetail(long ci) {
        return requestDetailRepository.FindByCi(ci)
                .orElse(null);
    }


    public long addRequestDetail(RequestDetail requestDetail) {

        requestDetailRepository.save(requestDetail);
        return requestDetailRepository.selectByIdrequest();
    }

    public void deleteRequestDetail(long id) {
        requestDetailRepository.DeleteById(id);
    }

    public List<RequestDetail> ListadoByRequest(long id){
        return requestDetailRepository.findRequestDetailsByRequest_Idrequest(id);
    }

    public List<RequestDetail> getRequestDetailByIdAnnouncement(long id){
        return requestDetailRepository.getByIdAnnouncement(id);
    }

}
