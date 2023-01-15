package com.clinic.controllers;

import com.clinic.entities.AccompanimentScript;
import com.clinic.entities.Client;
import com.clinic.entities.Order;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import com.clinic.utilities.FileUploadResponse;
import com.clinic.utilities.FileUtil;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@PreAuthorize("hasRole('ROLE_ENGINEER')")
@RequestMapping("/engineer")
public class EngineerController {

    private final ClientService clientService;
    private final FileService fileService;
    private final OrderService orderService;

    private final PDFService pdfService;

    @Autowired
    public EngineerController(
            ClientService cs,
            FileService fs,
            OrderService os,
            PDFService pdfS
    )
    {
        this.clientService = cs;
        this.fileService = fs;
        this.orderService = os;
        this.pdfService = pdfS;
    }

    @GetMapping("/get_scenario_by_order_id")
    public AccompanimentScript getScript(@RequestParam long orderId)
            throws OrderNotFoundException, ConfirmationNotFoundException
    {
        Order order = orderService.getOrderById(orderId);
        if (!fileService.exists(order.getConfirmation()))
            throw new ConfirmationNotFoundException(orderId);

        return order.getAccompanimentScript();
    }

    @GetMapping("/get_client_by_passport")
    public Client getClientByPassport(@RequestParam long passport) throws
            PassportNotFoundException, NoPersonToClientException
    { return clientService.getClientByPassport(passport); }

    @GetMapping("/get_all_clients")
    public List<Client> getAllClients()
    { return clientService.getAllClients(); }


    @PostMapping("/uploadGenome/{order}")
    public ResponseEntity<FileUploadResponse> uploadGenome(@RequestParam("file") MultipartFile multipartFile, @PathVariable("order") Long orderId)
            throws IOException, OrderNotFoundException
    {
        String[] fileParts = StringUtils.cleanPath(multipartFile.getOriginalFilename()).split("\\.");

        String fileName = "genomeOrder_"+orderId+"_"+ LocalDate.now() +"."+fileParts[fileParts.length-1];
        long size = multipartFile.getSize();

        fileName =  Paths.get(FileUtil.saveFile(fileName,"genome", multipartFile)).getFileName().toString();

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);

        Order currentOrder =  orderService.getOrderById(orderId);
        currentOrder.setGenome(fileName);
        orderService.save(currentOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/downloadGenome")
    public ResponseEntity<InputStreamResource> getConfirmation(@RequestParam String file)
            throws IOException
    { return FileUtil.downloadFile("genome/"+file); }

    @GetMapping("/getScenario/{order}")
    public ResponseEntity<InputStreamResource> getScenario(@PathVariable("order") Long orderId)
            throws OrderNotFoundException, IOException, DocumentException, NoScenarioForOrderException
    {
        Order currentOrder = orderService.getOrderById(orderId);
        String filePath = pdfService.generateScenario(currentOrder);

        File file = new File(filePath);
        HttpHeaders respHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/pdf");
        respHeaders.setContentType(mediaType);
        respHeaders.setContentLength(file.length());
        respHeaders.setContentDispositionFormData("attachment", file.getName());
        InputStreamResource isr = new InputStreamResource(Files.newInputStream(file.toPath()));
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
    }

}
