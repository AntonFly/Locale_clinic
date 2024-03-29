package com.clinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
