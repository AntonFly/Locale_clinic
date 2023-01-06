package com.clinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleStockCreate {
    @NotEmpty
    @NotNull
    private int id;

    @NotEmpty
    @NotNull
    @JsonProperty("vendor_id")
    private int vendorId;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private int amount;

    @NotEmpty
    @NotNull
    private String description;

    @NotEmpty
    @NotNull
    private int minAmount;

    @JsonProperty("user_id")
    @NotEmpty
    @NotNull
    private long userId;
}

