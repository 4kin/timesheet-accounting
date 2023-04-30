package ru.sevmash.timesheetaccounting.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    private TypesOfTimeEmun types;
    private Date date;
    private byte hours;
    private String fileName;
    private String notes;


}
