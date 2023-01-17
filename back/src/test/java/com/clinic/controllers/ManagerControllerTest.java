package com.clinic.controllers;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleOrderRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ManagerControllerTest {
    static ManagerController mc;

    @Autowired
    private ClientService cs;
    @Autowired
    private ModificationService ms;
    @Autowired
    private OrderService os;
    @Autowired
    private PDFService pdfService;
    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private SpecializationService ss;


    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ModificationRepository modificationRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    SpecializationRepository specializationRepository;

    Person createdPerson;
    Person createdPersonWithoutClient;
    Client createdClient;
    SimpleClientRegistration simpleClientRegistration;

    @BeforeEach
    @Transactional(rollbackFor = Exception.class)
    void setUp()
    {
        mc = new ManagerController(ss, ms, cs, os, scenarioService, pdfService);

        createdPerson = createPerson(1100000011);
        createdPersonWithoutClient = createPerson(1100000012);
        createdClient = createClient(createdPerson);
    }

    @Transactional(rollbackFor = Exception.class)
    Person createPerson(long passportNum) {
        Passport passport = new Passport();
        passport.setPassport(passportNum);

        List<Passport> passports = new ArrayList<>();
        passports.add(passport);

        Person person = new Person();
        person.setName("MANAGER CONTROLLER TEST");
        person.setSurname("MANAGER CONTROLLER TEST");
        person.setPatronymic("MANAGER CONTROLLER TEST");
        Calendar calendar = Calendar.getInstance();
        person.setDateOfBirth(Date.valueOf("1999-12-12"));
        person.setPassports(passports);
        person = personRepository.save(person);

        passport.setPerson(person);
        passportRepository.save(passport);

        return person;
    }

    @Transactional(rollbackFor = Exception.class)
    Client createClient(Person person)
    {
        Client client = new Client();
        client.setComment("MANAGER CONTROLLER TEST");
        client.setEmail("black.hornetnikita+managertest@gmail.com");
        client.setPerson(person);

        return clientRepository.save(client);
    }

    @AfterEach
    @Transactional(rollbackFor = Exception.class)
    void afterEach()
    {
        personRepository.delete(createdPerson);
        personRepository.delete(createdPersonWithoutClient);
    }

    @Test
    @DisplayName("Get all available specifications")
    @Transactional(rollbackFor = Exception.class)
    void getSpecs()
    { assertIterableEquals(mc.getSpecs(), specializationRepository.findAll()); }

    @Test
    @DisplayName("Get all available modifications")
    @Transactional(rollbackFor = Exception.class)
    void getMods()
    { assertIterableEquals(mc.getMods(), modificationRepository.findAll()); }

    @Test
    @DisplayName("Get all existing clients")
    @Transactional(rollbackFor = Exception.class)
    void getClients()
    { assertIterableEquals(mc.getClients(), clientRepository.findAll()); }

    @Test
    @DisplayName("Getting not existing passport client")
    @Transactional(rollbackFor = Exception.class)
    void clientNonExistentPassport()
    { assertThrows(PassportNotFoundException.class, () -> mc.clientExists(2211334455L)); }

    @Test
    @DisplayName("Getting not existing client with person")
    @Transactional(rollbackFor = Exception.class)
    void clientNonExistentClient()
    { assertThrows(NoPersonToClientException.class, () -> mc.clientExists(createdPersonWithoutClient.getPassports().get(0).getPassport())); }

    @Test
    @DisplayName("Getting existing client")
    @Transactional(rollbackFor = Exception.class)
    void clientExists()
    {
        Client client = assertDoesNotThrow(() -> mc.clientExists(createdPerson.getPassports().get(0).getPassport()));
        assertEquals(client, createdClient);
    }

    @Test
    @DisplayName("Invalid new client")
    @Transactional(rollbackFor = Exception.class)
    void createAlreadyExistingClient()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setPassport(createdPerson.getPassports().get(0).getPassport());
        simplePersonRegistration.setName(createdPerson.getName());
        simplePersonRegistration.setSurname(createdPerson.getSurname());
        simplePersonRegistration.setPatronymic(createdPerson.getPatronymic());
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-12-12"));

        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail(createdClient.getEmail());
        simpleClientRegistration.setComment(createdClient.getComment());
        simpleClientRegistration.setPerson(simplePersonRegistration);

        assertThrows(ClientConflictException.class,()->mc.createClient(simpleClientRegistration));
    }

    @Test
    @DisplayName("Valid new client")
    @Transactional(rollbackFor = Exception.class)
    void createValidClient()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setPassport(createdPersonWithoutClient.getPassports().get(0).getPassport());
        simplePersonRegistration.setName(createdPersonWithoutClient.getName());
        simplePersonRegistration.setSurname(createdPersonWithoutClient.getSurname());
        simplePersonRegistration.setPatronymic(createdPersonWithoutClient.getPatronymic());
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-12-12"));

        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("black.hornetnikita+managervalidclienttest@gmail.com");
        simpleClientRegistration.setComment("MANAGER CONTROLLER TEST");
        simpleClientRegistration.setPerson(simplePersonRegistration);

        Client client = assertDoesNotThrow(() -> mc.createClient(simpleClientRegistration));
        Optional<Client> clientCheck = clientRepository.findById(client.getId());

        assertTrue(clientCheck.isPresent());
        assertEquals(client, clientCheck.get());
    }

    @Test
    @DisplayName("Get modifications for non existent specialization")
    @Transactional(rollbackFor = Exception.class)
    void getModsByNotExistingSpec()
    { assertThrows(SpecializationNotFoundException.class, () -> mc.getModsBySpec(111111111)); }

    @Test
    @DisplayName("Get all modifications for specialization")
    @Transactional(rollbackFor = Exception.class)
    void getModsBySpec()
    {
        Set<Modification> checkModifications = modificationRepository.findAll().stream()
                .filter(m -> m.getSpecialization().stream().anyMatch(s -> s.getId() == 5))
                .collect(Collectors.toSet());

        Set<Modification> modifications = assertDoesNotThrow(() -> mc.getModsBySpec(5));

        assertIterableEquals(modifications, checkModifications);
    }


    @Test
    @DisplayName("Create order non existent client")
    @Transactional(rollbackFor = Exception.class)
    void createOrderNonExistentClient()
    {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration();
        simpleOrderRegistration.setClientId(10000000L);
        simpleOrderRegistration.setSpecId(5L);
        simpleOrderRegistration.setModIds(Arrays.asList(2L, 5L));
        simpleOrderRegistration.setComment("MANAGER CONTROLLER TEST");

        assertThrows(ClientNotFoundException.class, () -> mc.createOrder(simpleOrderRegistration));
    }


    @Test
    @DisplayName("Create order non existent specialization")
    @Transactional(rollbackFor = Exception.class)
    void createOrderNonExistentSpecialization()
    {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration();
        simpleOrderRegistration.setClientId(createdClient.getId());
        simpleOrderRegistration.setSpecId(100000000L);
        simpleOrderRegistration.setModIds(Arrays.asList(2L, 5L));
        simpleOrderRegistration.setComment("MANAGER CONTROLLER TEST");

        assertThrows(SpecializationNotFoundException.class, () -> mc.createOrder(simpleOrderRegistration));
    }


    @Test
    @DisplayName("Create order non existent modification")
    @Transactional(rollbackFor = Exception.class)
    void createOrderNonExistentModification()
    {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration();
        simpleOrderRegistration.setClientId(createdClient.getId());
        simpleOrderRegistration.setSpecId(5L);
        simpleOrderRegistration.setModIds(Arrays.asList(2L, 100000000L));
        simpleOrderRegistration.setComment("MANAGER CONTROLLER TEST");

        assertThrows(ModificationNotFoundException.class, () -> mc.createOrder(simpleOrderRegistration));
    }


    @Test
    @DisplayName("Create valid order")
    @Transactional(rollbackFor = Exception.class)
    void createOrder()
    {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration();
        simpleOrderRegistration.setClientId(createdClient.getId());
        simpleOrderRegistration.setSpecId(5L);
        simpleOrderRegistration.setModIds(Arrays.asList(2L, 5L));
        simpleOrderRegistration.setComment("MANAGER CONTROLLER TEST");

        Order order = assertDoesNotThrow(() -> mc.createOrder(simpleOrderRegistration));
        Optional<Order> checkOrder = orderRepository.findById(order.getId());

        assertTrue(checkOrder.isPresent());
        assertEquals(order, checkOrder.get());
    }

    @Test
    @DisplayName("Change non existent client")
    @Transactional(rollbackFor = Exception.class)
    void changeNonExistentClient()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setPassport(createdPersonWithoutClient.getPassports().get(0).getPassport());
        simplePersonRegistration.setName(createdPersonWithoutClient.getName());
        simplePersonRegistration.setSurname(createdPersonWithoutClient.getSurname());
        simplePersonRegistration.setPatronymic(createdPersonWithoutClient.getPatronymic());
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-12-12"));

        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("black.hornetnikita+managervalidclienttest@gmail.com");
        simpleClientRegistration.setComment("MANAGER CONTROLLER TEST");
        simpleClientRegistration.setPerson(simplePersonRegistration);
    }


    @Test
    @DisplayName("Change client")
    @Transactional(rollbackFor = Exception.class)
    void changeClient()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setPassport(1100000013);
        simplePersonRegistration.setName("MANAGER CHANGE TEST");
        simplePersonRegistration.setSurname("MANAGER CHANGE TEST");
        simplePersonRegistration.setPatronymic("MANAGER CHANGE TEST");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-10-10"));

        SimpleClientRegistration simpleClientRegistration = new SimpleClientRegistration();
        simpleClientRegistration.setEmail("black.hornetnikita+managerchangeclienttest@gmail.com");
        simpleClientRegistration.setComment("MANAGER CHANGE TEST");
        simpleClientRegistration.setPerson(simplePersonRegistration);

        Client changedClient = assertDoesNotThrow(() -> mc.changeClient(simpleClientRegistration, createdClient.getId()));
        Optional<Client> checkClient = clientRepository.findById(changedClient.getId());

        assertTrue(checkClient.isPresent());
        assertEquals(changedClient, checkClient.get());

        List<Long> checkPassports = new ArrayList<>();
        checkPassports.add(simplePersonRegistration.getPassport());

        assertIterableEquals(changedClient.getPerson().getPassports().stream().map(Passport::getPassport).collect(Collectors.toList()), checkPassports);
        assertEquals(changedClient.getPerson().getName(), simplePersonRegistration.getName());
        assertEquals(changedClient.getPerson().getSurname(), simplePersonRegistration.getSurname());
        assertEquals(changedClient.getPerson().getPatronymic(), simplePersonRegistration.getPatronymic());
        assertEquals(changedClient.getPerson().getDateOfBirth(), simplePersonRegistration.getDateOfBirth());

        assertEquals(changedClient.getEmail(), simpleClientRegistration.getEmail());
        assertEquals(changedClient.getComment(), simpleClientRegistration.getComment());
    }

    @Test
    @DisplayName("Generating commercial")
    @Transactional(rollbackFor = Exception.class)
    void generatingCommercial() throws ClientNotFoundException, SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, DocumentException, IOException {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        Order createdOrder = mc.createOrder(simpleOrderRegistration);
        String filePath = pdfService.generateCommercial(createdOrder);

        assert(Files.exists(Paths.get(filePath)));

        Files.delete(Paths.get(filePath));
//        ResponseEntity<InputStreamResource> result  = mc.get_commercial(createdOrder.getId());
//        byte[] content = result.getBody().getInputStream().readAllBytes();
//        for (byte i: content
//             ) {
//            System.out.print((char) i);
//        }
    }

    @Test
    @DisplayName("Generating risks")
    @Transactional(rollbackFor = Exception.class)
    void generatingRisks() throws ClientNotFoundException, SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, DocumentException, IOException {
        SimpleOrderRegistration simpleOrderRegistration = new SimpleOrderRegistration(
                createdClient.getId(),
                1L,
                Arrays.asList(2L, 5L),
                "Test Order"
        );
        Order createdOrder = mc.createOrder(simpleOrderRegistration);
        String filePath = pdfService.generateRiskList(createdOrder);

        assert(Files.exists(Paths.get(filePath)));

        Files.delete(Paths.get(filePath));
    }

    @Test
    @DisplayName("Upload confirmation")
    @Transactional(rollbackFor = Exception.class)
    void uploadConfirmation() throws ClientNotFoundException, SpecializationNotFoundException, ModificationNotFoundException, OrderNotFoundException, IOException {
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
                ()->assertTrue(Files.exists(Paths.get("confirmation/"+Objects.requireNonNull(response.getBody()).getFileName()))),
                ()-> assertEquals(finalCreatedOrder.getConfirmation(), Objects.requireNonNull(response.getBody()).getFileName())

        );

        Files.delete(Paths.get("confirmation/"+Objects.requireNonNull(response.getBody()).getFileName()));
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