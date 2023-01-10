package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "implants")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Implant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private long number;

    @Temporal(TemporalType.DATE)
    private Date implantation_date;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "client_implant",
            joinColumns = @JoinColumn(name = "id_implant"),
            inverseJoinColumns = @JoinColumn(name = "id_client"))
    Set<Client> clients;

}
