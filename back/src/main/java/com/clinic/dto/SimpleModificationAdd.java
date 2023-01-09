package com.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@Getter
public class SimpleModificationAdd {
    @NotEmpty
    @NotNull
    private  long clientId;

    @NotEmpty
    @NotNull
    private long[] modIds;
}
