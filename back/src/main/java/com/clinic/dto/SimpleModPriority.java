package com.clinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleModPriority {

    @NotEmpty
    @NotNull
    @JsonProperty("id")
    private long id;

    @NotEmpty
    @NotNull
    @JsonProperty("priority")
    private long priority;

}
