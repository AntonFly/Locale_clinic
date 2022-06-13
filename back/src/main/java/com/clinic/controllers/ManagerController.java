package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleSpecializationRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Modification;
import com.clinic.entities.Order;
import com.clinic.entities.Specialization;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.SpecializationMissingException;
import com.clinic.services.ClientService;
import com.clinic.services.ModificationService;
import com.clinic.services.OrderService;
import com.clinic.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
