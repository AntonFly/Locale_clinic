package com.police.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.entities.Call;
import com.police.entities.Officer;
import com.police.entities.User;
import com.police.entities.enums.CrimeType;
import com.police.entities.enums.Rank;
import com.police.services.CallService;
import com.police.services.OfficerService;
import com.police.services.PeopleArchiveService;
import com.police.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {

    private OfficerService officerService;
    private UserService userService;
    private CallService callService;
    private PeopleArchiveService peopleArchiveService;

    @Autowired
    public DispatcherController(OfficerService os, UserService us, CallService cs, PeopleArchiveService pas) {
        this.officerService = os;
        this.userService = us;
        this.callService = cs;
        this.peopleArchiveService = pas;
    }

    @PostMapping("/create_call")
    public Call createCall(Authentication authentication, HttpServletResponse httpServletResponse, @RequestBody String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String[] tempData = data.split(",");
            String callData = tempData[0] + ',' + tempData[1] + ',' + tempData[2] + '}';
            String crimeData = tempData[3].split(":")[1].replace('}', ' ').trim();
            Call call = objectMapper.readValue(callData, Call.class);
            CrimeType crimeType = objectMapper.readValue(crimeData, CrimeType.class);
            String username = (String) authentication.getPrincipal();
            User user = userService.getUserByUsername(username);
            Officer dispatcher = officerService.getOfficerByUser(user);
            call = callService.createCall(call.getDescription(), call.getCallLocation(), dispatcher.getId(), crimeType);
            if (call == null)
                throw new IOException();
            return call;
        }
        catch (IOException e) {
            return null;
        }
    }

}
