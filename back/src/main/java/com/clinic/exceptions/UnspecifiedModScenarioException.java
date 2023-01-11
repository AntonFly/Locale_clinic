package com.clinic.exceptions;

import lombok.Getter;

@Getter
public class UnspecifiedModScenarioException extends Exception {

    private final String message;

    public UnspecifiedModScenarioException(long modId)
    { message = "Отсутствует приоритет для модификации с id: " + modId; }

}
