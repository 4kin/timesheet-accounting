package ru.sevmash.timesheetaccounting.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * A DTO for the {@link Person} entity
 */
@Data
@AllArgsConstructor
public class PersonDto implements Serializable {
    @Size(max = 60)
    private final String firstName;
    private final String secondName;
    private final String otchestvo;
    @NotNull(message = "Дата должна быть не пустой")
    private final Date dateOfBirth;
    private final int personNumber;
//    private final Set<TimeSheetDto> timeSheets;
    private final Long id;
}