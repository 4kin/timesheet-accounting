package ru.sevmash.timesheetaccounting.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sevmash.timesheetaccounting.convertor.PersonConverter;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonConverter personConverter;

    @InjectMocks
    private PersonService personService;

    @Test
    void getAllPersons() {
    }

    @Test
    void getPersonById() {
    }

    @Test
    void getAllDeletedPersons() {
    }

    @Test
    void addNewPerson() {
    }

    @Test
    void updatePerson() {
    }

    PersonEntity personEntity;

    @BeforeEach
    void setUp() {
        personEntity = new PersonEntity();
        personEntity.setId(1l);
        personEntity.setDeleted(false);
        personEntity.setFirstName("Nikita");
        personEntity.setSecondName("Fokin");

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

    @Test
    @DisplayName("Востанавливаем человека по ИД")
    void restoreDeletedPerson() {
        personEntity.setDeleted(true);
        BDDMockito.given(personRepository.findById(personEntity.getId())).willReturn(Optional.of(personEntity));

        personService.restoreDeletedPerson(personEntity.getId());

        BDDMockito.verify(personRepository).save(any());
        assertThat(personEntity.isDeleted()).isFalse();
    }
}