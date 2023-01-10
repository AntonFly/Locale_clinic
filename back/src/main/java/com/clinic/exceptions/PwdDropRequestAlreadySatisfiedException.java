package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;

@Getter
public class PwdDropRequestAlreadySatisfiedException extends Exception {

    private final String message;

    public PwdDropRequestAlreadySatisfiedException(long id, Calendar date)
    { message = "Запрос на сброс пароля с id: " + id + " уже был удовлетворен " + date; }

}
