package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class PersonConflictException extends Exception {
    private final String message;

}
