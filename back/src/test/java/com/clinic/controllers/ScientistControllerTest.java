package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.entities.enums.ERole;
import com.clinic.entities.keys.ModificationScenarioId;
import com.clinic.entities.keys.StockId;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import com.clinic.services.ClientService;
import com.clinic.services.OrderService;
import com.clinic.services.ScenarioService;
import com.clinic.services.StockService;
import org.aspectj.weaver.ast.Or;
import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ScientistControllerTest {

    static ScientistController sc;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private ClientService clientService;

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ModificationRepository modificationRepository;
    @Autowired
    ModificationScenarioRepository modificationScenarioRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ScenarioRepository scenarioRepository;
    @Autowired
    SpecializationRepository specializationRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Person createdPerson;
    Person createdPersonWithoutClient;
    Client createdClient;
    Specialization createdSpecialization;
    List<Modification> createdModifications;
    Order createdOrder;
    Order createdOrderWithoutScenario;
    Scenario createdScenario;

    @BeforeEach
    @Transactional(rollbackFor = Exception.class)
    void beforeEach() {
        sc = new ScientistController(orderService, scenarioService, clientService);

        createdPerson = createPerson(1000000011);
        createdPersonWithoutClient = createPerson(1000000012);
        createdClient = createClient(createdPerson);


        createdModifications = createModifications();
        createdSpecialization = createSpecialization(new HashSet<>(createdModifications));

        createdSpecialization.setModifications(new HashSet<>(createdModifications));

        createdOrder = createOrder(createdClient, createdSpecialization, new HashSet<>(createdModifications));
        createdOrderWithoutScenario = createOrder(createdClient, createdSpecialization, new HashSet<>(createdModifications));

        Set<Order> orders = new HashSet<>();
        orders.add(createdOrder);
        orders.add(createdOrderWithoutScenario);
        createdClient.setOrders(orders);
        createdClient = clientRepository.save(createdClient);

        createdScenario = createScenario(createdOrder, createdSpecialization, new HashSet<>(createdModifications));
    }

    @Transactional(rollbackFor = Exception.class)
    Person createPerson(long passportNum) {
        Passport passport = new Passport();
        passport.setPassport(passportNum);

        List<Passport> passports = new ArrayList<>();
        passports.add(passport);

        Person person = new Person();
        person.setName("STOCK CONTROLLER TEST");
        person.setSurname("STOCK CONTROLLER TEST");
        person.setPatronymic("STOCK CONTROLLER TEST");
        person.setDateOfBirth(Calendar.getInstance().getTime());
        person.setPassports(passports);
        person = personRepository.save(person);

        passport.setPerson(person);
        passportRepository.save(passport);

        return person;
    }

    @Transactional(rollbackFor = Exception.class)
    Client createClient(Person person) {
        Client client = new Client();
        client.setComment("SCIENTIST CONTROLLER TEST");
        client.setEmail("black.hornetnikita+scientisttest@gmail.com");
        client.setPerson(person);

        return clientRepository.save(client);
    }

    @Transactional(rollbackFor = Exception.class)
    Specialization createSpecialization(Set<Modification> modifications) {
        Specialization specialization = new Specialization();
        specialization.setName("SCT");
        specialization.setModifications(modifications);

        return specializationRepository.save(specialization);
    }

    @Transactional(rollbackFor = Exception.class)
    List<Modification> createModifications()
    {
        List<Modification> modifications = new ArrayList<>();
        for (long i = 1; i < 100; i += 10) {
            Modification modification = new Modification();
            modification.setName("STOCK CONTROLLER TEST");
            modification.setChance(BigDecimal.valueOf(i));
            modification.setCurrency("RUB");
            modification.setPrice(3313);
            modification.setRisk("STOCK CONTROLLER TEST");
            modifications.add(modification);
        }

        return modificationRepository.saveAll(modifications);
    }

    @Transactional(rollbackFor = Exception.class)
    Order createOrder(Client client, Specialization specialization, Set<Modification> modifications) {
        Order order = new Order();
        order.setComment("SCIENTIST CONTROLLER TEST");
        order.setModifications(modifications);
        order.setSpecialization(specialization);
        order.setClient(client);

        return orderRepository.save(order);
    }

    @Transactional(rollbackFor = Exception.class)
    Scenario createScenario(Order order, Specialization specialization, Set<Modification> modifications) {
        Scenario scenario = new Scenario();
        scenario.setOrder(order);
        scenario.setSpecialization(specialization);

        scenario = scenarioRepository.save(scenario);

        List<ModificationScenario> modificationScenarios = new ArrayList<>();
        for (Modification modification : modifications) {
            ModificationScenarioId modificationScenarioId = new ModificationScenarioId();
            modificationScenarioId.setScenarioId(scenario.getId());
            modificationScenarioId.setModificationId(modification.getId());

            ModificationScenario modificationScenario = new ModificationScenario();
            modificationScenario.setId(modificationScenarioId);
            modificationScenario.setPriority(0);
            modificationScenario.setScenario(scenario);
            modificationScenario.setModification(modification);
            modificationScenarios.add(modificationScenario);
        }

        modificationScenarios = modificationScenarioRepository.saveAll(modificationScenarios);

        scenario.setModificationScenarios(modificationScenarios);
        return scenario;
    }

    @AfterEach
    @Transactional(rollbackFor = Exception.class)
    void afterEach()
    {
        personRepository.delete(createdPerson);
        personRepository.delete(createdPersonWithoutClient);
        specializationRepository.delete(createdSpecialization);
        modificationRepository.deleteAll(createdModifications);
    }

    @Test
    @DisplayName("Get ordered mods by non existent spec")
    @Transactional(rollbackFor = Exception.class)
    void getModsNonExistentSpec() {
        assertThrows(SpecializationNotFoundException.class, () -> sc.getOrderedModsBySpec(10000));
    }

    @Test
    @DisplayName("Get ordered mods by existent spec")
    @Transactional(rollbackFor = Exception.class)
    void getModsExistentSpec() {
        List<Modification> modifications = assertDoesNotThrow(() -> sc.getOrderedModsBySpec(createdSpecialization.getId()));

        List<Modification> sortedCreatedModifications = createdModifications.stream()
                .sorted(Comparator.comparing(Modification::getChance))
                .collect(Collectors.toList());

        assertIterableEquals(modifications, sortedCreatedModifications);
    }


    @Test
    @DisplayName("Create scenario for non existent specialization")
    @Transactional(rollbackFor = Exception.class)
    void createScenarioNonExistentSpec() {
        SimpleScenarioRegistration simpleScenarioRegistration = new SimpleScenarioRegistration();
        simpleScenarioRegistration.setSpecId(100000);
        simpleScenarioRegistration.setMods(createdModifications.stream().map(Modification::getId).collect(Collectors.toList()));
        simpleScenarioRegistration.setOrderId(createdOrder.getId());

        assertThrows(SpecializationNotFoundException.class, () -> sc.createScenario(simpleScenarioRegistration));
    }

    @Test
    @DisplayName("Create scenario for non existent order")
    @Transactional(rollbackFor = Exception.class)
    void createScenarioNonExistentOrder() {
        SimpleScenarioRegistration simpleScenarioRegistration = new SimpleScenarioRegistration();
        simpleScenarioRegistration.setSpecId(createdSpecialization.getId());
        simpleScenarioRegistration.setMods(createdModifications.stream().map(Modification::getId).collect(Collectors.toList()));
        simpleScenarioRegistration.setOrderId(100000);

        assertThrows(OrderNotFoundException.class, () -> sc.createScenario(simpleScenarioRegistration));
    }

    @Test
    @DisplayName("Create scenario for non existent modification")
    @Transactional(rollbackFor = Exception.class)
    void createScenarioNonExistentMod() {
        SimpleScenarioRegistration simpleScenarioRegistration = new SimpleScenarioRegistration();
        simpleScenarioRegistration.setSpecId(createdSpecialization.getId());
        List<Long> modifications = createdModifications.stream().map(Modification::getId).collect(Collectors.toList());
        modifications.add(100000L);
        simpleScenarioRegistration.setMods(modifications);
        simpleScenarioRegistration.setOrderId(createdOrderWithoutScenario.getId());

        assertThrows(ModificationNotFoundException.class, () -> sc.createScenario(simpleScenarioRegistration));
    }

    @Test
    @DisplayName("Create another scenario for order")
    @Transactional(rollbackFor = Exception.class)
    void createAnotherScenarioForOrder()
    {
        SimpleScenarioRegistration simpleScenarioRegistration = new SimpleScenarioRegistration();
        simpleScenarioRegistration.setSpecId(createdSpecialization.getId());
        simpleScenarioRegistration.setMods(createdModifications.stream().map(Modification::getId).collect(Collectors.toList()));
        simpleScenarioRegistration.setOrderId(createdOrder.getId());

        assertThrows(ScenarioOrderException.class, () -> sc.createScenario(simpleScenarioRegistration));
    }

    @Test
    @DisplayName("Create valid scenario for order")
    @Transactional(rollbackFor = Exception.class)
    void createScenario() {
        SimpleScenarioRegistration simpleScenarioRegistration = new SimpleScenarioRegistration();
        simpleScenarioRegistration.setSpecId(createdSpecialization.getId());
        simpleScenarioRegistration.setMods(createdModifications.stream().map(Modification::getId).collect(Collectors.toList()));
        simpleScenarioRegistration.setOrderId(createdOrderWithoutScenario.getId());

        Scenario scenario = assertDoesNotThrow(() -> sc.createScenario(simpleScenarioRegistration));
        Optional<Scenario> checkScenario = scenarioRepository.findById(scenario.getId());

        assertTrue(checkScenario.isPresent());
        assertEquals(scenario, checkScenario.get());
    }

    @Test
    @DisplayName("Update non existent scenario")
    @Transactional(rollbackFor = Exception.class)
    void updateNonExistentScenario() {
        SimpleScenarioUpdate simpleScenarioUpdate = new SimpleScenarioUpdate();
        simpleScenarioUpdate.setScenarioId(100000);

        List<SimpleModPriority> modPriorities = new ArrayList<>();
        long priority = 10;
        for (ModificationScenario modificationScenario : createdScenario.getModificationScenarios()) {
            SimpleModPriority simpleModPriority = new SimpleModPriority();
            simpleModPriority.setId(modificationScenario.getModification().getId());
            simpleModPriority.setPriority(priority);
            priority += 10;
            modPriorities.add(simpleModPriority);
        }

        simpleScenarioUpdate.setMods(modPriorities);

        assertThrows(ScenarioNotFoundException.class, () -> sc.updateScenario(simpleScenarioUpdate));
    }

    @Test
    @DisplayName("Update existent scenario with extra mod")
    @Transactional(rollbackFor = Exception.class)
    void updateScenarioExtraMod() {
        SimpleScenarioUpdate simpleScenarioUpdate = new SimpleScenarioUpdate();
        simpleScenarioUpdate.setScenarioId(createdScenario.getId());

        List<SimpleModPriority> modPriorities = new ArrayList<>();
        long priority = 10;
        for (ModificationScenario modificationScenario : createdScenario.getModificationScenarios()) {
            SimpleModPriority simpleModPriority = new SimpleModPriority();
            simpleModPriority.setId(modificationScenario.getModification().getId());
            simpleModPriority.setPriority(priority);
            priority += 10;
            modPriorities.add(simpleModPriority);
        }

        SimpleModPriority simpleModPriority = new SimpleModPriority();
        simpleModPriority.setId(10000);
        simpleModPriority.setPriority(10000);
        modPriorities.add(simpleModPriority);

        simpleScenarioUpdate.setMods(modPriorities);

        assertThrows(UnknownModScenarioException.class, () -> sc.updateScenario(simpleScenarioUpdate));
    }

    @Test
    @DisplayName("Update existent scenario without all mods")
    @Transactional(rollbackFor = Exception.class)
    void updateScenarioWithoutMods() {
        SimpleScenarioUpdate simpleScenarioUpdate = new SimpleScenarioUpdate();
        simpleScenarioUpdate.setScenarioId(createdScenario.getId());

        List<SimpleModPriority> modPriorities = new ArrayList<>();
        long priority = 10;
        for (ModificationScenario modificationScenario : createdScenario.getModificationScenarios()) {
            SimpleModPriority simpleModPriority = new SimpleModPriority();
            simpleModPriority.setId(modificationScenario.getModification().getId());
            simpleModPriority.setPriority(priority);
            priority += 10;
            modPriorities.add(simpleModPriority);
        }

        modPriorities.remove(0);

        simpleScenarioUpdate.setMods(modPriorities);

        assertThrows(UnspecifiedModScenarioException.class, () -> sc.updateScenario(simpleScenarioUpdate));
    }

    @Test
    @DisplayName("Update existent scenario")
    @Transactional(rollbackFor = Exception.class)
    void updateScenario() {
        SimpleScenarioUpdate simpleScenarioUpdate = new SimpleScenarioUpdate();
        simpleScenarioUpdate.setScenarioId(createdScenario.getId());

        List<SimpleModPriority> modPriorities = new ArrayList<>();
        long priority = 10;
        for (ModificationScenario modificationScenario : createdScenario.getModificationScenarios()) {
            SimpleModPriority simpleModPriority = new SimpleModPriority();
            simpleModPriority.setId(modificationScenario.getModification().getId());
            simpleModPriority.setPriority(priority);
            priority += 10;
            modPriorities.add(simpleModPriority);
        }

        simpleScenarioUpdate.setMods(modPriorities);

        Scenario scenario = assertDoesNotThrow(() -> sc.updateScenario(simpleScenarioUpdate));
        Optional<Scenario> scenarioCheck = scenarioRepository.findById(scenario.getId());

        assertTrue(scenarioCheck.isPresent());
        assertEquals(scenario, scenarioCheck.get());
    }

    @Test
    @DisplayName("Get orders by non existent passport")
    @Transactional(rollbackFor = Exception.class)
    void getOrdersNonExistentPassport()
    { assertThrows(PassportNotFoundException.class, () -> sc.getOrdersByPassport(1000000013)); }

    @Test
    @DisplayName("Get orders for not client person")
    @Transactional(rollbackFor = Exception.class)
    void getOrdersNoClientPerson()
    { assertThrows(NoPersonToClientException.class, () -> sc.getOrdersByPassport(createdPersonWithoutClient.getPassports().get(0).getPassport())); }

    @Test
    @DisplayName("Get orders by passport")
    @Transactional(rollbackFor = Exception.class)
    void getOrdersByPassport()
    {
        Set<Order> orders = assertDoesNotThrow(() -> sc.getOrdersByPassport(createdClient.getPerson().getPassports().get(0).getPassport()));

        Set<Order> checkOrders = new HashSet<>();
        checkOrders.add(createdOrder);
        checkOrders.add(createdOrderWithoutScenario);

        assertIterableEquals(orders, checkOrders);
    }

}