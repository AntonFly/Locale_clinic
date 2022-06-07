package com.police.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.entities.Officer;
import com.police.openam.SimpleOfficerRegistration;
import com.police.openam.SimpleUserRegistration;
import com.police.services.AdminService;
import com.police.validators.OfficerValidator;
import com.rabbitmq.client.AMQP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;

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

    @PostMapping("/create_officer")
    public String createOfficer(@RequestBody SimpleOfficerRegistration officerData){
        officerData.setJabber("rand_jab");

        SimpleUserRegistration registration = adminService.createOfficerUser(officerData);
        if (registration == null)
            return "Fail creating officer user, please check input data";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(registration);
        }
        catch (JsonProcessingException e){
            return "Fail creating officer user";
        }
    }

}
