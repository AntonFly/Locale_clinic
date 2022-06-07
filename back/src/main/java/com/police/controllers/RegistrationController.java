package com.police.controllers;

import com.police.configs.openam.OpenAmProperties;
import com.police.openam.SimpleUserRegistration;
import com.police.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping()
public class RegistrationController {

    private UserService userService;
    private OpenAmProperties properties;

    @Autowired
    public RegistrationController(UserService userService, OpenAmProperties oap){
        this.properties = oap;
        this.userService = userService;

    }

    @PostMapping("/registration")
    public String registration(@RequestBody SimpleUserRegistration user, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        SimpleUserRegistration registration = userService.registerUser(user);

        if (registration == null)
            return "fail";

        return registration.getUsername() + " " + registration.getUserPassword();
    }

    @GetMapping("/login")
    public void login(HttpServletResponse httpServletResponse){
        try {
            httpServletResponse.sendRedirect(properties.getLogin());
        }
        catch (IOException e){
            httpServletResponse.setStatus(404);
        }
    }

}
