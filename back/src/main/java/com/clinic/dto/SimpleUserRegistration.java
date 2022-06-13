package com.clinic.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SimpleUserRegistration {

    @NotNull
    @NotEmpty
    private SimplePersonRegistration personData;

    @NotNull
    @NotEmpty
    private String role;

    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String password;

    public SimplePersonRegistration getPersonData() {
        return personData;
    }

    public void setPersonData(SimplePersonRegistration personData) {
        this.personData = personData;
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
