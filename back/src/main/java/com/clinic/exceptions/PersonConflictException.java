package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class PersonConflictException extends Exception {

    private final String message;

    public PersonConflictException(long id)
    { message = "Уже зарегистрирована персона с id: " + id + ", но с другими параметрами"; }

}
