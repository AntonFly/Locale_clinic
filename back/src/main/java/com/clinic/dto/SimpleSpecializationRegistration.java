package com.clinic.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleSpecializationRegistration {

    @NotNull
    @NotEmpty
    private String name;
}
