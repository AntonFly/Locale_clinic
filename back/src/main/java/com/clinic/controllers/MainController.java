package com.clinic.controllers;

import com.clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello_officer")
    public String hello(Authentication authentication){

        String username = (String) authentication.getDetails();

        return "Hello, " + username + "!!, with role" + authentication.getAuthorities().toString();

    }

    @GetMapping("/home")
    public String home(){

        return "I hate myself";

    }

}
