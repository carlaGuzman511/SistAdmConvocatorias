package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Courts;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.CourtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtsService {

    @Autowired
    private CourtsRepository courtsRepository;


    public List<Courts> getAllCourts() {
        return courtsRepository.findAll();
    }


    public Courts getCourts(long id) {
        return courtsRepository.FindById(id)
                .orElse(null);

    }

    public long addCourts(Courts courts) {
        courtsRepository.save(courts);
        return courtsRepository.selectByIdCourts();
    }

    public void deleteCourts(long id) {
        courtsRepository.DeleteByCi(id);
    }

    public Courts getDescription(long id) {
        Courts courts = courtsRepository.getByIdAnnouncement(id);
        return courts;
    }

    public void countingCourts(String roleName, long idannoun) {
        Courts courts = courtsRepository.getByIdAnnouncement(idannoun);
        Courts courts1 = courtsRepository.getOne(courts.getIdcourts());
        switch(roleName){
            case "Comision de Conocimientos Docente": if(courts1.getNumberKnowledgeCourts() < courts1.getKnowledgeCourts()) {
                courts1.setNumberKnowledgeCourts(courts1.getNumberKnowledgeCourts() + 1);
                courtsRepository.save(courts1);
                break;
            }else{ System.out.println("0");
            break; }

            case "Comision de Meritos Docente": if(courts1.getNumberMeritCourts() < courts1.getMeritsCourts()) {
                courts1.setNumberMeritCourts(courts1.getNumberMeritCourts() + 1);
                courtsRepository.save(courts1);
                break;
            }else{ System.out.println("0");
                break; }

            case "Comision de Conocimientos Estudiante": if(courts1.getNumberKnowledgeStudentDelegate() < courts1.getStudentDelegateKnowledge()) {
                courts1.setNumberKnowledgeStudentDelegate(courts1.getNumberKnowledgeStudentDelegate() + 1);
                courtsRepository.save(courts1);
                break;
            }else{ System.out.println("0");
                break; }

            case "Comision de Meritos Estudiante": if(courts1.getNumberMeritStudentDelegate() < courts1.getStudentDelegateMerit()) {
                courts1.setNumberMeritStudentDelegate(courts1.getNumberMeritStudentDelegate() + 1);
                courtsRepository.save(courts1);
                break;
            }else{ System.out.println("0");
                break; }
            default: System.out.println("2");
                break;
        }
    }
}
