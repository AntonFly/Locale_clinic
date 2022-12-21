package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Person;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.PassportRepository;
import com.clinic.repositories.PersonRepository;
import com.clinic.services.ClientService;
import com.clinic.services.ModificationService;
import com.clinic.services.OrderService;
import com.clinic.services.SpecializationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
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
    ClientService cs;
    @Autowired
    OrderService os;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PassportRepository passportRepository;

    Client createdClient;
    SimpleClientRegistration simpleClientRegistration;

    @BeforeEach
    void setUp() throws PersonConflictException, ClientConflictException, PassportConflictException {
        mc = new ManagerController(ss,ms,cs,os);
        simpleClientRegistration = generateTestPerson();
        createdClient = mc.createClient(simpleClientRegistration);
    }

    @AfterEach
    void afterAll(){
        clientRepository.delete(createdClient);
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
    void getClients() { assertTrue(mc.getClients().size() > 0 );

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
    void getModsBySpec() {

        //assertEquals(1,mc.getModsBySpec());
    }

    @Test
    void getOrders() {
    }

    @Test
    void createOrder() {
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