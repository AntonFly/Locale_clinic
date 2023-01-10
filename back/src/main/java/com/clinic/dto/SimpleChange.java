package com.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleChange {
    @NotNull
    @NotEmpty
    private long id;
    @NotNull
    @NotEmpty
    private String change;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String symptoms;
    @NotNull
    @NotEmpty
    private String actions;
}
