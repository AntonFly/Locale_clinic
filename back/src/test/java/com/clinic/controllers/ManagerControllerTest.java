package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleOrderRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Order;
import com.clinic.entities.Person;
import com.clinic.exceptions.*;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.OrderRepository;
import com.clinic.repositories.PassportRepository;
import com.clinic.repositories.PersonRepository;
import com.clinic.services.*;
import org.hibernate.tool.schema.internal.exec.AbstractScriptSourceInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ManagerControllerTest {
    static ManagerController mc;

    @Autowired
    private SpecializationService ss;
    @Autowired
    private ModificationService ms;
    @Autowired
    private ClientService cs;
    @Autowired
    private OrderService os;
    @Autowired
    private ScenarioService scenarioService;



    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PassportRepository passportRepository;
    @Autowired
    OrderRepository orderRepository;

    Client createdClient;
    SimpleClientRegistration simpleClientRegistration;

    @BeforeEach
    void setUp() throws PersonConflictException, ClientConflictException, PassportConflictException {
        mc = new ManagerController(ss,ms,cs,os,scenarioService);
        simpleClientRegistration = generateTestPerson();
        createdClient = mc.createClient(simpleClientRegistration);
    }

    @AfterEach
    void afterEach(){
        personRepository.delete(createdClient.getPerson());
        createdClient = null;
    }

    @Test
    @DisplayName("Get all available specifications")
    void getSpecs() {
        assertTrue(mc.getSpecs().size()>0);
    }

    @Test
    @DisplayName("Get all available modifications")
    void getMods() {
        assertNotNull(mc.getMods());
    }

    @Test
    @DisplayName("Get all existing clients")
    void getClients() { assertTrue(mc.getClients().size() >= 1 );

    }

    @Test
    @DisplayName("Getting existing client")
    void clientExists() throws PersonConflictException, ClientConflictException, PassportConflictException, ClientNotFoundException {


        assertNotNull(mc.clientExists(simpleClientRegistration.getPerson().getPassport()));


    }
    @Test
    @DisplayName("Getting not existing client")
    void clientExistsNoValid() throws PersonConflictException, ClientConflictException, PassportConflictException, ClientNotFoundException {


        assertThrows(ClientNotFoundException.class, () -> mc.clientExists(2211334455L));

    }

    @Test
    @DisplayName("Valid new client")
    void createValidClient() {

        assertEquals(1, createdClient.getPerson().getPassports().stream()
                .filter(passport -> passport.getPassport() == simpleClientRegistration.getPerson().getPassport())
                .toArray().length);

    }

    @Test
    @DisplayName("Invalid new client")
    void createInValidClient() {

        assertThrows(PassportConflictException.class,()->mc.createClient(simpleClientRegistration));

    }

    @Test
    @DisplayName("Get all modifications for specialization")
    void getModsBySpec() throws SpecializationMissingException {
        assertEquals(7,mc.getModsBySpec(5).size());
    }

    @Test
    void getOrders() {
    }

    @Test
    @DisplayName("Create order")
    void createOrder() throws ClientNotFoundException, SpecializationMissingException, ModSpecConflictException, ModificationMissingException {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
                );
        Order createdOrder = mc.createOrder(simpleOrderRegistration);
        assertEquals("Test Order",createdOrder.getComment());
    }


    @Test
    @DisplayName("Change client")
    void changeClient(){
        SimpleClientRegistration temp = simpleClientRegistration;
        SimplePersonRegistration tempPerson = temp.getPerson();
        tempPerson.setPassport(1000000000L);
        tempPerson.setName("TestName1");
        temp.setPerson(tempPerson);
        temp.setEmail("testemail1@email.com");

        Client changedClient = mc.changeClient(temp, createdClient.getId());

        assertAll(
                ()-> assertEquals("testemail1@email.com",changedClient.getEmail()),
                ()-> assertTrue(changedClient.getPerson().getPassports().stream().anyMatch(item-> item.getPassport() == 1000000000L)),
                ()-> assertEquals("TestName1", changedClient.getPerson().getName())
        );
    }



    private SimpleClientRegistration generateTestPerson(){
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