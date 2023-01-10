package com.clinic.services;

import com.clinic.dto.SimplePwdDropRequestSatisfaction;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.PwdDropRequest;
import com.clinic.entities.User;
import com.clinic.exceptions.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {

    List<PwdDropRequest> getAllPwdDropRequests();

    @Transactional(rollbackFor = Exception.class)
    PwdDropRequest satisfyDropRequest(SimplePwdDropRequestSatisfaction dropRequestData)
            throws PwdDropRequestNotFoundException, PwdDropRequestAlreadySatisfiedException;

    @Transactional(rollbackFor = Exception.class)
    User createUser(SimpleUserRegistration clinicStaffData)
            throws PersonConflictException, UserConflictException, PassportConflictException;

    User changeUserData(User user);

    List<User> getAllUsers();
}
