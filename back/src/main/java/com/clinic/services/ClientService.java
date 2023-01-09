package com.clinic.services;

import com.clinic.dto.ExistingPersonClientRegistration;
import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleModificationAdd;
import com.clinic.entities.Client;
import com.clinic.entities.User;
import com.clinic.exceptions.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    @Transactional
    Client save(SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException;

    @Transactional
    Client save(ExistingPersonClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException;

    @Transactional
    void delete(Client client);

    Client getClientByPassport(Long passport)
            throws PassportNotFoundException, ClientNotFoundException;

    Client getClient(Long clientId) throws ClientNotFoundException;

    List<Client> getAllClients();

    Client updateClient(SimpleClientRegistration clientInfo, Long clientId);

    Client addPreviousModifications(SimpleModificationAdd modificationAdd)
            throws ClientNotFoundException, ModificationNotFoundException;;



}
