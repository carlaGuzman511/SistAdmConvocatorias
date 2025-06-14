package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;

import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.AcademicUnit;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.AcademicUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcademicUnitService {

    @Autowired
    private AcademicUnitRepository academicUnitRepository;

    public List<AcademicUnit> getAllAcademicUnits() {
        return academicUnitRepository.findAll();
    }

    public List<AcademicUnit> listByManagementFaculty( long idFac, long idMan) {
        return academicUnitRepository.listByManFac(idFac, idMan);
    }

    public AcademicUnit getAcademicUnit(int ci) {
        return academicUnitRepository.FindByCi(ci).orElse(null);
    }

    public long addAcademicUnit(AcademicUnit academicUnit) {

        academicUnitRepository.save(academicUnit);
        return academicUnitRepository.selectByIdAcademicUnit();
    }

    public void deleteAcademicUnit(int ci) {
        academicUnitRepository.DeleteByCi(ci);
    }
}
