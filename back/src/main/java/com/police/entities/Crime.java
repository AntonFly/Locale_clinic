package com.police.entities;

import com.fasterxml.jackson.annotation.*;
import com.police.entities.enums.CrimeType;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Table(name = "Crime")
@Entity
public class Crime {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "criminal", nullable = true)
    private Person criminal;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "crimeLocation", nullable = false)
    private Location crimeLocation;

    @Enumerated(EnumType.STRING)
    private CrimeType type;

    public Crime() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getCriminal() {
        return criminal;
    }

    public void setCriminal(Person criminal) {
        this.criminal = criminal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getCrimeLocation() {
        return crimeLocation;
    }

    public void setCrimeLocation(Location crimeLocation) {
        this.crimeLocation = crimeLocation;
    }

    public CrimeType getType() {
        return type;
    }

    public void setType(CrimeType type) {
        this.type = type;
    }
}
