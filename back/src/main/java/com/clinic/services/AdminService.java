package com.clinic.services;

import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.User;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.UserConflictException;

import java.util.List;

public interface AdminService {

    User createUser(SimpleUserRegistration clinicStaffData) throws PersonConflictException, UserConflictException;

    User changeUserData(User user);

    List<User> getAllUsers();
}
