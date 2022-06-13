package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "modifications"})
@Table(name = "specializations")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Specialization {

    @Id
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "specializations")
    Set<Modification> modifications;

    @JsonBackReference
    @OneToMany(mappedBy = "specialization")
    private Set<Order> orders;

    @JsonBackReference
    @OneToMany(mappedBy = "specialization")
    private Set<Scenario> scenarios;

}
