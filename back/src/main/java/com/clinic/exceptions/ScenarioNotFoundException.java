package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ScenarioNotFoundException extends Exception {

    private final String message;

    public ScenarioNotFoundException(long id)
    { message = "Не было найдено сценария с id: " + id; }

}
