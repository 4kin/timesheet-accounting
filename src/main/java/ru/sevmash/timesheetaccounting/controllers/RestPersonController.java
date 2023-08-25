package ru.sevmash.timesheetaccounting.controllers;


import org.springframework.web.bind.annotation.*;
import ru.sevmash.timesheetaccounting.aspesct.ToLog;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.services.PersonService;

import java.util.List;

@RestController
@RequestMapping({"/api/person"})
public class RestPersonController {
    private final PersonService personService;

    public RestPersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("")
    @ToLog
    public List<PersonDto> getAllPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    @ToLog
    public PersonEntity getPerson(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/deleted")
    @ToLog
    public List<PersonDto> getAllDeletedPerson() {
        return personService.getAllDeletedPersons();
    }


    @DeleteMapping("/{id}")
    @ToLog
    public PersonDto deletePerson(@PathVariable Long id) {
        return personService.setDeletedPersonById(id);
    }

    @DeleteMapping("/{id}/restore")
    @ToLog
    public PersonDto unDeletePerson(@PathVariable Long id) {
        return personService.restoreDeletedPerson(id);
    }


    @PostMapping("")
    @ToLog
    public PersonDto newPerson(@RequestBody PersonDto personDto) {
        return personService.addNewPerson(personDto);
    }

    @PutMapping("")
    @ToLog
    public PersonDto updatePerson(@RequestBody PersonDto personDto) {
        return personService.updatePerson(personDto);
    }


}
