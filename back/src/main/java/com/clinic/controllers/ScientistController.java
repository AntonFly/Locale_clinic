package com.clinic.controllers;

import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.dto.SimpleScenarioUpdate;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import com.clinic.services.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@PreAuthorize("hasRole('ROLE_SCIENTIST')")
@RequestMapping("/scientist")
public class ScientistController {
    private final OrderService orderService;
    private final ScenarioService scenarioService;
    private final ClientService clientService;

    @Autowired
    public ScientistController(
            OrderService os,
            ScenarioService sc,
            ClientService cs
    )
    {
        this.orderService = os;
        this.scenarioService = sc;
        this.clientService = cs;
    }

    @GetMapping("/get_all_clients")
    public List<Client> getAllClients()
    { return clientService.getAllClients(); }

    @GetMapping("/get_ordered_mods_by_spec")
    public List<Modification> getOrderedModsBySpec(@RequestParam long specId)
            throws SpecializationNotFoundException
    { return scenarioService.getAllModificationsBySpecOrderedByRisk(specId); }

    @PostMapping("/create_scenario")
    public Scenario createScenario(@RequestBody SimpleScenarioRegistration createData)
            throws SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, ScenarioOrderException
    { return scenarioService.createScenario(createData); }

    @PutMapping("/update_scenario")
    public Scenario updateScenario(@RequestBody SimpleScenarioUpdate updateData)
            throws ScenarioNotFoundException, UnspecifiedModScenarioException, UnknownModScenarioException
    { return scenarioService.updateScenario(updateData); }

    @GetMapping("/get_all_orders_by_passport")
    public Set<Order> getOrdersByPassport(@RequestParam long passport)
            throws PassportNotFoundException, NoPersonToClientException
    { return orderService.getAllOrdersByPassport(passport); }

}
