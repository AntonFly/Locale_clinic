package com.clinic.controllers;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.dto.SimpleChange;
import com.clinic.dto.SimpleImplantsUpdate;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
//@PreAuthorize("hasRole('ROLE_MEDIC')")
@RequestMapping("/medic")
public class MedicController {

    private final ClientService clientService;
    private final OrderService orderService;

    @Autowired
    public MedicController(
            ClientService cs,
            OrderService os
    )
    {
        this.clientService = cs;
        this.orderService = os;
    }

    @GetMapping("/get_orders_by_client_passport")
    public Set<Order> getOrdersByClientId(@RequestParam Long passport)
            throws PassportNotFoundException, NoPersonToClientException
    { return orderService.getAllOrdersByPassport(passport); }

    @GetMapping("/get_script_by_order")
    public AccompanimentScript getScriptByOrder(@RequestParam long orderId)
            throws OrderNotFoundException, NoScenarioForOrderException
    { return orderService.getScriptByOrderId(orderId); }

    @PutMapping("/update_changes")
    public Order updateBodyChangesForOrder(@RequestBody SimpleBodyChangesUpdate changesData)
        throws OrderNotFoundException
    {
        return orderService.createBodyChanges(changesData);
    }

    @PutMapping("/update_change")
    public BodyChange updateBodyChangeForOrder(@RequestBody SimpleChange changesData, @RequestParam long orderId )
            throws OrderNotFoundException
    { return orderService.updateBodyChange(changesData,orderId); }

    @GetMapping("/get_body_changes")
    public List<BodyChange> getBodyChangesForOrder(@RequestParam long orderId)
            throws OrderNotFoundException
    { return orderService.getBodyChanges(orderId); }

    @DeleteMapping("/drop_change")
    public Boolean dropBodyChange(@RequestBody long bodyChangeId)
            throws BodyChangeNotFoundException {
        return orderService.dropBodyChange(bodyChangeId);
    }

    @GetMapping("/get_all_clients")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/get_client_by_passport")
    public Client clientExists(@RequestParam Long passport)
            throws PassportNotFoundException, NoPersonToClientException
    { return clientService.getClientByPassport(passport); }

    @PostMapping("/update_implants")
    public Client addImplants(@RequestBody SimpleImplantsUpdate implantsUpdate)
            throws ClientNotFoundException
    { return clientService.addImplants(implantsUpdate); }

    @DeleteMapping("/drop_implant")
    public Boolean dropImplant(@RequestBody long implantId)
            throws ImplantNotFountException
    { return clientService.dropImplant(implantId); }




}
