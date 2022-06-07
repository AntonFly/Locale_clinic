package com.police.services;

import com.police.entities.Officer;
import com.police.entities.User;
import com.police.openam.SimpleOfficerRegistration;
import com.police.openam.SimpleUserRegistration;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AdminService {

    SimpleUserRegistration createOfficerUser(SimpleOfficerRegistration officerData);

    User changeUserData(User user);

    List<User> getAllUsers();
}
