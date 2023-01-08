package com.clinic.controllers;

import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.User;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.exceptions.UserConflictException;
import com.clinic.repositories.ScenarioRepository;
import com.clinic.services.AdminService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
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
            throws PersonConflictException, UserConflictException, PassportConflictException
    {
        return adminService.createUser(clinicStaffData);
    }

    @GetMapping("/test")
    public void findUsers() {
    }

    @GetMapping("get_all_users")
    public List<User> getAllUsers()
    { return adminService.getAllUsers(); }

}
