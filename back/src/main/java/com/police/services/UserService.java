package com.police.services;

import com.police.entities.User;
import com.police.openam.SimpleUserRegistration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {

    SimpleUserRegistration registerUser(SimpleUserRegistration user);

    User getUserByUsername(String username);

    boolean userExists(String username);

    @Transactional
    User save(User user);

    @Transactional
    void delete(User user);

    @Transactional
    User setRole(User user, String roleName);

    List<User> getAllUsers();

}
