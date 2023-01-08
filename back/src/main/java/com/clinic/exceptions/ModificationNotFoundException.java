package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModificationNotFoundException extends Exception {

    private final String message;

}
