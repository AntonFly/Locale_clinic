package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "modifications"})
@Table(name = "specializations")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Specialization {

    @Id
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "specializations")
    Set<Modification> modifications;

    @JsonBackReference
    @OneToMany(mappedBy = "specialization")
    private Set<Order> orders;

}
