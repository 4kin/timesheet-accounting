package ru.sevmash.timesheetaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevmash.timesheetaccounting.domain.TimeSheetEntity;

public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, Long> {
}