package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Client;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.services.ClientService;
import com.clinic.services.ModificationService;
import com.clinic.services.OrderService;
import com.clinic.services.SpecializationService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.DateFormat;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ManagerControllerTest {
    static ManagerController mc;

    @Autowired
    private static SpecializationService ss;
    @Autowired
    private static ModificationService ms;
    @Autowired
    static
    ClientService cs;
    @Autowired
    static
    OrderService os;

    @BeforeAll
    static void setUp() {
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
    void createClient() throws PersonConflictException, ClientConflictException {
        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("testemail@email.com");
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("TestName");
        simplePersonRegistration.setPassport(1413121110L);
        simplePersonRegistration.setPatronymic("TestPat");
        simplePersonRegistration.setSurname("TestSurname");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("2000-03-04"));
        simpleClientRegistration.setPerson(simplePersonRegistration);
        Client createdClient = mc.createClient(simpleClientRegistration);
        //assertTrue(simpleClientRegistration.getPerson().getPassport().equals(createdClient.getPerson().get));
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