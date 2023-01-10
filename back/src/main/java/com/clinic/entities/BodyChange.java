package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "body_changes")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BodyChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String change;

    private String description;

    private String symptoms;

    private String actions;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
