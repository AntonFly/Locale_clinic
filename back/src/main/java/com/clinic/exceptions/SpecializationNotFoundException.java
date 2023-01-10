package com.clinic.exceptions;

import com.clinic.entities.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SpecializationNotFoundException extends Exception {

    private final String message;

    public SpecializationNotFoundException(long id)
    { message = "Не было найдено специализации с id: " + id; }

}
