package com.clinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleScenarioUpdate {

    @NotEmpty
    @NotNull
    @JsonProperty("scenario_id")
    private long scenarioId;

    @NotEmpty
    @NotNull
    @JsonProperty("spec_id")
    private long specId;

    @NotEmpty
    @NotNull
    @JsonProperty("mod_ids")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<Long> modIds;

}
