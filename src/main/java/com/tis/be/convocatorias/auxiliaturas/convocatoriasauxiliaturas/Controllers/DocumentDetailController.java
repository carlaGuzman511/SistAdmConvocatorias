package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DocDetailCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.DocumentDetailInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.*;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentDetailRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.DocumentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DocumentDetailController {

    @Autowired
    private DocumentDetailService documentDetailService;
    @Autowired
    private DocumentDetailRepository documentDetailRepository;
    @Autowired
    private DocumentRepository documentRepository;


    @RequestMapping("/documentdetail")
    public List<DocumentDetail> getAllDocumentDetail() {
        return documentDetailService.getAllDocumentDetail();
    }

    @RequestMapping("/documentdetail/{id}")
    public DocumentDetail getDocumentDetail(@PathVariable long id) {
        return documentDetailService.getDocumentDetail(id);
    }

    @PostMapping("/documentdetail")
    public ResponseEntity<Long> addDocumentDetail(@RequestBody DocumentDetailInput documentDetailInput) {
        Document document = documentRepository.getById(documentDetailInput.getDocument());
        if (!documentDetailRepository.existsDocumentDetailByDetailAndDocument_Iddocument(documentDetailInput.getDetail(), documentDetailInput.getDocument())) {
            DocumentDetail documentDetail = new DocumentDetail();
            documentDetail.setIddocumentdetail(999999);
            documentDetail.setDetail(documentDetailInput.getDetail());
            documentDetail.setDocument(document);

            return ResponseEntity.ok().body(documentDetailService.addDocumentDetail(documentDetail));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    public ResponseEntity<Long> addDocumentDetailCreate(@RequestBody DocDetailCreateInput docDetailCreateInput, long id) {
        Document document = documentRepository.getById(id);
        if (!documentDetailRepository.existsDocumentDetailByDetailAndDocument_Iddocument(docDetailCreateInput.getDoc(), id)) {
            DocumentDetail documentDetail = new DocumentDetail();
            documentDetail.setIddocumentdetail(999999);
            documentDetail.setDetail(docDetailCreateInput.getDoc());
            documentDetail.setDocument(document);

            return ResponseEntity.ok().body(documentDetailService.addDocumentDetail(documentDetail));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/documentdetail/{id}")
    public void deleteDocumentDetail(@PathVariable long id) {
        documentDetailService.deleteDocumentDetail(id);
    }

    @RequestMapping("/documentdetail/document/{id}")
    public List<DocumentDetail> ListByDocument(@PathVariable("id") long id) {
        return documentDetailService.ListByDocument(id);
    }

}
