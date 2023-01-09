package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.bouncycastle.math.raw.Mod;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Table(name = "clients")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String email;

    private String comment;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person person;

    @JsonBackReference
    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "client_implant",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_implant"))
    Set<Implant> implants;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "client_modification",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_mod"))
    Set<Modification> modifications;


}
