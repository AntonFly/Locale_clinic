package com.clinic.dto;


import com.clinic.entities.Person;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExistingPersonClientRegistration {

    @NotNull
    @NotEmpty
    private Person person;

    @NotNull
    @NotEmpty
    private String email;

    private String comment;
}
