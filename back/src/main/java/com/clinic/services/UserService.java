package com.clinic.services;

import com.clinic.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    @Transactional
    User save(User user);

    @Transactional
    void delete(User user);

    @Transactional
    User setRole(User user, String roleName);

    List<User> getUsersByPersonId(Long personId);
    List<User> getAllUsers();

}
