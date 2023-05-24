package ru.sevmash.timesheetaccounting.controllers;


import org.springframework.web.bind.annotation.*;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.services.PersonService;

import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<PersonDto> allPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/deleted_persons")
    public List<PersonDto> allDeletedPerson() {
        return personService.getAllDeletedPersons();
    }


    @PostMapping("/person")
    public PersonDto createPerson(@RequestBody PersonDto personDto) {
        return personService.createPersons(personDto);
    }

    @GetMapping("/person/{id}/delete")
    public PersonDto setDeletedPerson(@PathVariable Long id) {
        return personService.setDeletedPersonById(id);
    }

    @GetMapping("/person/{id}/undelete")
    public PersonDto setUnDeletedPerson(@PathVariable Long id) {
        return personService.setUnDeletedPersonById(id);
    }

    @GetMapping("/person/{id}")
    public PersonEntity getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PatchMapping("/person/{id}")
    public PersonDto updatePerson(@RequestBody PersonDto personDto) {
        return personService.save(personDto);
    }


}
