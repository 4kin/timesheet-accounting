package ru.sevmash.timesheetaccounting.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.sevmash.timesheetaccounting.convertor.PersonConverter;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    PersonEntity personEntity;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonConverter personConverter;
    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personEntity = new PersonEntity();
        personEntity.setId(1l);
        personEntity.setDeleted(false);
        personEntity.setFirstName("Nikita");
        personEntity.setSecondName("Fokin");
    }

    @Test
    @DisplayName("Поиск персоны по ИД")
    void getPersonById() {
        BDDMockito.given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.getPersonById(personEntity.getId());

        BDDMockito.verify(personRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Поиcк персоны по ИД. Выброс исключения.")
    void getPersonByIdException() {
        BDDMockito.given(personRepository.findById(1l)).willReturn(Optional.empty());

        assertThatThrownBy(() -> personService.getPersonById(1l))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Not Found");
    }


    @Test
    void updatePerson() {
    }


    @Test
    @DisplayName("Удаляем человека по ИД")
    void setDeletedPersonById() {

        BDDMockito.given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.setDeletedPersonById(1l);
        assertThat(personEntity.isDeleted()).isTrue();
        BDDMockito.verify(personRepository).findById(1l);
        BDDMockito.verify(personRepository).save(personEntity);
    }

    //todo написать тест с выкидом ошибки

    @Test
    @DisplayName("Восстанавливаем человека по ИД")
    void restoreDeletedPerson() {
        personEntity.setDeleted(true);
        BDDMockito.given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.restoreDeletedPerson(personEntity.getId());

        BDDMockito.verify(personRepository).save(any());
        assertThat(personEntity.isDeleted()).isFalse();
    }

    @Test
    void testUpdatePerson() {
        PersonDto personDto = new PersonDto();
        personDto.setPersonNumber(123);
        personDto.setId(1l);
        personDto.setMiddleName("Александрович");

        //todo написать тест
        BDDMockito.given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));
        BDDMockito.given(personConverter.toEntity(personDto)).willReturn(personEntity);
        BDDMockito.given(personConverter.toDto(personEntity)).willReturn(personDto);
        BDDMockito.given(personRepository.save(personEntity)).willReturn(personEntity);


        PersonDto returnPersonDto = personService.updatePerson(personDto);


        BDDMockito.verify(personRepository).save(personEntity);

        assertThat(returnPersonDto.getId()).isEqualTo(1l);
        assertThat(returnPersonDto.getPersonNumber()).isEqualTo(123);


    }
}