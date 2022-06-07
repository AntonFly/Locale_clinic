package com.police.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.entities.District;
import com.police.entities.Location;
import com.police.entities.User;
import com.police.entities.enums.CrimeType;
import com.police.openam.SimpleUserRegistration;
import com.police.repositories.DistrictRepository;
import com.police.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping()
public class MainController {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    private OfficerService officerService;

    @Autowired
    private CrimeService crimeService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CallService callService;

    @Autowired
    private AmqpService amqpService;

    @GetMapping("/hello_officer")
    public String hello(Authentication authentication){

        String username = (String) authentication.getDetails();

        return "Hello, " + username + "!!, with role" + authentication.getAuthorities().toString();

    }

    @GetMapping("/home")
    public String home(){

        return "I hate myself";

    }

    @GetMapping("/crime")
    public String crime(){
        crimeService.getAllCrimes().forEach(e -> {
            District district = e.getCrimeLocation().getDistrict();
            district.setCrimeNumber(district.getCrimeNumber() + 1);
            districtRepository.save(district);
        });
        return "success";
    }

    @GetMapping("/rmq")
    public String rmq(@RequestParam long id){
        amqpService.sendMessageToOfficer("Hello officer", id);
        return "sent";
    }


    @GetMapping("/test")
    public String test(){

        officerService.prepareDayShift();
        return "testing shifts";
    }

    @GetMapping("/init")
    public String init(){
        userService.getAllUsers().forEach(a->{
            SimpleUserRegistration simpleUserRegistration = new SimpleUserRegistration();
            simpleUserRegistration.setUsername(a.getUsername());
            simpleUserRegistration.setUserPassword("12345678");
            userService.registerUser(simpleUserRegistration);
        });

        return "success";

    }
}
