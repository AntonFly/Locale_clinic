package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleOrderRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Order;
import com.clinic.exceptions.*;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.OrderRepository;
import com.clinic.repositories.PassportRepository;
import com.clinic.repositories.PersonRepository;
import com.clinic.services.*;
import com.clinic.utilities.FileUploadResponse;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Arrays;
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
    PDFService pdfService;



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
        mc = new ManagerController(ss,ms,cs,os,scenarioService,pdfService);
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
    void getModsBySpec() throws SpecializationNotFoundException {
        assertEquals(7,mc.getModsBySpec(5).size());
    }


    @Test
    @DisplayName("Create order")
    void createOrder() throws ClientNotFoundException, SpecializationNotFoundException, ModSpecConflictException, ModificationNotFoundException {
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

    @Test
    @DisplayName("Generating commercial")
    void generatingCommercial() throws ClientNotFoundException, SpecializationNotFoundException, ModSpecConflictException, ModificationNotFoundException, OrderNotFoundException, DocumentException, IOException {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        Order createdOrder = mc.createOrder(simpleOrderRegistration);
        String filePath = pdfService.generateCommercial(createdOrder);

        assert(Files.exists(Path.of(filePath)));

        Files.delete(Path.of(filePath));
//        ResponseEntity<InputStreamResource> result  = mc.get_commercial(createdOrder.getId());
//        byte[] content = result.getBody().getInputStream().readAllBytes();
//        for (byte i: content
//             ) {
//            System.out.print((char) i);
//        }
    }

    @Test
    @DisplayName("Generating risks")
    void generatingRisks() throws ClientNotFoundException, SpecializationNotFoundException, ModSpecConflictException, ModificationNotFoundException, OrderNotFoundException, DocumentException, IOException {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        Order createdOrder = mc.createOrder(simpleOrderRegistration);
        String filePath = pdfService.generateRiskList(createdOrder);

        assert(Files.exists(Path.of(filePath)));

        Files.delete(Path.of(filePath));
    }

    @Test
    @DisplayName("Upload confirmation")
    void uploadConfirmation() throws ClientNotFoundException, SpecializationNotFoundException, ModSpecConflictException, ModificationNotFoundException, OrderNotFoundException, IOException {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        Order createdOrder = mc.createOrder(simpleOrderRegistration);

        Path path = Paths.get("/src/test/data/test_confirmation.docx");
        String name = "test_confirmation.docx";
        String originalFileName = "test_confirmation.docx";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        MultipartFile file = new MockMultipartFile(name,originalFileName,contentType,content);
        ResponseEntity<FileUploadResponse> response = mc.uploadConfirmation(file,createdOrder.getId());

        createdOrder = orderRepository.getOne(createdOrder.getId());
        Order finalCreatedOrder = createdOrder;
        assertAll(
                ()->assertTrue(Files.exists(Path.of(Objects.requireNonNull(response.getBody()).getFileName()))),
                ()-> assertEquals(finalCreatedOrder.getConfirmation(), Objects.requireNonNull(response.getBody()).getFileName())

        );

        Files.delete(Path.of(Objects.requireNonNull(response.getBody()).getFileName()));
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