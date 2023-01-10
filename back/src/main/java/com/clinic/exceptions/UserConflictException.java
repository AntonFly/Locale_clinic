package com.clinic.exceptions;

import lombok.*;

@Getter
public class UserConflictException extends Exception {

    private final String message;

    public UserConflictException(String param)
    { message = "Уже существует пользователь с параметром: " + param; }

}
