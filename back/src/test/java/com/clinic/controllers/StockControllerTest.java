package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.entities.enums.ERole;
import com.clinic.entities.keys.StockId;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import com.clinic.services.AdminService;
import com.clinic.services.StockService;
import com.clinic.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class StockControllerTest {

    static StockController sc;

    @Autowired
    private StockService stockService;

    @Autowired
    PassportRepository passportRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Person createdPerson;
    User createdUser;
    Stock createdItem;

    @BeforeEach
    @Transactional(rollbackFor = Exception.class)
    void beforeEach()
    {
        sc = new StockController(stockService);

        createdPerson = createPerson();
        createdUser = createUser(createdPerson);
        createdItem = createItem(createdUser);
    }

    @Transactional(rollbackFor = Exception.class)
    Person createPerson()
    {
        Passport passport = new Passport();
        passport.setPassport(1000000011);

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
    User createUser(Person person)
    {
        String createdUserPassword = "TEST_PASSWORD";
        String encodedPassword = passwordEncoder.encode(createdUserPassword);

        User user = new User();
        user.setRole(roleRepository.findByName(ERole.ROLE_MEDIC));
        user.setPassword(encodedPassword);
        user.setEmail("black.hornetnikita+admintest@gmail.com");
        user.setPerson(person);

        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    Stock createItem(User user)
    {
        Stock stock = new Stock();
        StockId stockId = new StockId();
        stockId.setId(1001);
        stockId.setVendor(1001);
        stock.setStockId(stockId);
        stock.setName("STOCK CONTROLLER TEST");
        stock.setAmount(100);
        stock.setMinAmount(10);
        stock.setDescription("STOCK CONTROLLER TEST");
        stock.setLastUpdateTime(Calendar.getInstance());
        stock.setUser(user);

        return stockRepository.save(stock);
    }

    @AfterEach
    @Transactional(rollbackFor = Exception.class)
    void afterEach()
    {
        stockRepository.deleteAllByUser(createdUser);
        personRepository.delete(createdPerson);
        createdPerson = null;
        createdUser = null;
        createdItem = null;
    }

    @Test
    @DisplayName("Get all stock items")
    @Transactional(rollbackFor = Exception.class)
    void getAllStockItems()
    {
        List<Stock> items = sc.getAllItems();
        List<Stock> checkItems = stockRepository.findAll();

        assertIterableEquals(items, checkItems);

        List<Stock> createdItems = items.stream().filter(x -> Objects.equals(x.getStockId(), createdItem.getStockId())).collect(Collectors.toList());
        assertEquals(1, createdItems.size());

        Stock stock = createdItems.get(0);

        assertEquals(createdItem, stock);
    }

    @Test
    @DisplayName("Create already existing stock item")
    @Transactional(rollbackFor = Exception.class)
    void createAlreadyExistingStockItem()
    {
        SimpleStockCreate simpleStockCreate = new SimpleStockCreate();
        simpleStockCreate.setId(1001);
        simpleStockCreate.setVendorId(1001);
        simpleStockCreate.setDescription("STOCK CONTROLLER TEST");
        simpleStockCreate.setUserId(createdUser.getId());
        simpleStockCreate.setName("STOCK CONTROLLER TEST");
        simpleStockCreate.setAmount(100);
        simpleStockCreate.setMinAmount(10);

        assertThrows(StockConflictException.class, () -> sc.createStockItem(simpleStockCreate));
    }

    @Test
    @DisplayName("Create invalid data stock item")
    @Transactional(rollbackFor = Exception.class)
    void createInvalidDataStockItem()
    {
        SimpleStockCreate simpleStockCreate = new SimpleStockCreate();
        simpleStockCreate.setId(1002);
        simpleStockCreate.setVendorId(1002);
        simpleStockCreate.setDescription("STOCK CONTROLLER TEST");
        simpleStockCreate.setUserId(createdUser.getId());
        simpleStockCreate.setName("STOCK CONTROLLER TEST");
        simpleStockCreate.setAmount(-1);
        simpleStockCreate.setMinAmount(10);

        assertThrows(InvalidStockDataException.class, () -> sc.createStockItem(simpleStockCreate));

        simpleStockCreate.setAmount(100);
        simpleStockCreate.setMinAmount(-1);

        assertThrows(InvalidStockDataException.class, () -> sc.createStockItem(simpleStockCreate));
    }

    @Test
    @DisplayName("Create invalid user stock item")
    @Transactional(rollbackFor = Exception.class)
    void createInvalidUserStockItem()
    {
        SimpleStockCreate simpleStockCreate = new SimpleStockCreate();
        simpleStockCreate.setId(1002);
        simpleStockCreate.setVendorId(1002);
        simpleStockCreate.setDescription("STOCK CONTROLLER TEST");
        simpleStockCreate.setUserId(1000000);
        simpleStockCreate.setName("STOCK CONTROLLER TEST");
        simpleStockCreate.setAmount(100);
        simpleStockCreate.setMinAmount(10);

        assertThrows(UserNotFoundException.class, () -> sc.createStockItem(simpleStockCreate));
    }

    @Test
    @DisplayName("Create valid stock item")
    @Transactional(rollbackFor = Exception.class)
    void createValidStockItem()
    {
        SimpleStockCreate simpleStockCreate = new SimpleStockCreate();
        simpleStockCreate.setId(1002);
        simpleStockCreate.setVendorId(1002);
        simpleStockCreate.setDescription("STOCK CONTROLLER TEST");
        simpleStockCreate.setUserId(createdUser.getId());
        simpleStockCreate.setName("STOCK CONTROLLER TEST");
        simpleStockCreate.setAmount(100);
        simpleStockCreate.setMinAmount(10);

        Stock stock = assertDoesNotThrow(() -> sc.createStockItem(simpleStockCreate));
        Optional<Stock> checkStock = stockRepository.findByStockId(stock.getStockId());

        assertTrue(checkStock.isPresent());
        assertEquals(stock, checkStock.get());
    }

    @Test
    @DisplayName("Update non existent stock item")
    @Transactional(rollbackFor = Exception.class)
    void updateNonExistentStockItem()
    {
        SimpleStockUpdate simpleStockUpdate = new SimpleStockUpdate();
        simpleStockUpdate.setId(1002);
        simpleStockUpdate.setVendorId(1002);
        simpleStockUpdate.setDescription("STOCK CONTROLLER TEST");
        simpleStockUpdate.setUserId(createdUser.getId());
        simpleStockUpdate.setName("STOCK CONTROLLER TEST");
        simpleStockUpdate.setAmount(100);
        simpleStockUpdate.setMinAmount(10);

        assertThrows(StockNotFoundException.class, () -> sc.updateStockItem(simpleStockUpdate));
    }

    @Test
    @DisplayName("Update existent stock item with invalid user")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItemInvalidUser()
    {
        SimpleStockUpdate simpleStockUpdate = new SimpleStockUpdate();
        simpleStockUpdate.setId(1001);
        simpleStockUpdate.setVendorId(1001);
        simpleStockUpdate.setDescription("STOCK CONTROLLER TEST");
        simpleStockUpdate.setUserId(10000000);
        simpleStockUpdate.setName("STOCK CONTROLLER TEST");
        simpleStockUpdate.setAmount(100);
        simpleStockUpdate.setMinAmount(10);

        assertThrows(UserNotFoundException.class, () -> sc.updateStockItem(simpleStockUpdate));
    }

    @Test
    @DisplayName("Update existent stock item with invalid data")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItemInvalidData()
    {
        SimpleStockUpdate simpleStockUpdate = new SimpleStockUpdate();
        simpleStockUpdate.setId(1001);
        simpleStockUpdate.setVendorId(1001);
        simpleStockUpdate.setDescription("STOCK CONTROLLER TEST");
        simpleStockUpdate.setUserId(createdUser.getId());
        simpleStockUpdate.setName("STOCK CONTROLLER TEST");
        simpleStockUpdate.setAmount(-1);
        simpleStockUpdate.setMinAmount(10);

        assertThrows(InvalidStockDataException.class, () -> sc.updateStockItem(simpleStockUpdate));

        simpleStockUpdate.setAmount(100);
        simpleStockUpdate.setMinAmount(-1);

        assertThrows(InvalidStockDataException.class, () -> sc.updateStockItem(simpleStockUpdate));

    }

    @Test
    @DisplayName("Update existent stock item")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItem()
    {
        SimpleStockUpdate simpleStockUpdate = new SimpleStockUpdate();
        simpleStockUpdate.setId(1001);
        simpleStockUpdate.setVendorId(1001);
        simpleStockUpdate.setDescription("STOCK CONTROLLER TEST");
        simpleStockUpdate.setUserId(createdUser.getId());
        simpleStockUpdate.setName("STOCK CONTROLLER TEST");
        simpleStockUpdate.setAmount(100);
        simpleStockUpdate.setMinAmount(10);

        Stock stock = assertDoesNotThrow(() -> sc.updateStockItem(simpleStockUpdate));
        Optional<Stock> checkStock = stockRepository.findByStockId(stock.getStockId());

        assertTrue(checkStock.isPresent());
        assertEquals(stock, checkStock.get());
    }

    @Test
    @DisplayName("Update non existent stock item amount")
    @Transactional(rollbackFor = Exception.class)
    void updateNonExistentStockItemAmount()
    {
        SimpleStockAmountUpdate simpleStockAmountUpdate = new SimpleStockAmountUpdate();
        simpleStockAmountUpdate.setId(1002);
        simpleStockAmountUpdate.setVendorId(1002);
        simpleStockAmountUpdate.setChange(10);

        assertThrows(StockNotFoundException.class, () -> sc.updateStockItemAmount(simpleStockAmountUpdate));
    }

    @Test
    @DisplayName("Update existent stock item invalid amount")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItemInvalidAmount() {
        SimpleStockAmountUpdate simpleStockAmountUpdate = new SimpleStockAmountUpdate();
        simpleStockAmountUpdate.setId(1001);
        simpleStockAmountUpdate.setVendorId(1001);
        simpleStockAmountUpdate.setChange(-105);

        assertThrows(InvalidStockDataException.class, () -> sc.updateStockItemAmount(simpleStockAmountUpdate));
    }

    @Test
    @DisplayName("Update existent stock item valid amount")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItemValidAmount()
    {
        SimpleStockAmountUpdate simpleStockAmountUpdate = new SimpleStockAmountUpdate();
        simpleStockAmountUpdate.setId(1001);
        simpleStockAmountUpdate.setVendorId(1001);
        simpleStockAmountUpdate.setChange(10);

        Stock stock = assertDoesNotThrow(() -> sc.updateStockItemAmount(simpleStockAmountUpdate));
        Optional<Stock> checkStock = stockRepository.findByStockId(stock.getStockId());

        assertTrue(checkStock.isPresent());
        assertEquals(stock, checkStock.get());
        assertEquals(checkStock.get().getAmount(), 110);

        simpleStockAmountUpdate.setChange(-10);

        stock = assertDoesNotThrow(() -> sc.updateStockItemAmount(simpleStockAmountUpdate));
        checkStock = stockRepository.findByStockId(stock.getStockId());

        assertTrue(checkStock.isPresent());
        assertEquals(stock, checkStock.get());
        assertEquals(checkStock.get().getAmount(), 100);
    }

    @Test
    @DisplayName("Update non existent stock item min amount")
    @Transactional(rollbackFor = Exception.class)
    void updateNonExistentStockItemMinAmount()
    {
        SimpleStockMinAmountUpdate simpleStockAmountUpdate = new SimpleStockMinAmountUpdate();
        simpleStockAmountUpdate.setId(1002);
        simpleStockAmountUpdate.setVendorId(1002);
        simpleStockAmountUpdate.setMinAmount(15);

        assertThrows(StockNotFoundException.class, () -> sc.updateStockItemMinAmount(simpleStockAmountUpdate));
    }

    @Test
    @DisplayName("Update existent stock item invalid min amount")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItemInvalidMinAmount()
    {
        SimpleStockMinAmountUpdate simpleStockAmountUpdate = new SimpleStockMinAmountUpdate();
        simpleStockAmountUpdate.setId(1001);
        simpleStockAmountUpdate.setVendorId(1001);
        simpleStockAmountUpdate.setMinAmount(-1);

        assertThrows(InvalidStockDataException.class, () -> sc.updateStockItemMinAmount(simpleStockAmountUpdate));
    }

    @Test
    @DisplayName("Update existent stock item valid min amount")
    @Transactional(rollbackFor = Exception.class)
    void updateExistentStockItemValidMinAmount()
    {
        SimpleStockMinAmountUpdate simpleStockMinAmountUpdate = new SimpleStockMinAmountUpdate();
        simpleStockMinAmountUpdate.setId(1001);
        simpleStockMinAmountUpdate.setVendorId(1001);
        simpleStockMinAmountUpdate.setMinAmount(20);

        Stock stock = assertDoesNotThrow(() -> sc.updateStockItemMinAmount(simpleStockMinAmountUpdate));
        Optional<Stock> checkStock = stockRepository.findByStockId(stock.getStockId());

        assertTrue(checkStock.isPresent());
        assertEquals(stock, checkStock.get());

        assertEquals(checkStock.get().getMinAmount(), 20);
    }

}