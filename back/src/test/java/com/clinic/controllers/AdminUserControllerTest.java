package com.clinic.controllers;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.entities.enums.ERole;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import com.clinic.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AdminUserControllerTest {

    static AdminController ac;
    static UserController uc;

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PwdDropRequestRepository pwdDropRequestRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    User createdUser;
    String createdUserPassword;
    PwdDropRequest createdPwdDropRequest;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    @Transactional(rollbackFor = Exception.class)
    void beforeEach()
    {
        ac = new AdminController(adminService);
        uc = new UserController(userService);

        Person person = createTestPerson();

        createdUserPassword = "TEST_PASSWORD";
        String encodedPassword = passwordEncoder.encode(createdUserPassword);

        User user = new User();
        user.setRole(roleRepository.findByName(ERole.ROLE_MEDIC));
        user.setPassword(encodedPassword);
        user.setEmail("black.hornetnikita+admintest@gmail.com");
        user.setPerson(person);

        createdUser = userRepository.save(user);

        PwdDropRequest pwdDropRequest = new PwdDropRequest();
        pwdDropRequest.setUser(createdUser);
        pwdDropRequest.setRequestDate(Calendar.getInstance());
        createdPwdDropRequest = pwdDropRequestRepository.save(pwdDropRequest);
    }

    @AfterEach
    @Transactional(rollbackFor = Exception.class)
    void afterEach()
    {
        personRepository.delete(createdUser.getPerson());
        createdUser = null;
        createdUserPassword = null;
        createdPwdDropRequest = null;
    }

    @Test
    @DisplayName("Get all users")
    @Transactional(rollbackFor = Exception.class)
    void getAllUsers()
    {
        List<User> users = ac.getAllUsers();
        List<User> checkUsers = userRepository.findAll();

        assertIterableEquals(users, checkUsers);

        List<User> usersWithEmail = users.stream().filter(x -> Objects.equals(x.getEmail(), createdUser.getEmail())).collect(Collectors.toList());
        assertEquals(1, usersWithEmail.size());

        User user = usersWithEmail.get(0);

        assertEquals(createdUser, user);
    }

    @Test
    @DisplayName("Get all drop requests")
    @Transactional(rollbackFor = Exception.class)
    void getAllDropRequests()
    {
        List<PwdDropRequest> users = ac.getAllDropRequests();
        List<PwdDropRequest> checkUsers = pwdDropRequestRepository.findAll();

        assertIterableEquals(users, checkUsers);

        List<PwdDropRequest> pwdDropRequests = users.stream().filter(x -> Objects.equals(x.getUser(), createdUser)).collect(Collectors.toList());
        assertEquals(1, pwdDropRequests.size());

        PwdDropRequest pwdDropRequest = pwdDropRequests.get(0);

        assertEquals(createdPwdDropRequest, pwdDropRequest);
    }


    @Test
    @DisplayName("Create user with duplicate email")
    @Transactional(rollbackFor = Exception.class)
    void createUserDuplicateEmail()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setSurname("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setPatronymic("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-12-12"));
        simplePersonRegistration.setPassport(1000000011);

        SimpleUserRegistration simpleUserRegistration = new SimpleUserRegistration();
        simpleUserRegistration.setPerson(simplePersonRegistration);
        simpleUserRegistration.setEmail("black.hornetnikita+admintest@gmail.com");
        simpleUserRegistration.setRole("ROLE_SCIENTIST");
        simpleUserRegistration.setPassword("asdasdasd");

        assertThrows(UserConflictException.class, () -> ac.createUser(simpleUserRegistration));
    }

    @Test
    @DisplayName("Create user with duplicate role")
    @Transactional(rollbackFor = Exception.class)
    void createUserDuplicateRole()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setSurname("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setPatronymic("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-12-12"));
        simplePersonRegistration.setPassport(1000000011);

        SimpleUserRegistration simpleUserRegistration = new SimpleUserRegistration();
        simpleUserRegistration.setPerson(simplePersonRegistration);
        simpleUserRegistration.setEmail("black.hornetnikita+admintest1@gmail.com");
        simpleUserRegistration.setRole("ROLE_MEDIC");
        simpleUserRegistration.setPassword("asdasdasd");

        assertThrows(UserConflictException.class, () -> ac.createUser(simpleUserRegistration));
    }

    @Test
    @DisplayName("Create valid user")
    @Transactional(rollbackFor = Exception.class)
    void createUser()
    {
        SimplePersonRegistration simplePersonRegistration = new SimplePersonRegistration();
        simplePersonRegistration.setName("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setSurname("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setPatronymic("ADMIN CONTROLLER TEST");
        simplePersonRegistration.setDateOfBirth(Date.valueOf("1999-12-12"));
        simplePersonRegistration.setPassport(1000000011);

        SimpleUserRegistration simpleUserRegistration = new SimpleUserRegistration();
        simpleUserRegistration.setPerson(simplePersonRegistration);
        simpleUserRegistration.setEmail("black.hornetnikita+admintest1@gmail.com");
        simpleUserRegistration.setRole("ROLE_SCIENTIST");
        simpleUserRegistration.setPassword("asdasdasd");

        assertDoesNotThrow(() -> Thread.sleep(10000));
        User user = assertDoesNotThrow(() -> ac.createUser(simpleUserRegistration));

        Optional<User> optionalCreatedUser = userRepository.findById(user.getId());
        assertTrue(optionalCreatedUser.isPresent());

        assertEquals(optionalCreatedUser.get(), user);
    }

    @Test
    @DisplayName("Create password reset request for non existing user")
    @Transactional(rollbackFor = Exception.class)
    void createPwdResetRequestNonExistingUser()
    {
        SimplePwdDropRequest simplePwdDropRequest = new SimplePwdDropRequest();
        simplePwdDropRequest.setEmail("black.hornetnikita+admintestnonexistent@gmail.com");

        assertThrows(UserNotFoundException.class, () -> uc.createPwdDropRequest(simplePwdDropRequest));
    }

    @Test
    @DisplayName("Create password reset request for existing user")
    @Transactional(rollbackFor = Exception.class)
    void createPwdResetRequestExistingUser()
    {
        SimplePwdDropRequest simplePwdDropRequest = new SimplePwdDropRequest();
        simplePwdDropRequest.setEmail("black.hornetnikita+admintest@gmail.com");

        PwdDropRequest createdPwdDropRequest = assertDoesNotThrow(() -> uc.createPwdDropRequest(simplePwdDropRequest));

        Optional<PwdDropRequest> pwdDropRequest = pwdDropRequestRepository.findById(createdPwdDropRequest.getId());
        assertTrue(pwdDropRequest.isPresent());

        assertEquals(createdPwdDropRequest, pwdDropRequest.get());
    }

    @Test
    @DisplayName("Satisfy non existent password reset request")
    @Transactional(rollbackFor = Exception.class)
    void satisfyNonExistentPwdResetRequest()
    {
        SimplePwdDropRequestSatisfaction simplePwdDropRequestSatisfaction = new SimplePwdDropRequestSatisfaction();
        simplePwdDropRequestSatisfaction.setId(1000000000);

        assertThrows(PwdDropRequestNotFoundException.class, () -> ac.satisfyPwdDropRequest(simplePwdDropRequestSatisfaction));
    }

    @Test
    @DisplayName("Satisfy already satisfied password reset request")
    @Transactional(rollbackFor = Exception.class)
    void satisfyAlreadySatisfiedPwdResetRequest()
    {
        createdPwdDropRequest.setDropped(true);
        pwdDropRequestRepository.save(createdPwdDropRequest);

        SimplePwdDropRequestSatisfaction simplePwdDropRequestSatisfaction = new SimplePwdDropRequestSatisfaction();
        simplePwdDropRequestSatisfaction.setId(createdPwdDropRequest.getId());

        assertThrows(PwdDropRequestAlreadySatisfiedException.class, () -> ac.satisfyPwdDropRequest(simplePwdDropRequestSatisfaction));
    }

    @Test
    @DisplayName("Satisfy valid password reset request")
    @Transactional(rollbackFor = Exception.class)
    void satisfyValidPwdResetRequest()
    {
        SimplePwdDropRequestSatisfaction simplePwdDropRequestSatisfaction = new SimplePwdDropRequestSatisfaction();
        simplePwdDropRequestSatisfaction.setId(createdPwdDropRequest.getId());

        assertDoesNotThrow(() -> Thread.sleep(10000));
        PwdDropRequest pwdDropRequest = assertDoesNotThrow(() -> ac.satisfyPwdDropRequest(simplePwdDropRequestSatisfaction));

        Optional<PwdDropRequest> updatedPwdDropRequest = pwdDropRequestRepository.findById(pwdDropRequest.getId());
        assertTrue(updatedPwdDropRequest.isPresent());

        assertEquals(pwdDropRequest, updatedPwdDropRequest.get());

        assertTrue(pwdDropRequest.isDropped());
    }

    @Transactional(rollbackFor = Exception.class)
    public Person createTestPerson()
    {
        Passport passport = new Passport();
        passport.setPassport(1000000011);

        List<Passport> passports = new ArrayList<>();
        passports.add(passport);

        Person person = new Person();
        person.setName("ADMIN CONTROLLER TEST");
        person.setSurname("ADMIN CONTROLLER TEST");
        person.setPatronymic("ADMIN CONTROLLER TEST");
        person.setDateOfBirth(Calendar.getInstance().getTime());
        person.setPassports(passports);
        person = personRepository.save(person);

        passport.setPerson(person);
        passportRepository.save(passport);

        return person;
    }

}