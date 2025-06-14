package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.RequestDetailInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.RequestCreateInput.RequestDetailCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Request;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.RequestDetail;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestDetailRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.RequestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RequestDetailController {

    @Autowired
    private RequestDetailService requestDetailService;
    @Autowired
    private RequestDetailRepository requestDetailRepository;
    @Autowired
    private RequestRepository requestRepository;

    @RequestMapping("/requestdetail")
    public List<RequestDetail> getAllRequestDetail() {
        return requestDetailService.getAllRequestDetail();
    }

    @RequestMapping("/requestdetail/{id}")
    public RequestDetail getRequest(@PathVariable int id) {
        return requestDetailService.getRequestDetail(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/requestdetail")
    public ResponseEntity<Long> addRequestDetail(@RequestBody RequestDetailInput requestDetailInput) {
        Request request = requestRepository.getById(requestDetailInput.getRequest());
        if (!requestDetailRepository.existsRequestDetailByDescriptionAndRequest_Idrequest(requestDetailInput.getDescription(), requestDetailInput.getRequest())) {
            RequestDetail requestDetail = new RequestDetail();
            requestDetail.setIdrequestdetail(999999);
            requestDetail.setDescription(requestDetailInput.getDescription());
            requestDetail.setRequest(request);

            return ResponseEntity.ok().body(requestDetailService.addRequestDetail(requestDetail));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/requestdetail/create")
    public ResponseEntity<Long> addRequestDetailCreate(@RequestBody RequestDetailCreateInput requestDetailCreateInput, long id) {
        Request request = requestRepository.getById(id);
        if (!requestDetailRepository.existsRequestDetailByDescriptionAndRequest_Idrequest(requestDetailCreateInput.getRequisito(), id)) {
            RequestDetail requestDetail = new RequestDetail();
            requestDetail.setIdrequestdetail(999999);
            requestDetail.setDescription(requestDetailCreateInput.getRequisito());
            requestDetail.setRequest(request);

            return  ResponseEntity.ok().body(requestDetailService.addRequestDetail(requestDetail));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/requestdetail/{id}")
    public void deleteRequestDetail(@PathVariable int id) {
        requestDetailService.deleteRequestDetail(id);
    }

    @RequestMapping("/requestdetail/request/{id}")
    public List<RequestDetail> listadoByRequestDetail(@PathVariable("id") long id) {
        return requestDetailService.ListadoByRequest(id);
    }

    @RequestMapping("/requestdetail/announcement/{id}")
    public List<RequestDetail> getRequestByIdAnnouncement(@PathVariable("id") long id) {

        return requestDetailService.getRequestDetailByIdAnnouncement(id);
    }

}
