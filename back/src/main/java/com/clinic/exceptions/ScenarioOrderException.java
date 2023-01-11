package com.clinic.exceptions;

import lombok.Getter;

@Getter
public class ScenarioOrderException extends Exception {

    private final String message;

    public ScenarioOrderException(long orderId)
    { message = "Уже зарегистрирован сценарий для заказа с id: " + orderId; }
}
