package ru.sevmash.timesheetaccounting.domain;

import lombok.Data;

import java.sql.Date;


/**
 * A DTO for the {@link PersonEntity} entity
 */
@Data
public class PersonDto  {
    private Long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Date dateOfBirth;
    private int personNumber;
    private boolean deleted;
}