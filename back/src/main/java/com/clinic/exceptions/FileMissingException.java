package com.clinic.exceptions;

import lombok.Getter;

import java.io.File;

@Getter
public class FileMissingException extends Exception {

    private final String message;

    public FileMissingException(File file)
    { message = "Отсутствует файл по пути: " + file.getAbsolutePath(); }

}
