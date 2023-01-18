package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
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
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@PreAuthorize("hasRole('ROLE_MANAGER')")
@RequestMapping("/manager")
public class ManagerController {

    private final SpecializationService specializationService;
    private final ModificationService modificationService;
    private final ClientService clientService;
    private final OrderService orderService;

    private final ScenarioService scenarioService;

    private final PDFService pdfService;

    @Autowired
    public ManagerController(
            SpecializationService ss,
            ModificationService ms,
            ClientService cs,
            OrderService os,
            ScenarioService scenarioService,
            PDFService pdfService
    )
    {
        this.specializationService = ss;
        this.modificationService = ms;
        this.clientService = cs;
        this.orderService = os;
        this.scenarioService = scenarioService;
        this.pdfService = pdfService;
    }

    @GetMapping("/get_specs")
    public List<Specialization> getSpecs()
    { return specializationService.getAllSpecializations(); }

    @GetMapping("/get_mods")
    public List<Modification> getMods()
    { return modificationService.getAllModifications(); }

    @GetMapping("/get_clients")
    public List<Client> getClients()
    { return clientService.getAllClients(); }

    @GetMapping("/get_client_by_passport")
    public Client clientExists(@RequestParam Long passport)
            throws PassportNotFoundException, NoPersonToClientException
    { return clientService.getClientByPassport(passport); }

    @GetMapping("/get_order_by_passport")
    public Set<Order> getOrders(@RequestParam Long passport)
            throws PassportNotFoundException, NoPersonToClientException
    { return clientService.getClientByPassport(passport).getOrders(); }

    @PostMapping("/create_client")
    public Client createClient(@RequestBody SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException
    { return clientService.createClient(clientData); }

    @PostMapping("/create_client_existing_person")
    public Client createClientWithExistingPerson(@RequestBody ExistingPersonClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException
    { return clientService.createClient(clientData); }

    @GetMapping("/get_mods_by_spec")
    public Set<Modification> getModsBySpec(@RequestParam int specId)
            throws SpecializationNotFoundException
    { return scenarioService.getAllModificationsBySpec(specId); }

    @GetMapping("/get_orders")
    public List<Order> getOrders()
    { return orderService.getAllOrders(); }

    @PostMapping("/create_order")
    public Order createOrder(@RequestBody SimpleOrderRegistration orderData)
            throws ClientNotFoundException, SpecializationNotFoundException, ModificationNotFoundException
    {
        Order order = new Order();
        order.setClient(clientService.getClient(orderData.getClientId()));

        order.setSpecialization(specializationService.getSpecById(orderData.getSpecId()));

        order.setComment(orderData.getComment());

        Set<Modification> modifications = new HashSet<>();
        for (Long modId : orderData.getModIds())
            modifications.add(modificationService.getModificationById(modId));

        order.setModifications(modifications);

        return orderService.save(order);
    }

    @PostMapping("/update_client")
    public Client changeClient(@RequestBody SimpleClientRegistration clientInfo, @RequestParam Long clientId)
            throws ClientNotFoundException
    { return clientService.updateClient(clientInfo, clientId); }

    @PostMapping("/add_previous_modification")
    public  Client addPreviousModifications(@RequestBody SimpleModificationAdd modificationAdd)
            throws ClientNotFoundException, ModificationNotFoundException
    { return  clientService.addPreviousModifications(modificationAdd); }

    @GetMapping("/get_commercial/{order}")
    public ResponseEntity<InputStreamResource> get_commercial(@PathVariable("order") Long orderId)
            throws OrderNotFoundException, IOException, DocumentException
    {
        Order currentOrder = orderService.getOrderById(orderId);
        String filePath = pdfService.generateCommercial(currentOrder);

        File file = new File(filePath);
        HttpHeaders respHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/pdf");
        respHeaders.setContentType(mediaType);
        respHeaders.setContentLength(file.length());
        respHeaders.setContentDispositionFormData("attachment", file.getName());
        InputStreamResource isr = new InputStreamResource(Files.newInputStream(file.toPath()));
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
    }

    @GetMapping("/get_risks/{order}")
    public ResponseEntity<InputStreamResource> get_risks(@PathVariable("order") Long orderId)
            throws OrderNotFoundException, IOException, DocumentException
    {
        Order currentOrder = orderService.getOrderById(orderId);
        String filePath = pdfService.generateRiskList(currentOrder);

        File file = new File(filePath);
        HttpHeaders respHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/pdf");
        respHeaders.setContentType(mediaType);
        respHeaders.setContentLength(file.length());
        respHeaders.setContentDispositionFormData("attachment", file.getName());
        InputStreamResource isr = new InputStreamResource(Files.newInputStream(file.toPath()));
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
    }

    @PostMapping("/uploadConfirmation/{order}")
    public ResponseEntity<FileUploadResponse> uploadConfirmation(@RequestParam("file") MultipartFile multipartFile, @PathVariable("order") Long orderId)
            throws IOException, OrderNotFoundException
    {
        String[] fileParts = StringUtils.cleanPath(multipartFile.getOriginalFilename()).split("\\.");

        String fileName = "confirmationOrder_"+orderId+"_"+ LocalDate.now() +"."+fileParts[fileParts.length-1];
        long size = multipartFile.getSize();

        fileName =  Paths.get(FileUtil.saveFile(fileName,"confirmation", multipartFile)).getFileName().toString();

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);

        Order currentOrder =  orderService.getOrderById(orderId);
        currentOrder.setConfirmation(fileName);
        orderService.save(currentOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download_confirmation")
    public ResponseEntity<InputStreamResource> get_confirmation(@RequestParam String file)
            throws IOException
    { return FileUtil.downloadFile("confirmation/" + file); }

}
