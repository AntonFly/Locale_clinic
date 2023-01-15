package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.impl.PDFServiceImpl;
import com.clinic.repositories.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EngineerControllerTest {

    @Autowired
    EngineerController ec;

    @Autowired
    ScientistController sc;

    @Autowired
    PDFServiceImpl pdfService;
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

    @BeforeEach
    void startUp() throws PersonConflictException, ClientConflictException, PassportConflictException, ClientNotFoundException, SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, ScenarioOrderException {

        simpleClientRegistration = generateTestPerson();
        createdClient = managerController.createClient(simpleClientRegistration);

        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        createdOrder = managerController.createOrder(simpleOrderRegistration);

        sc.createScenario(new SimpleScenarioRegistration(1L, createdOrder.getId(), Arrays.asList(1L)));


    }

    @AfterEach
    void afterEach(){
        personRepository.delete(createdClient.getPerson());
        createdClient = null;
    }

    @Test
    @DisplayName("Upload genome")
    void uploadGenome() {

        Path path = Paths.get("/src/test/data/test_genome.docx");
        String name = "test_genome.docx";
        String originalFileName = "test_genome.docx";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        MultipartFile file = new MockMultipartFile(name,originalFileName,contentType,content);
        ResponseEntity<FileUploadResponse> response = assertDoesNotThrow(()-> ec.uploadGenome(file,createdOrder.getId()));

        createdOrder = orderRepository.getOne(createdOrder.getId());
        Order finalCreatedOrder = createdOrder;
        assertAll(
                ()->assertTrue(Files.exists(Paths.get("genome/"+Objects.requireNonNull(response.getBody()).getFileName()))),
                ()-> assertEquals(finalCreatedOrder.getGenome(), Objects.requireNonNull(response.getBody()).getFileName())

        );

        assertDoesNotThrow(()-> Files.delete(Paths.get("genome/"+Objects.requireNonNull(response.getBody()).getFileName())));

    }

    @Test
    @DisplayName("Getting scenario")
    void getScenario() throws NoScenarioForOrderException, DocumentException, IOException, OrderNotFoundException, ScenarioOrderException, SpecializationNotFoundException, ModificationNotFoundException {
        createdOrder = orderRepository.getOne(createdOrder.getId());
        String filePath = pdfService.generateScenario(createdOrder);

        assert(Files.exists(Paths.get(filePath)));

        Files.delete(Paths.get(filePath));
    }

    @Test
    @DisplayName("Getting scenario invalid")
    void getScenarioInvalid() {
        assertThrows(NoScenarioForOrderException.class,()->pdfService.generateScenario(createdOrder));

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