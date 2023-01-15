package com.clinic.exceptions;

import lombok.Getter;

@Getter
public class ConfirmationNotFoundException extends Exception {

    private final String message;

    public ConfirmationNotFoundException(long id)
    { message = "There is no confirmation for order with id: " + id; }

}
