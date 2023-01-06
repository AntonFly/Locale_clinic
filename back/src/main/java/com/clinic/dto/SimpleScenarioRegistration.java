package com.clinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleScenarioRegistration {

    @NotEmpty
    @NotNull
    private int specId;

    @NotEmpty
    @NotNull
    @JsonProperty("modIds")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<Integer> modIds;

}
