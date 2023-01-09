package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PassportNotFoundException extends Exception {

    private final String message;

    public PassportNotFoundException(long id)
    { message = "There is no " + id + " passport registered"; }

}
