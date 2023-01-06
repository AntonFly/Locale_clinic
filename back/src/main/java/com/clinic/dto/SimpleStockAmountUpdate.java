package com.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleStockAmountUpdate {
    @NotEmpty
    @NotNull
    private int id;

    @NotEmpty
    @NotNull
    @JsonProperty("vendor_id")
    private int vendorId;

    @NotEmpty
    @NotNull
    private int change;
}
