package com.clinic.controllers;

import com.clinic.dto.SimpleClinicStaffRegistration;
import com.clinic.dto.SimpleUserRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Specialization;
import com.clinic.repositories.SpecializationRepository;
import com.clinic.services.AdminService;
import com.clinic.services.ClientService;
import com.clinic.services.SpecializationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/manager")
public class ManagerController {

    private SpecializationService specializationService;
    private ClientService clientService;

    @Autowired
    public ManagerController(
            SpecializationService ss,
            ClientService cs
    ){
        this.specializationService = ss;
        this.clientService = cs;
    }

    @GetMapping("/get_specs")
    public List<Specialization> getSpecs(){
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/get_clients")
    public List<Client> getClients(){
        return clientService.getAllClients();
    }

/*    @PostMapping("/create_user")
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
    }*/

}
