package io.zipcoder.crudapp.Controller;

import io.zipcoder.crudapp.Model.Person;
import io.zipcoder.crudapp.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository personRepository;


    public PersonController(PersonRepository repository) {
        this.personRepository = repository;
    }


    @PostMapping("/people")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        return new ResponseEntity<>(personRepository.findOne(id),HttpStatus.OK);
    }

    @GetMapping("/people/")
    public ResponseEntity<List<Person>> getPersonList() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(x -> personList.add(x));
        return new ResponseEntity<>(personList,HttpStatus.OK);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        if(personRepository.findOne(person.getId()) != null) {
            personRepository.delete(person.getId());
            return new ResponseEntity<>(personRepository.save(person),HttpStatus.OK);
        }
        return new ResponseEntity<>(personRepository.save(person),HttpStatus.CREATED);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable int id) {
        personRepository.delete(id);
        return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);

    }
}
