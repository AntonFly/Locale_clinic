package com.clinic.exceptions;

import lombok.*;

@AllArgsConstructor
@Getter
public class UserConflictException extends Exception {

    private final String message;

}
