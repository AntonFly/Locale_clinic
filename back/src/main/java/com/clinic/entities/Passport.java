package com.clinic.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "passports")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Passport {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_person")
    private Person person;

    @Id
    private long passport;

}
