package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Request;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Requirement;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequestRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequirementService {

    @Autowired
    private RequirementRepository requirementRepository;

    public List<Requirement> getAllRequirement() {
        return requirementRepository.findAll();
    }

    public Requirement getRequirement(long ci) {
        return requirementRepository.FindByCi(ci)
                .orElse(null);
    }


    public long addRequirement(Requirement requirement) {

        requirementRepository.save(requirement);
        return requirementRepository.selectByIdrequirement();
    }

    public void deleteRequirement(long ci) {
        requirementRepository.DeleteByCi(ci);
    }

    public List<Requirement> ListadoByAuxiliary(long id){
        return requirementRepository.findRequirementByAuxiliary_Idauxiliary(id);
    }

    public List<Requirement> ListadoByAnnouncement(long id){
        return requirementRepository.findRequirementsByAnnouncement_Idannouncement(id);
    }


    public void updateRequirementAssignedItems(long id, int assignedItems) {
        requirementRepository.UpdateByAssignedItems(id, assignedItems);

    }
}
