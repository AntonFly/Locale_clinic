package com.clinic.controllers;

import com.clinic.dto.SimpleClinicStaffRegistration;
import com.clinic.dto.SimpleUserRegistration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.clinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService as){
        this.adminService = as;
    }

    @GetMapping("/hm")
    public String gmu(){
        return "why spring?";
    }

    @PostMapping("/create_user")
    public String createClinicStaffUser(@RequestBody SimpleClinicStaffRegistration clinicStaffData){

        SimpleUserRegistration registration = adminService.createStaffUser(clinicStaffData);
        if (registration == null)
            return "Fail creating officer user, please check input data";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(registration);
        }
        catch (JsonProcessingException e){
            return "Fail creating clinic staff user";
        }
    }

}
