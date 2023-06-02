package ru.sevmash.timesheetaccounting.controllers;


import org.springframework.web.bind.annotation.*;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.services.PersonService;

import java.util.List;

@RestController
//@CrossOrigin
public class RestPersonController {
    private final PersonService personService;

    public RestPersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<PersonDto> getAllPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/person/{id}")
    public PersonEntity getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/persons/deleted")
    public List<PersonDto> allDeletedPerson() {
        return personService.getAllDeletedPersons();
    }


    @DeleteMapping("/person/{id}")
    public PersonDto deletePerson(@PathVariable Long id) {
        return personService.setDeletedPersonById(id);
    }

    @DeleteMapping("/person/undelete/{id}")
    public PersonDto unDeletePerson(@PathVariable Long id) {
        return personService.setUnDeletedPersonById(id);
    }


    @PostMapping("/person")
    public PersonDto newPerson(@RequestBody PersonDto personDto) {
        return personService.save(personDto);
    }

    @PutMapping("/person/{id}")
    public PersonDto updatePerson(@RequestBody PersonDto personDto) {
        return personService.save(personDto);
    }


}
