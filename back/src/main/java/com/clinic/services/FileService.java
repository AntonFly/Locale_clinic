package com.clinic.services;

import com.clinic.exceptions.FileNotFoundException;

import java.io.File;

public interface FileService {

    File getFile(String filename)
            throws FileNotFoundException;

    boolean exists(String filename);
}
