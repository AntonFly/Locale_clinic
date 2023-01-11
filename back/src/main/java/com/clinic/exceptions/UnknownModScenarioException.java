package com.clinic.exceptions;

import lombok.Getter;

@Getter
public class UnknownModScenarioException extends Exception {

    private final String message;

    public UnknownModScenarioException(long modId)
    { message = "В сценарии отсутствует модификация с id: " + modId; }
}
