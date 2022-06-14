package com.clinic.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SimpleUserRegistration {

    @NotNull
    @NotEmpty
    private SimplePersonRegistration person;

    @NotNull
    @NotEmpty
    private String role;

    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String password;

    public SimplePersonRegistration getPerson() {
        return person;
    }

    public void setPerson(SimplePersonRegistration person) {
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
