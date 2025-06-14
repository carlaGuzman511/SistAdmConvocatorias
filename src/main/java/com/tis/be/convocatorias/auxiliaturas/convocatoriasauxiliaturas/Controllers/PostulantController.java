package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain.PostulantInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update.PostulantUpdateStatusInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Career;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Postulant;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.CareerRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PersonRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PostulantRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.PostulantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class PostulantController {

    @Autowired
    private PostulantService postulantService;
    @Autowired
    private PostulantRepository postulantRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CareerRepository careerRepository;


    @RequestMapping("/postulant")
    public List<Postulant> getAllPostulants() {
        return postulantService.getAllPostulants();
    }

    @RequestMapping("/postulant/{id}")
    public Postulant getPostulant(@PathVariable long id) {
        return postulantService.getPostulant(id);
    }

    @PostMapping("/postulant")
    public ResponseEntity<Long> addPostulant(@RequestBody PostulantInput postulantInput) {
        Person person = personRepository.getById(postulantInput.getPerson());
        Career career = careerRepository.getById(postulantInput.getCareer());
        if (!postulantRepository.existsPostulantByCareer_IdcareerAndPerson_IdAndStatus(
                postulantInput.getCareer(), postulantInput.getPerson(), postulantInput.isStatus())){
        Postulant postulant = new Postulant();
        postulant.setIdpostulant(99999);
        postulant.setStatus(postulantInput.isStatus());
        postulant.setPerson(person);
        postulant.setCareer(career);

        return ResponseEntity.ok().body(postulantService.addPostulant(postulant));
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @DeleteMapping("/postulant/{id}")
    public void deletePostulant(@PathVariable long id) {
        postulantService.deletePostulant(id);
    }

    @RequestMapping("/postulant/person/{id}")
    public List<Postulant> listByPerson(@PathVariable("id") long id) {
        return postulantService.listByPerson(id);
    }

    @RequestMapping("/postulant/listar/{career}")
    public List<Postulant> listadoPorCarrera(@PathVariable("career") int career) {
        return postulantService.listadoPorCarrera(career);
    }

    @RequestMapping("/postulant/listando/{ci}/{career}")
    public List<Postulant> QueryCiCodCareer(@PathVariable("ci") String ci , @PathVariable("career") int career) {
        return postulantService.QueryCiCodCareer(ci,career);
    }

    @RequestMapping("/postulant/list/LSS")
    public List<Postulant> listPostulantByResultOralandLogbook(){
        return postulantService.listPostulantByResultOralandLogbookExists();
    }

    @RequestMapping("/postulant/listadoManytoMany")
    public List<Postulant> listByAnnouncementAuxiliaryThematic(){
        return postulantService.listPostulantByAnnouncementAuxiliaryThematic();
    }

    @PutMapping("postulant/update/status")
    public Postulant updatePostulantStatus(@RequestBody PostulantUpdateStatusInput postulantUpdateStatusInput){
        postulantService.updatePostulantStatus(
                postulantUpdateStatusInput.getIdPostulant(),
                postulantUpdateStatusInput.isStatus()
        );
        return postulantService.getPostulant(postulantUpdateStatusInput.getIdPostulant());
    }
}
