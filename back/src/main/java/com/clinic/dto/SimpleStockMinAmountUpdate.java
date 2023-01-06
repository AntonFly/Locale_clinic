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
public class SimpleStockMinAmountUpdate {
    @NotEmpty
    @NotNull
    private int id;

    @NotEmpty
    @NotNull
    @JsonProperty("vendor_id")
    private int vendorId;

    @NotEmpty
    @NotNull
    @JsonProperty("min_amount")
    private int minAmount;
}
