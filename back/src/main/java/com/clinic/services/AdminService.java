package com.clinic.services;

import com.clinic.dto.SimpleClinicStaffRegistration;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.User;

import java.util.List;

public interface AdminService {

    SimpleUserRegistration createStaffUser(SimpleClinicStaffRegistration clinicStaffData);

    User changeUserData(User user);

    List<User> getAllUsers();
}
