package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DocsToPresentCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.DocumentCreateInput.DocumentWithDetailsCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.DocumentInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AnnouncementRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private DocumentDetailController documentDetailController;


    @RequestMapping("/document")
    public List<Document> getAllDocument() {
        return documentService.getAllDocument();
    }

    @RequestMapping("/document/{id}")
    public Document getDocument(@PathVariable long id) {
        return documentService.getDocument(id);
    }

    @PostMapping("/document")
    public ResponseEntity<Long> addDocument(@RequestBody DocumentInput documentInput) {
        Announcement announcement = announcementRepository.getById(documentInput.getAnnouncement());
        if (!documentRepository.existsDocumentByAnnouncement_IdannouncementAndNameAndNote(documentInput.getAnnouncement(), documentInput.getName(), documentInput.getNote())) {
            Document document = new Document();
            document.setIddocument(999999);
            document.setName(documentInput.getName());
            document.setNote(documentInput.getNote());
            document.setAnnouncement(announcement);

            return ResponseEntity.ok().body(documentService.addDocument(document));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }


    public ResponseEntity<Long> addDocumentCreate(@RequestBody DocsToPresentCreateInput docsToPresentCreateInput, long id) {
        Announcement announcement = announcementRepository.getById(id);
        if (!documentRepository.existsDocumentByAnnouncement_IdannouncementAndNameAndNote(id, docsToPresentCreateInput.getName(), docsToPresentCreateInput.getNote())) {
            Document document = new Document();
            document.setIddocument(999999);
            document.setName(docsToPresentCreateInput.getName());
            document.setNote(docsToPresentCreateInput.getNote());
            document.setAnnouncement(announcement);
            documentService.addDocument(document);
            for (int i = 0; i < docsToPresentCreateInput.getDocs().size(); i++) {
                documentDetailController.addDocumentDetailCreate(
                        docsToPresentCreateInput.getDocs().get(i),
                        documentService.getByLastId()
                );
            }
            return ResponseEntity.ok().body(documentService.addDocument(document));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @PostMapping("/document/create")
    public ResponseEntity<Long> addDocumentCreateSingle(@RequestBody DocumentWithDetailsCreateInput documentWithDetailsCreateInput) {
        Announcement announcement = announcementRepository.getById(documentWithDetailsCreateInput.getAnnouncement());
        if (!documentRepository.existsDocumentByAnnouncement_IdannouncementAndNameAndNote(documentWithDetailsCreateInput.getAnnouncement(), documentWithDetailsCreateInput.getName(), documentWithDetailsCreateInput.getNote())) {
            Document document = new Document();
            document.setIddocument(999999);
            document.setName(documentWithDetailsCreateInput.getName());
            document.setNote(documentWithDetailsCreateInput.getNote());
            document.setAnnouncement(announcement);
            documentService.addDocument(document);
            for (int i = 0; i < documentWithDetailsCreateInput.getDocs().size(); i++) {
                documentDetailController.addDocumentDetailCreate(
                        documentWithDetailsCreateInput.getDocs().get(i),
                        documentService.getByLastId()
                );
            }
            return ResponseEntity.ok().body(documentService.addDocument(document));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/document/{id}")
    public void deleteDocument(@PathVariable long id) {
        documentService.deleteDocument(id);
    }

    @RequestMapping("/document/announcement/{id}")
    public List<Document> ListByAnnouncement(@PathVariable("id") long id) {
        return documentService.ListByAnnouncement(id);
    }

}
