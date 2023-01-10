package com.clinic.impl;

import com.clinic.dto.SimplePwdDropRequest;
import com.clinic.entities.PwdDropRequest;
import com.clinic.entities.User;
import com.clinic.entities.enums.ERole;
import com.clinic.exceptions.UserNotFoundException;
import com.clinic.repositories.PwdDropRequestRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private PersonService personService;

    private final PwdDropRequestRepository pwdDropRequestRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(
            PersonService ps,
            PwdDropRequestRepository pwdr,
            RoleRepository rr,
            UserRepository ur)
    {
        this.personService = ps;
        this.pwdDropRequestRepository = pwdr;
        this.roleRepository = rr;
        this.userRepository = ur;
    }

    @Override
    public PwdDropRequest createPwdDropRequest(SimplePwdDropRequest pwdDropRequestData)
            throws UserNotFoundException
    {
        User user = userRepository.findByEmail(pwdDropRequestData.getEmail())
                .orElseThrow(() -> new UserNotFoundException("email", pwdDropRequestData.getEmail()));

        PwdDropRequest pwdDropRequest = new PwdDropRequest();
        pwdDropRequest.setUser(user);
        pwdDropRequest.setRequestDate(Calendar.getInstance());

        return pwdDropRequestRepository.save(pwdDropRequest);
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
        user.setRole(roleRepository.findByName(ERole.valueOf(roleName)));
        return user;
    }

    @Override
    public boolean existsByEmail(String email)
    { return userRepository.existsByEmail(email); }
    @Override
    public List<User> getUsersByPersonId(Long id) {
        return userRepository.findAllByPersonId(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
