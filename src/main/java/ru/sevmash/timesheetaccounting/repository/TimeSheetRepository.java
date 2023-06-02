package ru.sevmash.timesheetaccounting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.sevmash.timesheetaccounting.domain.TimeSheetEntity;

import java.util.List;

public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, Long> {
    List<TimeSheetEntity> findAllByPerson_IdAndDeletedIsTrue(Long id, Sort sort);

    List<TimeSheetEntity> findAllByPerson_IdAndDeletedIsFalse(Long id, Sort sort);

    @Query("select t from time_sheet t where t.person.id = ?1 ")
    List<TimeSheetEntity> findByPersonId(@NonNull Long id, Sort sort);



    @Transactional
    @Modifying
    @Query("update time_sheet t set t.deleted = ?2 where t.id = ?1")
    int setTimeSheetEntityIsDeleted(Long id, Boolean aBoolean);


    List<TimeSheetEntity> findAllByDeletedIsFalse();

    //todo сделать отображение удаленных записей


}