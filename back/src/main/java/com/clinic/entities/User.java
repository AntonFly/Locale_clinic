package com.clinic.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @NotEmpty
    private String password;
    @NotEmpty
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person person;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private Set<PwdDropRequest> pwdDropRequests;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private Set<Stock> stock;
}
