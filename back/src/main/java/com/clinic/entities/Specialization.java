package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "specializations")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "spec_mod",
            joinColumns = @JoinColumn(name = "id_spec"),
            inverseJoinColumns = @JoinColumn(name = "id_mod"))
    Set<Modification> modifications;

    @JsonBackReference
    @OneToMany(mappedBy = "specialization")
    private List<Scenario> scenarios;

}
