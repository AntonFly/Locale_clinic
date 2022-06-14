package com.clinic.controllers;

import com.clinic.configs.email.MessageConfig;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.dto.TestClass;
import com.clinic.entities.Client;
import com.clinic.entities.Modification;
import com.clinic.entities.Specialization;
import com.clinic.entities.User;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.UserConflictException;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.ModificationRepository;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.services.UserService;
import com.clinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController()
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(
            AdminService as
    ){
        this.adminService = as;
    }

    @GetMapping("/hm")
    public String gmu(){
        return "why spring?";
    }

    @PostMapping("/create_user")
    public User createUser(@RequestBody SimpleUserRegistration clinicStaffData)
    throws PersonConflictException, UserConflictException
    {
        return adminService.createUser(clinicStaffData);
    }

    @GetMapping("/test")
    public void findUsers() {

    }

}
