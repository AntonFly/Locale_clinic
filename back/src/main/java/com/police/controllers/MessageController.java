package com.police.controllers;

import com.police.entities.Officer;
import com.police.entities.User;
import com.police.services.AmqpService;
import com.police.services.OfficerService;
import com.police.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
public class MessageController {

    private AmqpService amqpService;
    private UserService userService;
    private OfficerService officerService;

    @Autowired
    public MessageController(UserService us, OfficerService os, AmqpService as){
        this.amqpService = as;
        this.officerService = os;
        this.userService = us;
    }

    @GetMapping(value = "/officer/message",
    produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> receiveMessage(Authentication authentication){
        String username = (String) authentication.getPrincipal();
        User user = userService.getUserByUsername(username);
        Officer officer = officerService.getOfficerByUser(user);
        return amqpService.receiveMessageForOfficer(officer.getId());
    }

}
