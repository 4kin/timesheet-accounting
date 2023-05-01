package ru.sevmash.timesheetaccounting.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "person")
@Table(indexes = {
        @Index(name = "idx_person_firstname", columnList = "firstName")
})
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 60)
    private String firstName;
    private String secondName;
    private String otchestvo;
    @NotNull(message = "Дата должна быть не пустой")
    private Date dateOfBirth;
    private int personNumber;

    @OneToMany(mappedBy = "person")
    private Set<TimeSheet> timeSheets;



}