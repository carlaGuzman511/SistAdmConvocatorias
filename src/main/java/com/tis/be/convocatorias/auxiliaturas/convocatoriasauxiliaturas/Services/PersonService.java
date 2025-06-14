package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Services;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.Person;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPerson(long id) {
        return personRepository.getById(id);
    }


    public void addPerson(Person person) {

        personRepository.save(person);
    }

    public void updatePerson(String ci, Person person) {
        personRepository.UpdateByCi(ci, person.getName(), person.getLastName(), person.getAddress(), person.getPhoneNumber(), person.getEmail());

    }

    public void deletePerson(String ci) {
        personRepository.DeleteByCi(ci);
    }

    public Object encontrarci(String ci){
        return personRepository.encontrarci(ci);
    }

}
