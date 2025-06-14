package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Document;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public List<Document> getAllDocument() {

        return documentRepository.findAll();
    }

    public Document getDocument(long id) {
        return documentRepository.FindById(id)
                .orElse(null);

    }

    public long addDocument(Document document) {
        documentRepository.save(document);
        return documentRepository.selectByIdDocument();
    }

    public void deleteDocument(long id) {
        documentRepository.DeleteByCi(id);
    }

    public List<Document> ListByAnnouncement(long id){
        return documentRepository.findDocumentsByAnnouncement_Idannouncement(id);
    }

    public long getByLastId(){
        return documentRepository.selectByIdDocument();
    }

}
