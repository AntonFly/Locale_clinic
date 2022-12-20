package com.clinic.controllers;

import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.User;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.UserConflictException;
import com.clinic.repositories.ScenarioRepository;
import com.clinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
