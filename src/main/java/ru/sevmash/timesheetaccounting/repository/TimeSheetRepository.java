package ru.sevmash.timesheetaccounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sevmash.timesheetaccounting.domain.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
}