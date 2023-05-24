package ru.sevmash.timesheetaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
//    @Query("select p from person p where p.deleted = false")
//    List<PersonEntity> findAllPersons();

    List<PersonEntity> findAllByDeletedIsFalse();
    List<PersonEntity> findAllByDeletedIsTrue();


}