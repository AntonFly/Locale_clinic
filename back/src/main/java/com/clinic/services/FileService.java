package com.clinic.services;

import com.clinic.exceptions.ConfirmationMissingException;

import java.io.File;

public interface FileService {

    File getFile(String filename) throws ConfirmationMissingException;

    boolean exists(String filename);
}
