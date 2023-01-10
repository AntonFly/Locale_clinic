package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ModificationNotFoundException extends Exception {

    private final String message;

    public ModificationNotFoundException(long id)
    { message = "Не найдено модификации с id: " + id; }

}
