package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "person")
@Entity
public class Person {

    @Id
    private long id;

    private String name;
    private String surname;
    private String patronymic;

    @Temporal(TemporalType.DATE)
    Date dateOfBirth;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
