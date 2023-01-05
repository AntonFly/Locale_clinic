package com.clinic.controllers;

import com.clinic.dto.SimpleScenarioRegistration;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.*;
import com.clinic.exceptions.ModificationMissingException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.SpecializationMissingException;
import com.clinic.exceptions.UserConflictException;
import com.clinic.repositories.*;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/scientist")
public class ScientistController {
    private final AdminService adminService;
    private final ClientService clientService;
    private final ModificationService modificationService;
    private final OrderService orderService;
    private final PersonService personService;
    private final ScenarioService scenarioService;
    private final SpecializationService specializationService;
    private final UserService userService;
    private final ClientRepository clientRepository;
    private final ModificationRepository modificationRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final ScenarioRepository scenarioRepository;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;

    private final StockRepository stockRepository;

    private final PwdDropRequestRepository pwdDropRequestRepository;

    @Autowired
    public ScientistController(
            AdminService as,
            ClientService cs,
            ModificationService ms,
            OrderService os,
            PersonService ps,
            ScenarioService sc,
            SpecializationService ss,
            UserService us,
            ClientRepository cr,
            ModificationRepository mr,
            OrderRepository or,
            PersonRepository pr,
            RoleRepository rr,
            ScenarioRepository sr,
            SpecializationRepository ssr,
            UserRepository ur,
            PwdDropRequestRepository pwdr,
            StockRepository str
    ){
        this.adminService = as;
        this.clientService = cs;
        this.modificationService = ms;
        this.orderService = os;
        this.personService = ps;
        this.scenarioService = sc;
        this.specializationService = ss;
        this.userService = us;
        this.clientRepository = cr;
        this.modificationRepository = mr;
        this.orderRepository = or;
        this.personRepository = pr;
        this.roleRepository = rr;
        this.scenarioRepository = sr;
        this.specializationRepository = ssr;
        this.userRepository = ur;
        this.pwdDropRequestRepository = pwdr;
        this.stockRepository = str;
    }

    @GetMapping("/get_scenarios_by_spec")
    public List<Scenario> getScenariosBySpec(@RequestParam int specId)
            throws SpecializationMissingException
    { return scenarioService.getAllScenariosBySpecId(specId); }

    @GetMapping("/get_ordered_mods_by_spec")
    public List<Modification> getOrderedModsBySpec(@RequestParam int specId)
            throws SpecializationMissingException
    { return scenarioService.getAllModificationsBySpecOrderedByRisk(specId); }

    @PostMapping("/create_scenario")
    public Scenario createScenario(@RequestBody SimpleScenarioRegistration scenarioData)
            throws SpecializationMissingException, ModificationMissingException
    { return scenarioService.createScenario(scenarioData); }

}
