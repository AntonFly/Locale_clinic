package com.clinic.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleOrderRegistration {

    @NotEmpty
    @NotNull
    private Long clientId;

    @NotEmpty
    @NotNull
    private Long specId;

    @NotEmpty
    @NotNull
    private List<Long> modIds;

    private String comment;
}
