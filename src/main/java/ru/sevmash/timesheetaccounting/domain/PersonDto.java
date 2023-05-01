package ru.sevmash.timesheetaccounting.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link PersonEntity} entity
 */
@Data
public class PersonDto implements Serializable {
    private Long id;
    private String firstName;
    private String secondName;
    private String otchestvo;
    private Date dateOfBirth;
    private int personNumber;
}