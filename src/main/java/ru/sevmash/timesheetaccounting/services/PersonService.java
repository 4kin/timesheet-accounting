package ru.sevmash.timesheetaccounting.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sevmash.timesheetaccounting.domain.Person;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDto> getAllUsers() {
        //TODO разаобраться с ощибкой
        return personRepository.findAll()
                .stream()
                .map(PersonService::converToPersonDTO)
                .collect(Collectors.toList());
    }

    private static PersonDto converToPersonDTO(Person person) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(person , PersonDto.class);
    }

}
