package ru.sevmash.timesheetaccounting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity(name = "person")
@Table(indexes = {
        @Index(name = "idx_person_firstname", columnList = "firstName"),
        @Index(name = "idx_personentity_deleted", columnList = "deleted")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 60)
    private String firstName;
    private String secondName;
    private String middleName;
    @NotNull(message = "Дата должна быть не пустой")
    private Date dateOfBirth;
    private int personNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    @ToString.Exclude
    @JsonIgnore
    // TODO проверить работу для EAGER
    private Set<TimeSheetEntity> timeSheetEntities;
    private boolean deleted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PersonEntity that = (PersonEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
