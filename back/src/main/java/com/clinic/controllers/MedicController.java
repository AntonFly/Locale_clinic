package com.clinic.controllers;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.dto.SimpleImplantsUpdate;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController()
//@PreAuthorize("hasRole('ROLE_MEDIC')")
@RequestMapping("/medic")
public class MedicController {

    private SpecializationService specializationService;
    private ModificationService modificationService;
    private ClientService clientService;
    private OrderService orderService;
    private ScenarioService scenarioService;

    @Autowired
    public MedicController(
            SpecializationService ss,
            ModificationService ms,
            ClientService cs,
            OrderService os,
            ScenarioService scs
    ){
        this.specializationService = ss;
        this.modificationService = ms;
        this.clientService = cs;
        this.orderService = os;
        this.scenarioService = scs;
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

    @GetMapping("/get_body_changes")
    public List<BodyChange> getBodyChangesForOrder(@RequestParam long orderId)
            throws OrderNotFoundException
    {
        return orderService.getBodyChanges(orderId);
    }

    @DeleteMapping("/drop_change")
    public Boolean dropBodyChange(@RequestBody long bodyChangeId)
            throws BodyChangeNotFoundException {
        return orderService.dropBodyChange(bodyChangeId);
    }

    @GetMapping("/get_all_clients")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @PostMapping("/update_implants")
    public Client addImplants(@RequestBody SimpleImplantsUpdate implantsUpdate) throws ClientNotFoundException {
        return clientService.addImplants(implantsUpdate);
    }

    @DeleteMapping("/drop_implant")
    public Boolean dropImplant(@RequestBody long implantId)
            throws BodyChangeNotFoundException, ImplantNotFountException {
        return clientService.dropImplant(implantId);
    }




}
