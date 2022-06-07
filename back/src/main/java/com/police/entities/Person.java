package com.police.entities;


import com.fasterxml.jackson.annotation.*;
import com.police.entities.enums.Sex;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Person")
@Entity
public class Person {

    @Id
    private long passportNumber;

    private String name;

    private String surname;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonBackReference(value = "officer_person")
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Officer officer;

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @ManyToOne
    @JoinColumn(name = "registrationLocation")
    private Location registrationLocation;

    @JsonIgnore
    @OneToMany(mappedBy = "criminal", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Crime> crimes = new ArrayList<>();

    public Person() {
    }

    public long getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(long passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Officer getOfficer() {
        return officer;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public Location getRegistrationLocation() {
        return registrationLocation;
    }

    public void setRegistrationLocation(Location registrationLocation) {
        this.registrationLocation = registrationLocation;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public void setCrimes(List<Crime> crimes) {
        this.crimes = crimes;
    }
}
