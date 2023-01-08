package com.clinic.exceptions;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler(
            {
                    ClientConflictException.class,
                    UserConflictException.class,
                    PersonConflictException.class,
                    ModSpecConflictException.class,
                    StockConflictException.class
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
                    SpecializationNotFoundException.class,
                    ModificationNotFoundException.class,
                    ClientNotFoundException.class,
                    OrderNotFoundExceprion.class,
                    StockNotFoundException.class,
                    UserNotFoundException.class
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
                    InvalidStockDataException.class
            })
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInvalidDataException(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(BAD_REQUEST)
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