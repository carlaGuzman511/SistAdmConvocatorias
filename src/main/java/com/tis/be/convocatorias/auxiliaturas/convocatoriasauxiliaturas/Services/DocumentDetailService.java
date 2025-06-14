package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.DocumentDetail;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.DocumentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentDetailService {

    @Autowired
    private DocumentDetailRepository documentDetailRepository;


    public List<DocumentDetail> getAllDocumentDetail() {

        return documentDetailRepository.findAll();
    }


    public DocumentDetail getDocumentDetail(long id) {
        return documentDetailRepository.FindById(id)
                .orElse(null);

    }

    public long addDocumentDetail(DocumentDetail documentDetail) {
        documentDetailRepository.save(documentDetail);
        return documentDetailRepository.selectByIdDocumentDetail();
    }

    public void deleteDocumentDetail(long id) {
        documentDetailRepository.DeleteByCi(id);
    }

    public List<DocumentDetail> ListByDocument(long id){
        return documentDetailRepository.findDocumentDetailsByDocument_Iddocument(id);
    }


}
