package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception{

    private final String message;

    public UserNotFoundException(String paramName, String param)
    { message = "Не найдено пользователя с " + paramName + " : " + param; }
}
