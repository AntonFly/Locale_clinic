package com.clinic.controllers;


import com.clinic.services.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(
            FileService fs
    ){
        this.fileService = fs;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/downloadPdf/{file_name}" })
    public ResponseEntity<InputStreamResource> downloadPdf(
            @PathVariable("file_name") String fileName
            )
    {
        try
        {
            File file = fileService.getFile(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(Files.newInputStream(file.toPath()));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
