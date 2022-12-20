package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "scenario")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Specialization specialization;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "modification_scenario",
            joinColumns = @JoinColumn(name = "scanario_id"),
            inverseJoinColumns = @JoinColumn(name = "mod_id"))
    Set<Modification> modifications;

}
