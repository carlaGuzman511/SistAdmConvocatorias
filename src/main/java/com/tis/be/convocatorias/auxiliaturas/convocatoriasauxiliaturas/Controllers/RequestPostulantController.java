package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.RequestPostulantInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.RequestPostulantUpdateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestDetailRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestPostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.RequestPostulantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RequestPostulantController {

    @Autowired
    private RequestPostulantService requestPostulantService;
    @Autowired
    private RequestPostulantRepository requestPostulantRepository;
    @Autowired
    private RequestDetailRepository requestDetailRepository;
    @Autowired
    private PostulantRepository postulantRepository;

    @RequestMapping("/requestPostulant")
    public List<RequestPostulant> getAllRequestPostulant() {
        return requestPostulantService.getAllRequestPostulant();
    }

    @RequestMapping("/requestPostulant/{id}")
    public RequestPostulant getRequestPostulant(@PathVariable int id) {
        return requestPostulantService.getRequestPostulant(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/requestPostulant")
    public ResponseEntity<Long> addRequestPostulant(@RequestBody RequestPostulantInput requestPostulantInput) {
        Postulant postulant = postulantRepository.getById(requestPostulantInput.getIdpostulant());
        RequestDetail requestDetail = requestDetailRepository.getById(requestPostulantInput.getIdrequestdetail());
        if (!requestPostulantRepository.existsRequestPostulantByPostulant_IdpostulantAndRequestdetail_IdrequestdetailAndStatus(
                requestPostulantInput.getIdpostulant(), requestPostulantInput.getIdrequestdetail(), requestPostulantInput.isStatus()))
        {
            RequestPostulant requestPostulant = new RequestPostulant();
            requestPostulant.setIdrequestpostulant(999999);
            requestPostulant.setStatus(requestPostulantInput.isStatus());
            requestPostulant.setObservation(requestPostulantInput.getObservation());
            requestPostulant.setPostulant(postulant);
            requestPostulant.setRequestdetail(requestDetail);

            return ResponseEntity.ok().body(requestPostulantService.addRequestPostulant(requestPostulant));

        } else{
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/requestPostulant/{id}")
    public void deleteRequirement(@PathVariable int id) {
        requestPostulantService.deleteRequestPostulant(id);
    }

    @RequestMapping("/requestPostulant/postulant/{id}")
    public List<RequestPostulant> listadoByPostulant(@PathVariable("id") long id) {
        return requestPostulantService.ListadoByPostulant(id);
    }

    @PutMapping("requestPostulant/single/status")
    public RequestPostulant updateRequestPostulantSingleStatus(@RequestBody RequestPostulantUpdateInput requestPostulantUpdateInput) {
        requestPostulantService.updateRequestPostulantStatus(
                requestPostulantUpdateInput.getIdRequestPostulant(),
                requestPostulantUpdateInput.isStatus(),
                requestPostulantUpdateInput.getObservation());

        return requestPostulantService.getRequestPostulant(requestPostulantUpdateInput.getIdRequestPostulant());
    }

}
