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

@RestController()
//@PreAuthorize("hasRole('ROLE_SCIENTIST')")
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

    @GetMapping("/get_ordered_mods_by_spec")
    public List<Modification> getOrderedModsBySpec(@RequestParam int specId)
            throws SpecializationNotFoundException
    { return scenarioService.getAllModificationsBySpecOrderedByRisk(specId); }

    @PostMapping("/create_scenario")
    public Scenario createScenario(@RequestBody SimpleScenarioRegistration createData)
            throws SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, ScenarioOrderException
    { return scenarioService.createScenario(createData); }

    @PutMapping("/update_scenario")
    public Scenario updateScenario(@RequestBody SimpleScenarioUpdate updateData)
            throws ScenarioNotFoundException, SpecializationNotFoundException, ModificationNotFoundException, UnspecifiedModScenarioException, UnknownModScenarioException
    { return scenarioService.updateScenario(updateData); }

    @GetMapping("/get_all_orders_by_passport")
    public Set<Order> getOrdersByPassport(@RequestParam long passport)
            throws PassportNotFoundException, NoPersonToClientException
    { return orderService.getAllOrdersByPassport(passport); }

}
