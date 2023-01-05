package com.clinic.configs.impl;

import com.clinic.entities.User;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private PersonService personService;

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(PersonService ps, UserRepository ur, RoleRepository rr){
        this.personService = ps;
        this.userRepository = ur;
        this.roleRepository = rr;
    }

    @Override
    public User save(User user) {
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User setRole(User user, String roleName) {
        user.setRole(roleRepository.findByRole(roleName));
        return user;
    }

    @Override
    public List<User> getUsersByPersonId(Long id) {
        return userRepository.findAllByPersonId(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
