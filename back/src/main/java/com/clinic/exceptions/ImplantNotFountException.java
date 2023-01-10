package com.clinic.exceptions;

import lombok.Getter;

@Getter
public class ImplantNotFountException extends Exception {

    private final String message;

    public ImplantNotFountException(long id)
    { message = "Не было найдено импланта с id: " + id; }
}
