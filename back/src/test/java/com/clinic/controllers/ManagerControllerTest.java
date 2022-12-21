package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Client;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.PassportRepository;
import com.clinic.repositories.PersonRepository;
import com.clinic.services.ClientService;
import com.clinic.services.ModificationService;
import com.clinic.services.OrderService;
import com.clinic.services.SpecializationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

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

    @BeforeEach
    void setUp() {
        mc = new ManagerController(ss,ms,cs,os);
    }

    @Test
    void getSpecs() {
        assertTrue(mc.getSpecs().size()>0);
    }

    @Test
    void getMods() {
    }

    @Test
    void getClients() {

    }

    @Test
    void clientExists() {
    }

    @Test
    @DisplayName("Valid new client")
    void createValidClient() throws PersonConflictException, ClientConflictException, PassportConflictException {

        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("testemail@email.com");
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("TestName");
        simplePersonRegistration.setPassport(1122334455L);
        simplePersonRegistration.setPatronymic("TestPat");
        simplePersonRegistration.setSurname("TestSurname");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("2000-03-04"));
        simpleClientRegistration.setPerson(simplePersonRegistration);

        Client createdClient = mc.createClient(simpleClientRegistration);

        assertEquals(1, createdClient.getPerson().getPassports().stream()
                .filter(passport -> passport.getPassport() == simplePersonRegistration.getPassport())
                .toArray().length);

        clientRepository.delete(createdClient);
        personRepository.delete(createdClient.getPerson());

    }

    @Test
    @DisplayName("Invalid new client")
    void createInValidClient() throws PersonConflictException, ClientConflictException, PassportConflictException {
        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("testemail@email.com");
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("TestName");
        simplePersonRegistration.setPassport(1122334455L);
        simplePersonRegistration.setPatronymic("TestPat");
        simplePersonRegistration.setSurname("TestSurname");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("2000-03-04"));
        simpleClientRegistration.setPerson(simplePersonRegistration);
        Client createdClient = mc.createClient(simpleClientRegistration);

        assertThrows(PassportConflictException.class,()->mc.createClient(simpleClientRegistration));

        clientRepository.delete(createdClient);
        personRepository.delete(createdClient.getPerson());

    }

    @Test
    void getModsBySpec() {
    }

    @Test
    void getOrders() {
    }

    @Test
    void createOrder() {
    }
}