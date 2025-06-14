package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.IdlabelInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByIdAnnouncementAuxiliaryAprovedInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByIdAnnouncementIdThematicInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists.ListByIdUserAnnouncementATInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.LogBookInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.LogbookHabilitadosInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Searcher.SearchLogbookBy;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LabelRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.LogBookRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.LogBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LogBookController {

    @Autowired
    private LogBookService logBookService;
    @Autowired
    private LogBookRepository logBookRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private AnnouncementController announcementController;

    @RequestMapping("/logbook")
    public List<LogBook> getAllLogBook() {
        return logBookService.getAllLogBook();
    }

    @RequestMapping("/logbook/{id}")
    public LogBook getLogBook(@PathVariable long id) {
        return logBookService.getLogBook(id);
    }

    @PostMapping("/logbook")
    public ResponseEntity<Long> addLogBook(@RequestBody LogBookInput logBookInput) {
        Label label = labelRepository.getById(logBookInput.getLabel());
        if (!logBookRepository.existsLogBookByLabel_IdlabelAndDeliveryDateAndDeliveryHour(logBookInput.getLabel(), logBookInput.getDeliveryDate(), logBookInput.getDeliveryHour())){
        LogBook logBook = new LogBook();
        logBook.setIdlogbook(999999);
        logBook.setDeliveryDate(logBookInput.getDeliveryDate());
        logBook.setDeliveryHour(logBookInput.getDeliveryHour());
        logBook.setDocument_quantity(logBookInput.getDocumentQuantity());
        logBook.setObservation(logBookInput.getObservation());
        logBook.setLabel(label);

        return ResponseEntity.ok().body(logBookService.addLogBook(logBook));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/logbook/{id}")
    public void deleteLogBook(@PathVariable long id) {
        logBookService.deleteLogBook(id);
    }

    @RequestMapping("/logbook/listado/{id}")
    public List<LogBook> listadoAnnouncement(@PathVariable("id") long id) {
        return logBookService.ListadoAnnouncement(id);
    }

    @PostMapping("/logbook/listado/status")
    public List<LogBook> listadoAnnouncementByStatus(@RequestBody LogbookHabilitadosInput logbookHabilitadosInput) {
        return logBookService.ListadoByAnnouncementIdandPostulantStatus(logbookHabilitadosInput.getIdannouncement(), logbookHabilitadosInput.isStatus());
    }

    @PostMapping("/logbook/list/UAW")
    public List<LogBook> listByIdUserAnnouncementAT(@RequestBody ListByIdUserAnnouncementATInput listByIdUserAnnouncementATInput) {

        long aux = announcementController.findAreaNameByIdAnnouncement(listByIdUserAnnouncementATInput.getIdannouncement());
        List<LogBook> list = new ArrayList<>();
        if (aux == 1) {

            list = logBookService.ListByIdUserAnnouncementT(
                    listByIdUserAnnouncementATInput.getIdannouncement(),
                    aux,
                    listByIdUserAnnouncementATInput.getId(),
                    listByIdUserAnnouncementATInput.getIduser()
            );

        } else if (aux == 2) {
            list = logBookService.ListByIdUserAnnouncementA(
                    listByIdUserAnnouncementATInput.getIdannouncement(),
                    aux,
                    listByIdUserAnnouncementATInput.getId(),
                    listByIdUserAnnouncementATInput.getIduser()
            );
        }
        return list;
    }

    @PostMapping("/logbook/list/UAAproved")
    public List<LogBook> ListByIdUserAnnouncementAuxiliaryAproved(@RequestBody ListByIdAnnouncementAuxiliaryAprovedInput listByIdAnnouncementAuxiliaryAprovedInput) {
        return logBookService.ListByIdUserAnnouncementAuxiliaryAproved(
                listByIdAnnouncementAuxiliaryAprovedInput.getIdannouncement(),
                listByIdAnnouncementAuxiliaryAprovedInput.getIdauxiliary()
        );
    }

    @PostMapping("logbook/list/ATstatusT")
    public List<LogBook> ListByIdAnnouncementThematicIsStatusTrue(@RequestBody ListByIdAnnouncementIdThematicInput listByIdAnnouncementIdThematicInput) {
        return logBookService.ListByThematicAnnouncementIsStatusTrue(
                listByIdAnnouncementIdThematicInput.getIdannouncement(),
                listByIdAnnouncementIdThematicInput.getIdthematic()
        );
    }


    @PostMapping("logbook/verification/label")
    public int existByIdLabel(@RequestBody IdlabelInput idlabelInput) {
        if (logBookService.existsLogbookByIdLabel(idlabelInput.getIdlabel())) {
            return 1;
        } else {
            return 2;
        }
    }

    //    -------------------------------------------------- Searchers -----------------------------------------------------
    @PostMapping("/logbook/searchByText")
    public List<LogBook> searchPostulants(@RequestBody SearchLogbookBy searchLogbookBy) {
        String searchTerm = searchLogbookBy.getText();
        String searchTerm2 = "";
        String[] currencies = searchTerm.split(" ");
        if (currencies.length > 1) {
            searchTerm = currencies[0];
            searchTerm2 = currencies[1];
            List<LogBook> combo = new ArrayList<>();
            combo.addAll(logBookService.searcherLogbookByTextAnnouncement(searchTerm, searchTerm, searchLogbookBy.getIdannouncement()));
            combo.addAll(logBookService.searcherLogbookByTextAnnouncement(searchTerm2, searchTerm2, searchLogbookBy.getIdannouncement()));
            LinkedHashSet<LogBook> hashSet = new LinkedHashSet<>(combo);
            ArrayList<LogBook> listWithoutDuplicates = new ArrayList<>(hashSet);
            return listWithoutDuplicates;
        }
        return logBookService.searcherLogbookByTextAnnouncement(searchTerm, searchTerm, searchLogbookBy.getIdannouncement());
    }

    @PostMapping("/logbook/searchByText/status")
    public List<LogBook> searchPostulantsByStatus(@RequestBody SearchLogbookBy searchLogbookBy) {
        String searchTerm = searchLogbookBy.getText();
        String searchTerm2 = "";
        String[] currencies = searchTerm.split(" ");
        if (currencies.length > 1) {
            searchTerm = currencies[0];
            searchTerm2 = currencies[1];
            List<LogBook> combo = new ArrayList<>();
            combo.addAll(logBookService.searcherLogbookByTextAnnounAndStatus(searchTerm, searchTerm, searchLogbookBy.getIdannouncement(), searchLogbookBy.isStatus()));
            combo.addAll(logBookService.searcherLogbookByTextAnnounAndStatus(searchTerm2, searchTerm2, searchLogbookBy.getIdannouncement(), searchLogbookBy.isStatus()));
            LinkedHashSet<LogBook> hashSet = new LinkedHashSet<>(combo);
            ArrayList<LogBook> listWithoutDuplicates = new ArrayList<>(hashSet);
            return listWithoutDuplicates;
        }
        return logBookService.searcherLogbookByTextAnnounAndStatus(searchTerm, searchTerm, searchLogbookBy.getIdannouncement(), searchLogbookBy.isStatus());
    }
//    -------------------------------------------------- Searchers -----------------------------------------------------


}
