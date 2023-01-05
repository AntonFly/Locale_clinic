package com.clinic.exceptions;


import com.clinic.entities.Specialization;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestControllerAdvicor {

    @ExceptionHandler(
            {
                    ClientConflictException.class,
                    UserConflictException.class,
                    PersonConflictException.class,
                    ModSpecConflictException.class
            })
    @ResponseStatus(CONFLICT)
    public ErrorResponse handleConflictException(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(CONFLICT)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(
            {
                    SpecializationMissingException.class,
                    ModificationMissingException.class,
                    ClientNotFoundException.class,
                    OrderNotFoundExceprion.class
            })
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleMissingException(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(
            {
                    Exception.class
            })
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleOtherException(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(BAD_REQUEST)
                .timestamp(now())
                .build();
    }

}