package com.clinic.exceptions;

import com.clinic.entities.Passport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PassportConflictException extends Exception {

    private final String message;

    public PassportConflictException(long num)
    { message = "Пасспорт с номером " + num + " уже зарегистрирован"; }
}
