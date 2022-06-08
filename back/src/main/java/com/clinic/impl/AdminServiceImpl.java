package com.clinic.impl;

import com.clinic.dto.SimpleClinicStaffRegistration;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.Person;
import com.clinic.entities.User;
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
    public SimpleUserRegistration createStaffUser(SimpleClinicStaffRegistration userData) {
        Person person = new Person();
        person.setName(userData.getName());
        person.setSurname(userData.getSurname());
        person.setPatronymic(userData.getPatronymic());
        person.setId(userData.getPassport());

        personService.save(person);

        User user = new User();
        user.setPerson(personService.getPersonById(userData.getPassport()).get());
        user.setRole(roleRepository.findByRole(userData.getRole()));
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());

        user = userService.save(user);
        //emailService.sendRegistrationMessageToOfficer(officer.getId(), userData);

        SimpleUserRegistration userRegData = new SimpleUserRegistration();
        userRegData.setEmail(user.getEmail());
        userRegData.setPassword(user.getPassword());
        return userRegData;

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
