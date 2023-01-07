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
    private int id;

    private String change;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
