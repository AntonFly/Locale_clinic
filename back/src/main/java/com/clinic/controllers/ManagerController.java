package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleOrderRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Modification;
import com.clinic.entities.Order;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.*;
import com.clinic.services.ClientService;
import com.clinic.services.ModificationService;
import com.clinic.services.OrderService;
import com.clinic.services.SpecializationService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/manager")
public class ManagerController {

    private SpecializationService specializationService;
    private ModificationService modificationService;
    private ClientService clientService;
    private OrderService orderService;

    @Autowired
    public ManagerController(
            SpecializationService ss,
            ModificationService ms,
            ClientService cs,
            OrderService os
    ){
        this.specializationService = ss;
        this.modificationService = ms;
        this.clientService = cs;
        this.orderService = os;
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
    public Client clientExists(@RequestBody Long passport)
            throws ClientNotFoundException
    {
       return clientService.getClientByPassport(passport);
    }

    @PostMapping("/create_client")
    public Client createClient(@RequestBody SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException
    {
        return clientService.save(clientData);
    }

    @GetMapping("/get_mods_by_spec")
    public List<Modification> getModsBySpec(@RequestBody SimpleSpecializationRegistration specializationData)
            throws SpecializationMissingException
    {
        return modificationService.getAllModificationsBySpec(specializationData);
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
        order.setClient(clientService.getClientByPassport(orderData.getPassport()));

        Specialization specialization = specializationService.getSpecByName(orderData.getSpecName());
        order.setSpecialization(specialization);

        Set<Modification> modifications = new HashSet<>();
        modifications.add(modificationService.getModificationByName(orderData.getModName()));

        List<Modification> allowedModifications = modificationService.getAllModificationsBySpec(new SimpleSpecializationRegistration(specialization.getName()));
        if (!new HashSet<>(allowedModifications).containsAll(modifications))
            throw new ModSpecConflictException(
                    "Some modifications are not allowed for given specialization: " +
                    modifications.stream()
                            .filter(modification -> !allowedModifications.contains(modification))
                            .map(Modification::getName)
                            .collect(Collectors.joining(","))
            );

        order.setModifications(modifications);

        return orderService.save(order);
    }

}