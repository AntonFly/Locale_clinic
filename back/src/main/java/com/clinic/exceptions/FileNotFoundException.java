package com.clinic.exceptions;

import lombok.Getter;

import java.io.File;

@Getter
public class FileNotFoundException extends Exception {

    private final String message;

    public FileNotFoundException(File file)
    { message = "Отсутствует файл по пути: " + file.getAbsolutePath(); }

}
