package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "person")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String surname;
    private String patronymic;

    @Temporal(TemporalType.DATE)
    Date dateOfBirth;

    @JsonManagedReference
    @OneToMany(mappedBy = "person")
    private List<Passport> passports;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Person)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Person c = (Person) o;

        return id == c.id &&
                Objects.equals(name, c.name) &&
                Objects.equals(surname, c.surname) &&
                Objects.equals(patronymic, c.patronymic) &&
                dateOfBirth == c.dateOfBirth;
    }
}
