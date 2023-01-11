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
    @JsonProperty("spec_id")
    private long specId;

    @NotEmpty
    @NotNull
    @JsonProperty("order_id")
    private long orderId;

    @NotEmpty
    @NotNull
    @JsonProperty("mods")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<Long> mods;

}
