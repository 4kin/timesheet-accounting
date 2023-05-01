package ru.sevmash.timesheetaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}