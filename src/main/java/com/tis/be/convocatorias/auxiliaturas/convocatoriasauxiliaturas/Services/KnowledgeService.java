package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Knowledge;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;


    public List<Knowledge> getAllKnowledge() {
        return knowledgeRepository.findAll();
    }


    public Knowledge getKnowledge(long id) {
        return knowledgeRepository.FindById(id)
                .orElse(null);

    }

    public long addKnowledge(Knowledge knowledge) {
        knowledgeRepository.save(knowledge);
        return knowledgeRepository.selectByIdknowledge();
    }


    public void deleteKnowledge(long id) {
        knowledgeRepository.DeleteByCi(id);
    }


    public List<Knowledge> ListByAnnouncement(long id){
        return knowledgeRepository.findKnowledgeByAnnouncement_Idannouncement(id);
    }

}
