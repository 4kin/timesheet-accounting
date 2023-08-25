package ru.sevmash.timesheetaccounting.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.sevmash.timesheetaccounting.convertor.PersonConverter;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

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
        personEntity.setId(1L);
        personEntity.setDeleted(false);
        personEntity.setFirstName("Nikita");
        personEntity.setSecondName("Fokin");
    }

    @Test
    @DisplayName("Поиск персоны по ИД")
    void getPersonById() {
        given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.getPersonById(personEntity.getId());

        verify(personRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Поиcк персоны по ИД. Выброс исключения.")
    void getPersonByIdException() {
        given(personRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> personService.getPersonById(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Not Found");
    }


    @Test
    void updatePerson() {
    }


    @Test
    @DisplayName("Удаляем человека по ИД")
    void setDeletedPersonById() {

        given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.setDeletedPersonById(1L);
        assertThat(personEntity.isDeleted()).isTrue();
        verify(personRepository).findById(1L);
        verify(personRepository).save(personEntity);
    }

    //todo написать тест с выкидом ошибки

    @Test
    @DisplayName("Восстанавливаем человека по ИД")
    void restoreDeletedPerson() {
        personEntity.setDeleted(true);
        given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.restoreDeletedPerson(personEntity.getId());

        verify(personRepository).save(any());
        assertThat(personEntity.isDeleted()).isFalse();
    }

    @Test
    void testUpdatePerson() {
        PersonDto personDto = new PersonDto();
        personDto.setPersonNumber(123);
        personDto.setId(1L);
        personDto.setMiddleName("Александрович");

        //todo написать тест
        given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));
        given(personConverter.toEntity(personDto)).willReturn(personEntity);
        given(personConverter.toDto(personEntity)).willReturn(personDto);
        given(personRepository.save(personEntity)).willReturn(personEntity);


        PersonDto returnPersonDto = personService.updatePerson(personDto);


        verify(personRepository).save(personEntity);

        assertThat(returnPersonDto.getId()).isEqualTo(1L);
        assertThat(returnPersonDto.getPersonNumber()).isEqualTo(123);


    }
}