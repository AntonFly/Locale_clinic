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
    private int specId;

    @NotEmpty
    @NotNull
    @JsonProperty("order_id")
    private int orderId;

    @NotEmpty
    @NotNull
    @JsonProperty("mod_ids")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<Integer> modIds;

}
