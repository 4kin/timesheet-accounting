package ru.sevmash.timesheetaccounting.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity(name = "time_sheet")
public class TimeSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Enumerated(EnumType.STRING)
    private TypesOfTimeEmun types;
    private Date date;
    private byte hours;
    private String fileName;
    private String notes;


}
