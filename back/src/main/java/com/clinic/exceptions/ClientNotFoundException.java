package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ClientNotFoundException extends Exception {

    private final String message;

    public ClientNotFoundException(long id)
    { message = "Не было найдено клиента для персоны с id: " + id; }

}
