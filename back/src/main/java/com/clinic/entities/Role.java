package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "user_role")
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    protected Long id;
    private String role;

    @Transient
    @ManyToMany
    private Set<User> users;

    public Role() {
    }
    public Role(long id) {
        this.id = id;
    }
    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String name) {
        this.role = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}
