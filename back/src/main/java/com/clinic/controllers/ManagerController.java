package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import com.clinic.utilities.FileUploadResponse;
import com.clinic.utilities.FileUploadUtil;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController()
@RequestMapping("/manager")
public class ManagerController {

    private SpecializationService specializationService;
    private ModificationService modificationService;
    private ClientService clientService;
    private OrderService orderService;

    private  ScenarioService scenarioService;

    private  PDFService pdfService;

    @Autowired
    public ManagerController(
            SpecializationService ss,
            ModificationService ms,
            ClientService cs,
            OrderService os,
            ScenarioService scenarioService,
            PDFService pdfService
    ){
        this.specializationService = ss;
        this.modificationService = ms;
        this.clientService = cs;
        this.orderService = os;
        this.scenarioService = scenarioService;
        this.pdfService = pdfService;
    }

    @GetMapping("/get_specs")
    public List<Specialization> getSpecs(){
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/get_mods")
    public List<Modification> getMods(){
        return modificationService.getAllModifications();
    }

    @GetMapping("/get_clients")
    public List<Client> getClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/get_client_by_passport")
    public Client clientExists(@RequestParam Long passport)
            throws ClientNotFoundException
    {
       return clientService.getClientByPassport(passport);
    }

    @PostMapping("/create_client")
    public Client createClient(@RequestBody SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException {

        return clientService.save(clientData);
    }

    @PostMapping("/create_client_existing_person")
    public Client createClientWithExistingPerson(@RequestBody ExistingPersonClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException {

        return clientService.save(clientData);
    }





    @GetMapping("/get_mods_by_spec")
    public Set<Modification> getModsBySpec(@RequestParam int specId)
            throws SpecializationMissingException
    {
        return scenarioService.getAllModificationsBySpec(specId);
    }

    @GetMapping("/get_orders")
    public List<Order> getOrders()
    {
        return orderService.getAllOrders();
    }

    @PostMapping("/create_order")
    public Order createOrder(@RequestBody SimpleOrderRegistration orderData)
            throws
            ClientNotFoundException,
            SpecializationMissingException,
            ModificationMissingException,
            ModSpecConflictException
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
    public Client changeClient(@RequestBody SimpleClientRegistration clientInfo, @RequestParam Long clientId){
        return clientService.updateClient(clientInfo, clientId);
    }

    @GetMapping("/get_commercial/{order}")
    public ResponseEntity<InputStreamResource> get_commercial(
            @PathVariable("order") Long orderId
    ) throws OrderNotFoundExceprion, IOException, DocumentException {
        Order currentOrder = orderService.getOrderById(orderId);
        String filePath = pdfService.generateCommercial(currentOrder);

        try
        {
            File file = new File(filePath);
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

    @GetMapping("/get_risks/{order}")
    public ResponseEntity<InputStreamResource> get_risks(
            @PathVariable("order") Long orderId
    ) throws OrderNotFoundExceprion, IOException, DocumentException {
        try
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
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadConfirmation/{order}")
    public ResponseEntity<FileUploadResponse> uploadConfirmation(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable("order") Long orderId)
            throws IOException, OrderNotFoundExceprion {

        String[] fileParts = StringUtils.cleanPath(multipartFile.getOriginalFilename()).split("\\.");

        String fileName = "confirmationOrder_"+orderId+"."+fileParts[fileParts.length-1];
        long size = multipartFile.getSize();

        fileName = FileUploadUtil.saveFile(fileName,"confirmation", multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);

        Order currentOrder =  orderService.getOrderById(orderId);
        currentOrder.setConfirmation(fileName);
        orderService.save(currentOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
