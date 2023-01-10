package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BodyChangeNotFoundException extends Exception {

    private final String message;

    public BodyChangeNotFoundException(long id)
    { message = "There are no body changes with id: " + id; }
}
