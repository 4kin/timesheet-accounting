package ru.sevmash.timesheetaccounting.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sevmash.timesheetaccounting.domain.Person;
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
        return personService.getAllUsers();
    }
}
