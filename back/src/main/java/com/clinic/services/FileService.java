package com.clinic.services;

import com.clinic.exceptions.ConfirmationMissingException;
import com.clinic.exceptions.FileMissingException;

import java.io.File;

public interface FileService {

    File getFile(String filename)
            throws FileMissingException;

    boolean exists(String filename);
}
