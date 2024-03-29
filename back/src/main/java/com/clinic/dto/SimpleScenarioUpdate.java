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
    @JsonProperty("mods")
    private List<SimpleModPriority> mods;

}
