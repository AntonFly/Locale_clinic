package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Table(name = "orders")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String comment;

    private String confirmation;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "accompaniment_script")
    private AccompanimentScript accompanimentScript;

    private String genome;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "spec")
    private Specialization specialization;

    @ManyToMany
    @JoinTable(
            name = "orders_modifications",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_mod"))
    Set<Modification> modifications;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<BodyChange> bodyChanges;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Scenario scenario;

}
