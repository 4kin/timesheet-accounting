package ru.sevmash.timesheetaccounting.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity(name = "person")
@Table(indexes = {
        @Index(name = "idx_person_firstname", columnList = "firstName"),
        @Index(name = "idx_personentity_deleted", columnList = "deleted")
})
@Data
public class PersonEntity {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    // TODO проверить работу для EAGER
    private Set<TimeSheetEntity> timeSheetEntities;
    private boolean deleted;



}
