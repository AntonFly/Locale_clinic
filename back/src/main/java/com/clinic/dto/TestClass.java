package com.clinic.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TestClass
{
    @NotNull
    @NotEmpty
    String id;

}
