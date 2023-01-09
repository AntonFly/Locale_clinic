package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PwdDropRequestNotFoundException extends Exception {

    private final String message;

}