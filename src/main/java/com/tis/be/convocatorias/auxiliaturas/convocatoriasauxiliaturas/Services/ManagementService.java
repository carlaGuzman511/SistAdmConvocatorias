package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Management;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.ManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagementService {

    @Autowired
    private ManagementRepository managementRepository;

    public List<Management> getAllManagements() {
        return managementRepository.findAll();
    }

    public Management getManagement(int ci) {

        return managementRepository.FindByCi(ci)
                .orElse(null);
    }

    public void addManagement(Management management) {

        managementRepository.save(management);
    }

    public void deleteManagement(int ci) {
        managementRepository.DeleteByCi(ci);
    }
}
