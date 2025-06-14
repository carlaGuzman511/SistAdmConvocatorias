package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;//package com.tis.convocatorias.postulant.service.Career;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Career;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    public List<Career> getAllCareer() {
        return careerRepository.findAll();
    }

    public Career getCareer(int ci) {
        return careerRepository.FindByCi(ci)
                .orElse(null);
    }


    public void addCareer(Career career) {

        careerRepository.save(career);
    }

    public void updateCareer(int ci, Career career) {
        careerRepository.UpdateByCi(ci, career.getName());

    }

    public void deleteCareer(int ci) {
        careerRepository.DeleteByCi(ci);
    }
}
