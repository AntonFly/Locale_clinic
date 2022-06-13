package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_spec")
    private Specialization specialization;

    @ManyToMany
    @JoinTable(
            name = "orders_modifications",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_mod"))
    Set<Modification> modifications;

}
