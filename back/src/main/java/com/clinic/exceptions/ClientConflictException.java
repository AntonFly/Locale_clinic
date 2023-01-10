package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ClientConflictException extends Exception {

    private final String message;

    public ClientConflictException(long personId)
    { message = "There is already a client associated with person with id: " + personId; }

}
