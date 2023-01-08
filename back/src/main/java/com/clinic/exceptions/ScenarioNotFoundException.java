package com.clinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScenarioNotFoundException extends Exception {

    private final String message;

}
