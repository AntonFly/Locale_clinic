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
                    PassportConflictException.class,
                    PersonConflictException.class,
                    StockConflictException.class,
                    UserConflictException.class
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
                    BodyChangeNotFoundException.class,
                    ClientNotFoundException.class,
                    ConfirmationNotFoundException.class,
                    FileNotFoundException.class,
                    ImplantNotFountException.class,
                    ModificationNotFoundException.class,
                    OrderNotFoundException.class,
                    PassportNotFoundException.class,
                    PwdDropRequestNotFoundException.class,
                    ScenarioOrderException.class,
                    SpecializationNotFoundException.class,
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
                    InvalidStockDataException.class,
                    NoPersonToClientException.class,
                    NoScenarioForOrderException.class,
                    UnknownModScenarioException.class,
                    UnspecifiedModScenarioException.class
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
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(INTERNAL_SERVER_ERROR)
                .timestamp(now())
                .build();
    }

}