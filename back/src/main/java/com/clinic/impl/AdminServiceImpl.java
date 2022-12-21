package com.clinic.impl;

import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.Person;
import com.clinic.entities.Role;
import com.clinic.entities.User;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.UserConflictException;
import com.clinic.repositories.RoleRepository;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private JavaMailSender sender;
    private final SimpleMailMessage registrationMessage;
    private PersonService personService;
    private UserService userService;

    private RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(
            JavaMailSender jms,
            @Qualifier("registrationMessage") SimpleMailMessage rm,
            PersonService ps,
            UserService us,
            RoleRepository rp) {
        this.personService = ps;
        this.roleRepository = rp;
        this.userService = us;
        this.sender = jms;
        this.registrationMessage = rm;
    }


    @Override
    public User createUser(SimpleUserRegistration userData)
            throws UserConflictException, PersonConflictException, PassportConflictException {
        Person person = personService.save(userData.getPerson());

        User user = new User();
        Role role = roleRepository.findByRole(userData.getRole());

        if (userService.getUsersByPersonId(person.getId()).stream().anyMatch(tmpUser -> tmpUser.getRole() == role))
            throw new UserConflictException("There is already a user with given privileges");

        user.setPerson(person);
        user.setRole(role);
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());

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
        sender.send(message);

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
}
