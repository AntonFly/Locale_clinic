package com.clinic.services;

import com.clinic.dto.ExistingPersonClientRegistration;
import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimpleImplantsUpdate;
import com.clinic.dto.SimpleModificationAdd;
import com.clinic.entities.Client;
import com.clinic.exceptions.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientService {

    @Transactional(rollbackFor = Exception.class)
    Client createClient(SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException;

    @Transactional(rollbackFor = Exception.class)
    Client createClient(ExistingPersonClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException;

    @Transactional(rollbackFor = Exception.class)
    void delete(Client client);

    Client getClientByPassport(Long passport)
            throws PassportNotFoundException, NoPersonToClientException;

    Client getClient(Long clientId)
            throws ClientNotFoundException;

    List<Client> getAllClients();

    Client updateClient(SimpleClientRegistration clientInfo, Long clientId)
            throws ClientNotFoundException;

    Client addPreviousModifications(SimpleModificationAdd modificationAdd)
            throws ClientNotFoundException, ModificationNotFoundException;

    Client addImplants(SimpleImplantsUpdate implantsUpdate)
            throws ClientNotFoundException;

    Boolean dropImplant(long implantId)
            throws ImplantNotFountException;

}
