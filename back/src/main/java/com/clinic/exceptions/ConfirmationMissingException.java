package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ConfirmationMissingException extends Exception {

    private final String message;

    public ConfirmationMissingException(long id)
    { message = "There is no confirmation for order with id: " + id; }

}
