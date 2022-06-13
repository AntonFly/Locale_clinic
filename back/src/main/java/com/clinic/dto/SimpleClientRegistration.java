package com.clinic.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleClientRegistration {

    @NotNull
    @NotEmpty
    private SimplePersonRegistration person;

    @NotNull
    @NotEmpty
    private String email;

    private String comment;

}
