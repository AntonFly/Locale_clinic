package com.clinic.services;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.User;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.PersonConflictException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientService {

    @Transactional
    Client save(SimpleClientRegistration clientData) throws PersonConflictException, ClientConflictException;

    @Transactional
    void delete(Client client);

    List<Client> getAllClients();

}
