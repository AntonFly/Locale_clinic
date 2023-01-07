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
public class SimpleBodyChangesUpdate {

    @NotEmpty
    @NotNull
    @JsonProperty("order_id")
    private long orderId;

    @NotEmpty
    @NotNull
    @JsonProperty("changes")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> changes;

}
