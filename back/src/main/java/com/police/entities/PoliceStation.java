package com.police.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Police_Station")
@Entity
public class PoliceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stationLocation")
    private Location stationLocation;

    @Basic
    private Time openingTime;

    @Basic
    private Time closingTime;

    @JsonIgnore
    private Long budget;

    @JsonIgnore
    @OneToMany(mappedBy = "policeStation")
    private List<Officer> officers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "policeStation")
    private List<WorkExperience> workExperiences = new ArrayList<>();

    public PoliceStation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getStationLocation() {
        return stationLocation;
    }

    public void setStationLocation(Location stationLocation) {
        this.stationLocation = stationLocation;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public List<Officer> getOfficers() {
        return officers;
    }

    public void setOfficers(List<Officer> officers) {
        this.officers = officers;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }
}
