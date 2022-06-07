package com.police.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;

@Table(name = "usr")
@Entity
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @JsonBackReference(value = "officer_user")
    @OneToOne(mappedBy = "usr")
    private Officer officer;

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Officer getOfficer() {
        return officer;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
