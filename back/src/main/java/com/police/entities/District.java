package com.police.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "District")
@Entity
public class District {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long area;

    private Long crimeNumber;

    private Long population;

    @JsonIgnore
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Location> locations = new ArrayList<>();

    public District() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getCrimeNumber() {
        return crimeNumber;
    }

    public void setCrimeNumber(Long crimeNumber) {
        this.crimeNumber = crimeNumber;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
