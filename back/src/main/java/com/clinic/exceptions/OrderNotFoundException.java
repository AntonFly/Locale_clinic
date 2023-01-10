package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends Exception {

    private final String message;

    public OrderNotFoundException(long id)
    { message = "Не найдено заказа с id: " + id; }
}
