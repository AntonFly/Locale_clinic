package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicControllerTest {

    @Autowired
    MedicController mc;
    @Autowired
    ScientistController sc;
    @Autowired
    ManagerController managerController;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ModificationRepository modificationRepository;
    @Autowired
    SpecializationRepository specializationRepository;
    @Autowired
    ScenarioRepository scenarioRepository;
    @Autowired
    ModificationScenarioRepository modificationScenarioRepository;



    SimpleClientRegistration simpleClientRegistration;
    Client createdClient;
    Order createdOrder;
    BodyChange createdBodyChange;
    Implant createdImplant;
    Specialization selectedSpecialization;

    Modification selectedModification;


    @BeforeEach
     void startUp() throws PersonConflictException, ClientConflictException, PassportConflictException, ClientNotFoundException, SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException {

        simpleClientRegistration = generateTestPerson();
        createdClient = managerController.createClient(simpleClientRegistration);

        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        createdOrder = managerController.createOrder(simpleOrderRegistration);

        SimpleBodyChangesUpdate simpleBodyChangesUpdate = new SimpleBodyChangesUpdate();
        SimpleChange[] simpleChanges = new SimpleChange[1];
        SimpleChange simpleChange = new SimpleChange();
        simpleChange.setChange("Test change");
        simpleChange.setDescription("Test description");
        simpleChange.setActions("Test actions");
        simpleChange.setSymptoms("Test symptoms");
        simpleChanges[0]= simpleChange;
        simpleBodyChangesUpdate.setChanges(simpleChanges);
        simpleBodyChangesUpdate.setOrderId(createdOrder.getId());

        createdBodyChange =  mc.updateBodyChangesForOrder(simpleBodyChangesUpdate).getBodyChanges().get(0);

        SimpleImplantsUpdate simpleImplantsUpdate = new SimpleImplantsUpdate();
        simpleImplantsUpdate.setClientId(createdClient.getId());

        SimpleImplant simpleImplant = new SimpleImplant();
        simpleImplant.setDescription("Test implant description");
        simpleImplant.setName("Test implant");

        SimpleImplant[] simpleImplants = new SimpleImplant[1];
        simpleImplants[0]= simpleImplant;
        simpleImplantsUpdate.setImplants(simpleImplants);

        createdImplant = mc.addImplants(simpleImplantsUpdate).getImplants().iterator().next();

        selectedSpecialization = specializationRepository.findById(1L).get();
        selectedModification = modificationRepository.findById(1L).get();




    }

    @AfterEach

    void afterEach(){
        personRepository.delete(createdClient.getPerson());
        createdClient = null;
    }

    @Test
    @DisplayName("Update body changes")
    void updateBodyChangesForOrder() {
        assertTrue(orderRepository.getOne(createdOrder.getId()).getBodyChanges().stream().allMatch((item)->item.getChange().equals("Test change")));
    }


    @Test
    @DisplayName("Delete body change")
    void dropBodyChange() throws BodyChangeNotFoundException {
        mc.dropBodyChange(createdBodyChange.getId());
        assertEquals(0,orderRepository.getOne(createdOrder.getId()).getBodyChanges().size());
    }

    @Test
    @DisplayName("Adding implant")
    void addImplants() {
        assertTrue(clientRepository.getOne(createdClient.getId()).getImplants().stream().allMatch((item)->item.getName().equals("Test implant")));

    }

    @Test
    @DisplayName("Delete implant")
    void dropImplant() throws BodyChangeNotFoundException, ImplantNotFountException {
        mc.dropImplant(createdImplant.getId());
        assertEquals(0,clientRepository.getOne(createdClient.getId()).getImplants().size());
    }

    @Test
    @DisplayName("Get script for order without scenario")
    void getScriptByOrderInvalid() {
        assertThrows(NoScenarioForOrderException.class,()->mc.getScriptByOrder(createdOrder.getId()));

    }

    @Test
    @DisplayName("Get script for order ")

    void getScriptByOrder() throws OrderNotFoundException, NoScenarioForOrderException, ScenarioOrderException, SpecializationNotFoundException, ModificationNotFoundException {


        sc.createScenario(new SimpleScenarioRegistration(1L, createdOrder.getId(), List.of(1L)));

        assertEquals(selectedModification.getAccompaniment(),mc.getScriptByOrder(createdOrder.getId()).getScenarios().replace("\r\n",""));

    }



    private static SimpleClientRegistration generateTestPerson(){
        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("testemail@email.com");
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("TestName");
        simplePersonRegistration.setPassport(1122334455L);
        simplePersonRegistration.setPatronymic("TestPat");
        simplePersonRegistration.setSurname("TestSurname");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("2000-03-04"));
        simpleClientRegistration.setPerson(simplePersonRegistration);

        return simpleClientRegistration;
    }
}