package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Faculty;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;


    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }


    public Faculty getFaculty(long id) {
        return facultyRepository.FindById(id)
                .orElse(null);

    }

    public void addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }


    public void deleteFaculty(long id) {
        facultyRepository.DeleteByCi(id);
    }

}
