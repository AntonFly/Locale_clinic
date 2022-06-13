package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "specialization_scenarios")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scenarios;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "specialization")
    private Specialization specialization;

}
