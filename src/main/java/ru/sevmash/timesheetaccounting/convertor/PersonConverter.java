package ru.sevmash.timesheetaccounting.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;

@Component
public class PersonConverter {
    private final ModelMapper modelMapper;

    public PersonConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PersonDto toDto(PersonEntity person) {
        return modelMapper.map(person, PersonDto.class);
    }

    public PersonEntity toEntity(PersonDto personDto) {
        return modelMapper.map(personDto, PersonEntity.class);
    }


}
