package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "specializations", "orders"})
@Table(name = "modifications")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Modification {

    @Id
    private String name;

    private int price;

    private String currency;

    @ManyToMany
    @JoinTable(
            name = "modification_specialization",
            joinColumns = @JoinColumn(name = "mod"),
            inverseJoinColumns = @JoinColumn(name = "spec"))
    Set<Specialization> specializations;

    @JsonIgnore
    @ManyToMany(mappedBy = "modifications")
    Set<Order> orders;
}
