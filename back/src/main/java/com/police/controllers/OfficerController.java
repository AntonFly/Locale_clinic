package com.police.controllers;

import com.police.entities.Call;
import com.police.entities.Officer;
import com.police.entities.Person;
import com.police.entities.User;
import com.police.entities.enums.OfficerStatus;
import com.police.services.CallService;
import com.police.services.OfficerService;
import com.police.services.PeopleArchiveService;
import com.police.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/officer")
public class OfficerController {

    private OfficerService officerService;
    private UserService userService;
    private CallService callService;
    private PeopleArchiveService peopleArchiveService;

    @Autowired
    public OfficerController(OfficerService os, UserService us, CallService cs, PeopleArchiveService pas){
        this.officerService = os;
        this.userService = us;
        this.callService = cs;
        this.peopleArchiveService = pas;
    }

    @GetMapping("/info")
    public Officer info(Authentication authentication, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");

        String username = (String) authentication.getPrincipal();
        User user = userService.getUserByUsername(username);
        return officerService.getOfficerByUser(user);
    }

    @GetMapping("/call_history")
    public List<Call> callHistory(Authentication authentication, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username = (String) authentication.getPrincipal();
        User user = userService.getUserByUsername(username);
        Officer officer = officerService.getOfficerByUser(user);
        return callService.getCallsByOfficer(officer);
    }

    @GetMapping("/active_call")
    public List<Call> activeCall(Authentication authentication, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username = (String) authentication.getPrincipal();
        User user = userService.getUserByUsername(username);
        Officer officer = officerService.getOfficerByUser(user);
        return callService.getActiveCallByOfficer(officer);
    }

    @PostMapping("/active_call")
    public Call addOfficerToCall(Authentication authentication, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username = (String) authentication.getPrincipal();
        User user = userService.getUserByUsername(username);
        Officer officer = officerService.getOfficerByUser(user);
        officer.setPremium(officer.getPremium() + 10L);
        officerService.save(officer);
        officerService.changeStatus(officer, OfficerStatus.WAITING);
        List<Call> calls = callService.getActiveCallByOfficer(officer);
        if (calls.size() == 0)
            return null;
        if(officerService.getOfficersByCall(calls.get(0)).stream()
                .filter(a -> a.getOfficerStatus().equals(OfficerStatus.CALLED))
                .count() == 0)
            callService.finishCall(calls.get(0).getId());

        return calls.get(0);
    }

    @GetMapping("/search")
    public List<Person> search(@RequestParam(required = false) Long passport,
                               @RequestParam(required = false, defaultValue = "") String name,
                               @RequestParam(required = false, defaultValue = "") String surname, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return peopleArchiveService.getPeopleByAttributes(passport, name, surname);
    }

}
