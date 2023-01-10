package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PwdDropRequestNotFoundException extends Exception {

    private final String message;

    public PwdDropRequestNotFoundException(long id)
    { message = "Не было найдено заявки на сброс пароля с id: " + id; }

}
