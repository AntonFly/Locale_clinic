package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
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
    private int id;

    private String name;

    private int price;

    private String currency;

    private String risk;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "modification_scenario",
            joinColumns = @JoinColumn(name = "mod_id"),
            inverseJoinColumns = @JoinColumn(name = "scanario_id")) //Anton moment gif
    Set<Scenario> scenarios;
}
