package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PwdDropRequestAlreadySatisfiedException extends Exception {

    private final String message;

}
