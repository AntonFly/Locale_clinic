package com.clinic.controllers;

import com.clinic.dto.SimplePwdDropRequest;
import com.clinic.entities.PwdDropRequest;
import com.clinic.exceptions.UserNotFoundException;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(
            UserService userService
    )
    {
        this.userService = userService;
    }

    @PostMapping("/create_pwd_drop_request")
    public PwdDropRequest createPwdDropRequest(@RequestBody SimplePwdDropRequest pwdDropRequestData)
            throws UserNotFoundException
    { return userService.createPwdDropRequest(pwdDropRequestData); }
}
