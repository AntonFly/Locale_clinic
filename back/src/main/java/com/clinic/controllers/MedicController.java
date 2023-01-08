package com.clinic.controllers;

import com.clinic.dto.SimpleBodyChangesUpdate;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@PreAuthorize("hasRole('ROLE_MEDIC')")
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
    public List<Order> getOrdersByClientId(@RequestParam Long passport)
            throws ClientNotFoundException
    {
        return orderService.getAllOrdersByClientId(passport);
    }

    @GetMapping("/get_scenarios_by_spec")
    public List<Scenario> getScenariosBySpec(@RequestParam long specId)
        throws SpecializationNotFoundException
    {
        return scenarioService.getAllScenariosBySpecId(specId);
    }

    @GetMapping("/get_script_by_order")
    public AccompanimentScript getScriptByOrder(@RequestParam long orderId)
        throws OrderNotFoundException
    {
        return orderService.getScriptByOrderId(orderId);
    }

    @PutMapping("/update_changes")
    public Order updateBodyChangesForOrder(@RequestBody SimpleBodyChangesUpdate changesData)
        throws OrderNotFoundException
    {
        return orderService.createBodyChanges(changesData);
    }


}
