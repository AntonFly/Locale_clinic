package com.clinic.impl;

import com.clinic.dto.SimplePwdDropRequestSatisfaction;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.Person;
import com.clinic.entities.PwdDropRequest;
import com.clinic.entities.Role;
import com.clinic.entities.User;
import com.clinic.entities.enums.ERole;
import com.clinic.exceptions.*;
import com.clinic.repositories.PwdDropRequestRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService{

    private final JavaMailSender sender;
    private final SimpleMailMessage registrationMessage;
    private final SimpleMailMessage pwdResetMessage;

    private final PasswordEncoder passwordEncoder;

    private final PersonService personService;
    private final UserService userService;

    private final RoleRepository roleRepository;
    private final PwdDropRequestRepository pwdDropRequestRepository;

    @Autowired
    public AdminServiceImpl(
            JavaMailSender jms,
            @Qualifier("registrationMessage") SimpleMailMessage rm,
            @Qualifier("passwordResetMessage") SimpleMailMessage prm,
            PasswordEncoder pe,
            PersonService ps,
            UserService us,
            RoleRepository rp,
            PwdDropRequestRepository pwdr)
    {
        this.sender = jms;
        this.registrationMessage = rm;
        this.pwdResetMessage = prm;
        this.passwordEncoder = pe;
        this.personService = ps;
        this.userService = us;
        this.roleRepository = rp;
        this.pwdDropRequestRepository = pwdr;
    }

    @Override
    public List<PwdDropRequest> getAllPwdDropRequests()
    { return pwdDropRequestRepository.findAll(); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PwdDropRequest satisfyDropRequest(SimplePwdDropRequestSatisfaction dropRequestData)
            throws PwdDropRequestNotFoundException, PwdDropRequestAlreadySatisfiedException
    {
        PwdDropRequest pwdDropRequest = pwdDropRequestRepository.findById(dropRequestData.getId())
                .orElseThrow(() -> new PwdDropRequestNotFoundException(dropRequestData.getId()));

        if (pwdDropRequest.isDropped())
            throw new PwdDropRequestAlreadySatisfiedException(pwdDropRequest.getId(), pwdDropRequest.getDropDate());

        User user = pwdDropRequest.getUser();

        String password = "LAMAOOOO";//alphaNumericString(10);
        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);
        userService.save(user);

        String text = String.format(pwdResetMessage.getText(),
                user.getPerson().getName(),
                user.getPerson().getSurname(),
                user.getEmail(),
                password
        );

        SimpleMailMessage message = new SimpleMailMessage();
        pwdResetMessage.copyTo(message);
        message.setTo(user.getEmail());
        message.setText(text);

        pwdDropRequest.setDropped(true);
        pwdDropRequest.setDropDate(Calendar.getInstance());
        pwdDropRequest = pwdDropRequestRepository.save(pwdDropRequest);

        sender.send(message);

        return pwdDropRequest;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(SimpleUserRegistration userData)
            throws UserConflictException, PassportConflictException, PersonConflictException
    {
        Person person;
        Optional<Person> optionalPerson = personService.getPersonByPassportNum(userData.getPerson().getPassport());
        if (optionalPerson.isPresent())
            person = optionalPerson.get();
        else
            person = personService.createPerson(userData.getPerson());

        User user = new User();
        Role role = roleRepository.findByName(ERole.valueOf(userData.getRole()));

        if (userService.getUsersByPersonId(person.getId()).stream().anyMatch(tmpUser -> tmpUser.getRole() == role))
            throw new UserConflictException("role", role.getName().name());

        if (userService.existsByEmail(userData.getEmail()))
            throw new UserConflictException("email", userData.getEmail());

        user.setPerson(person);
        user.setRole(role);
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));

        user = userService.save(user);

        String text = String.format(registrationMessage.getText(),
                person.getName(),
                person.getSurname(),
                user.getEmail(),
                    user.getPassword()
                );

        SimpleMailMessage message = new SimpleMailMessage();
        registrationMessage.copyTo(message);
        message.setTo(user.getEmail());
        message.setText(text);
        //sender.send(message);

        return user;
    }

    @Override
    public User changeUserData(User user) {

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
