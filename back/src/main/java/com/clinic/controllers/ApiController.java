package com.clinic.controllers;

import com.clinic.dto.SimpleModificationAdd;
import com.clinic.entities.Client;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.ModificationNotFoundException;
import com.clinic.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api")
public class ApiController {

    private final ClientService clientService;

    @Autowired
    public ApiController(
            ClientService cs
    ){
        this.clientService = cs;
    }

    @PostMapping("/add_previous_modification")
    public Client addPreviousModifications(@RequestBody SimpleModificationAdd modificationAdd)
            throws ClientNotFoundException, ModificationNotFoundException
    { return  clientService.addPreviousModifications(modificationAdd); }
}
