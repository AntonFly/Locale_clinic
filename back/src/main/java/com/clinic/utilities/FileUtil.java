package com.clinic.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.clinic.entities.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static String saveFile(String fileName,String fileFolder, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get(fileFolder);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return filePath.toString();
    }


    public static ResponseEntity<InputStreamResource>  downloadFile(String filePath) throws IOException {


        File file = new File(filePath);
        HttpHeaders respHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream");
        respHeaders.setContentType(mediaType);
        respHeaders.setContentLength(file.length());
        respHeaders.setContentDispositionFormData("attachment", file.getName());
        InputStreamResource isr = new InputStreamResource(Files.newInputStream(file.toPath()));
        return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);


    }



}