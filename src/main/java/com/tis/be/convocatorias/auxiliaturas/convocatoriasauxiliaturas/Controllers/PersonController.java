package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Controllers;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PersonRepository;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/person")
    public List<Person> getAllPerson() {
        return personService.getAllPersons();
    }

    @RequestMapping("/person/{id}")
    public Person getPerson(@PathVariable long id) {
        return personService.getPerson(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/person")
    public ResponseEntity<Long> addPerson(@RequestBody Person person) {
        if (!personRepository.existsPersonByCi(person.getCi())) {
            personService.addPerson(person);
            return ResponseEntity.ok().body(person.getId());
        } else {
            long idrepetitivo = -2;
            return ResponseEntity.badRequest().body(idrepetitivo);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/person/{id}")
    public void updatePerson(@RequestBody Person person, @PathVariable String id) {
        personService.updatePerson(id, person);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/person/{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/person/encontrar/{ci}")
    public Object encontrarci(@PathVariable String ci) {
        return personService.encontrarci(ci);
    }
}
