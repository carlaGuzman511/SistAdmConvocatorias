package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Postulant;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostulantService {

    @Autowired
    private PostulantRepository postulantRepository;


    public List<Postulant> getAllPostulants() {
        return postulantRepository.findAll();
    }


    public Postulant getPostulant(long id) {
        return postulantRepository.FindById(id)
                .orElse(null);

    }

    public long addPostulant(Postulant postulant) {
        postulantRepository.save(postulant);
        return postulantRepository.selectByIdpostulant();
    }

    public void deletePostulant(long id) {
        postulantRepository.DeleteByCi(id);
    }


    public List<Postulant> listByPerson(long id){

        return postulantRepository.findPostulantesByPerson_Id(id);
    }

    public List<Postulant> listadoPorCarrera(int career){

        return postulantRepository.findByCareer_CodCareerOrderByPerson(career);
    }
    public List<Postulant> QueryCiCodCareer(String ci, int career){

        return postulantRepository.queryByPerson_CiAndCareer_CodCareer(ci,career);
    }

    public List<Postulant> listPostulantByResultOralandLogbookExists (){

        return postulantRepository.listPostulantFromResultOralandLogbookExists();
    }


    public List<Postulant> listPostulantByAnnouncementAuxiliaryThematic(){

        return postulantRepository.listPostulantIfLaboratoryOrTeaching();
    }

    public void updatePostulantStatus(long id, boolean status) {
        postulantRepository.UpdateStatusPostulant(id, status);

    }

}
