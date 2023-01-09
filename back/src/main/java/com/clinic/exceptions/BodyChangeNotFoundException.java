package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BodyChangeNotFoundException extends Exception {

    private final String message;
}
