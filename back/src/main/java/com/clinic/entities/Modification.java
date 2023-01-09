package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Table(name = "modifications")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Modification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String currency;

    private String risk;

    private BigDecimal chance;

    private String accompaniment;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "spec_mod",
            joinColumns = @JoinColumn(name = "id_mod"),
            inverseJoinColumns = @JoinColumn(name = "id_spec"))
    Set<Specialization> specialization;


    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "client_modification",
            joinColumns = @JoinColumn(name = "id_mod"),
            inverseJoinColumns = @JoinColumn(name = "id_client"))
    Set<Client> clients;

    @JsonBackReference
    @OneToMany(
            mappedBy = "modification",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ModificationScenario> modificationScenarios;
}
