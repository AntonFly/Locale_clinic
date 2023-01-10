package com.clinic.exceptions;

import lombok.Getter;

@Getter
public class NoPersonToClientException extends Exception {

    private final String message;
    public NoPersonToClientException(long id)
    { message = "Не было найдено клиента для персоны с id: " + id; }

}
