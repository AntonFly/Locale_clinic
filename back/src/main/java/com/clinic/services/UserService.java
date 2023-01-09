package com.clinic.services;

import com.clinic.dto.SimplePwdDropRequest;
import com.clinic.entities.PwdDropRequest;
import com.clinic.entities.User;
import com.clinic.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    PwdDropRequest createPwdDropRequest(SimplePwdDropRequest pwdDropRequestData)
            throws UserNotFoundException;

    @Transactional
    User save(User user);

    @Transactional
    void delete(User user);

    @Transactional
    User setRole(User user, String roleName);

    boolean existsByEmail(String email);

    List<User> getUsersByPersonId(Long personId);
    List<User> getAllUsers();

}
