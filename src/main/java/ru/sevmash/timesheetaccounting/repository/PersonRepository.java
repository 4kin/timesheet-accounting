package ru.sevmash.timesheetaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevmash.timesheetaccounting.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}