package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.RequestInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.RequestCreateInput.RequestWithDetailsInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Announcement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Request;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RequestController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private RequestDetailController requestDetailController;

    @RequestMapping("/request")
    public List<Request> getAllRequest() {
        return requestService.getAllRequest();
    }

    @RequestMapping("/request/{id}")
    public Request getRequest(@PathVariable int id) {
        return requestService.getRequest(id);
    }

    @PostMapping("/request")
    public ResponseEntity<Long> addRequest(@RequestBody RequestInput requestInput) {
        Announcement announcement = announcementRepository.getById(requestInput.getAnnouncement());
        if (!requestRepository.existsRequestByAnnouncement_IdannouncementAndDescriptionAndNote(requestInput.getAnnouncement(), requestInput.getDescription(), requestInput.getNote())) {
            Request request = new Request();
            request.setIdrequest(999999);
            request.setDescription(requestInput.getDescription());
            request.setNote(requestInput.getNote());
            request.setAnnouncement(announcement);

            return ResponseEntity.ok().body(requestService.addRequest(request));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }


    @PostMapping("/request/create")
    public ResponseEntity<Long> addShapeCreateSingle(@RequestBody RequestWithDetailsInput requestWithDetailsInput) {
        Announcement announcement = announcementRepository.getById(requestWithDetailsInput.getAnnouncement());
        if (!requestRepository.existsRequestByAnnouncement_IdannouncementAndDescriptionAndNote(requestWithDetailsInput.getAnnouncement(), requestWithDetailsInput.getDescription(), requestWithDetailsInput.getNote())) {
            Request request = new Request();
            request.setIdrequest(999999);
            request.setNote(requestWithDetailsInput.getNote());
            request.setDescription(requestWithDetailsInput.getDescription());
            request.setAnnouncement(announcement);
            requestService.addRequest(request);
            for (int i = 0; i < requestWithDetailsInput.getRequests().size(); i++) {
                requestDetailController.addRequestDetailCreate(
                        requestWithDetailsInput.getRequests().get(i),
                        requestService.getLastById()
                );

            }

            return ResponseEntity.ok().body(requestService.getLastById());

        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/request/{id}")
    public void deleteRequest(@PathVariable int id) {
        requestService.deleteRequest(id);
    }

    @RequestMapping("/request/announcement/{id}")
    public List<Request> listadoByAnnouncement(@PathVariable("id") long id) {
        return requestService.ListadoByAnnouncement(id);
    }

}
