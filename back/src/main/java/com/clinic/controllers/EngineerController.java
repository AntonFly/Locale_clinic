package com.clinic.controllers;

import com.clinic.entities.AccompanimentScript;
import com.clinic.entities.Client;
import com.clinic.entities.Order;
import com.clinic.exceptions.ConfirmationMissingException;
import com.clinic.exceptions.NoPersonToClientException;
import com.clinic.exceptions.OrderNotFoundException;
import com.clinic.exceptions.PassportNotFoundException;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
//@PreAuthorize("hasRole('ROLE_ENGINEER')")
@RequestMapping("/engineer")
public class EngineerController {

    private final ClientService clientService;
    private final FileService fileService;
    private final OrderService orderService;

    @Autowired
    public EngineerController(
            ClientService cs,
            FileService fs,
            OrderService os
    )
    {
        this.clientService = cs;
        this.fileService = fs;
        this.orderService = os;
    }

    @GetMapping("/get_scenario_by_order_id")
    public AccompanimentScript getSpecs(@RequestParam long orderId)
            throws OrderNotFoundException, ConfirmationMissingException
    {
        Order order = orderService.getOrderById(orderId);
        if (!fileService.exists(order.getConfirmation()))
            throw new ConfirmationMissingException(orderId);

        return order.getAccompanimentScript();
    }

    @GetMapping("/get_client_by_passport")
    public Client getClientByPassport(@RequestParam long passport) throws
            PassportNotFoundException, NoPersonToClientException
    { return clientService.getClientByPassport(passport); }

    @GetMapping("/get_all_clients")
    public List<Client> getAllClients()
    { return clientService.getAllClients(); }

}
