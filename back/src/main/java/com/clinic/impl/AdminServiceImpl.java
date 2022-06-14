package com.clinic.impl;

import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.Person;
import com.clinic.entities.Role;
import com.clinic.entities.User;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.UserConflictException;
import com.clinic.repositories.RoleRepository;
import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    private PersonService personService;
    private UserService userService;

    private RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(PersonService ps, UserService us, RoleRepository rp) {
        this.personService = ps;
        this.roleRepository = rp;
        this.userService = us;
    }


    @Override
    public User createUser(SimpleUserRegistration userData)
            throws UserConflictException, PersonConflictException {
        Person person = personService.save(userData.getPerson());

        User user = new User();
        Role role = roleRepository.findByRole(userData.getRole());

        if (userService.getUsersByPersonId(person.getId()).stream().anyMatch(tmpUser -> tmpUser.getRole() == role))
            throw new UserConflictException("There is already a user with given privileges");

        user.setPerson(person);
        user.setRole(role);
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());

        return userService.save(user);
        //emailService.sendRegistrationMessageToOfficer(officer.getId(), userData);
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
