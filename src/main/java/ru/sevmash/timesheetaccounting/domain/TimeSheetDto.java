package ru.sevmash.timesheetaccounting.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link TimeSheetEntity} entity
 */
@Data
public class TimeSheetDto implements Serializable {
    private final Long id;
    private final TypesOfTimeEmun types;
    private final Date date;
    private final byte hours;
    private final String fileName;
    private final String notes;
}