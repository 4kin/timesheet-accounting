package ru.sevmash.timesheetaccounting.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sevmash.timesheetaccounting.convertor.PersonConverter;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonConverter personConverter;

    public PersonService(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }


    public PersonDto getPersonById(Long id) {
        //TODO обработать пустой оптионал
        return personConverter.toDto(personRepository.findById(id).get());
    }

    public List<PersonDto> getAllPersons() {
        //TODO разаобраться с ощибкой
        return personRepository.findAll()
                .stream()
                .map(personConverter::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public PersonDto save(PersonDto personDto) {
        //TODO сделать проверку что есть персона в базе
        PersonEntity person = personConverter.toEntity(personDto);
        PersonEntity saved = personRepository.save(person);
        return personConverter.toDto(saved);
    }
}
