package com.clinic.exceptions;

public class NoScenarioForOrderException extends Exception {

    private final String message;

    public NoScenarioForOrderException(long id)
    { message = "Заказ с id: " + id + ", не содержит сценария"; }
}
