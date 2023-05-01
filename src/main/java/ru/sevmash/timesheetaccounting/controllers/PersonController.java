package ru.sevmash.timesheetaccounting.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
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

    @GetMapping("/person/{id}")
    public PersonDto getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping("/person/update")
    public void savePerson(@RequestBody PersonDto personDto) {
        personService.save(personDto);
    }



}
