package com.clinic.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleOrderRegistration {

    @NotEmpty
    @NotNull
    private Long passport;

    @NotEmpty
    @NotNull
    private String specName;

    @NotEmpty
    @NotNull
    private String modName;
}
