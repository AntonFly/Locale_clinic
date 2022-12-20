package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "accompaniment_script")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccompanimentScript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String scenarios;

    @JsonBackReference
    @OneToMany(mappedBy = "accompanimentScript")
    private Set<Order> orders;
}
