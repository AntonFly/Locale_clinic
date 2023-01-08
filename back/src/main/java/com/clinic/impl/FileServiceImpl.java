package com.clinic.impl;

import com.clinic.exceptions.ConfirmationMissingException;
import com.clinic.services.FileService;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public File getFile(String filename)
            throws ConfirmationMissingException
    {
        File file = new File("D:\\" + filename);
        if (!file.exists())
            throw new ConfirmationMissingException("No file found path: " + filename);

        return file;
    }

    @Override
    public boolean exists(String filename)
    { return new File("D:\\" + filename).exists(); }
}
