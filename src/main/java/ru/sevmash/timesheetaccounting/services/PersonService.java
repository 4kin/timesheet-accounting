package ru.sevmash.timesheetaccounting.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sevmash.timesheetaccounting.convertor.PersonConverter;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonConverter personConverter;


    public PersonService(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    public List<PersonDto> getAllPersons() {

        return personRepository.findAllByDeletedIsFalse()
                .stream()
                .map(personConverter::toDto)
                .collect(Collectors.toList());
    }

    public PersonEntity getPersonById(Long id) {
        Optional<PersonEntity> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person id = " + id + " Not Found");
        }
    }

    public List<PersonDto> getAllDeletedPersons() {

        return personRepository.findAllByDeletedIsTrue()
                .stream()
                .map(personConverter::toDto)
                .collect(Collectors.toList());
    }


    public PersonDto addNewPerson(PersonDto personDto) {
        return personConverter.toDto(
                personRepository.save(
                        personConverter.toEntity(personDto)
                )
        );
    }


    public PersonDto updatePerson(PersonDto personDto) {
        personRepository.findById(personDto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person id  = " + personDto.getId() + "not found"));
        PersonEntity person = personConverter.toEntity(personDto);
        PersonEntity saved = personRepository.save(person);
        return personConverter.toDto(saved);
    }

    public PersonDto setDeletedPersonById(Long id) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person id = " + id + " Not Found"));
        personEntity.setDeleted(true);
        return personConverter.toDto(personRepository.save(personEntity));
    }

    public PersonDto restoreDeletedPerson(Long id) {
        PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person id = " + id + " Not Found"));
        personEntity.setDeleted(false);
        return personConverter.toDto(personRepository.save(personEntity));
    }


}
