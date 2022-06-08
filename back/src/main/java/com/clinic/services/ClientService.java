package com.clinic.services;

import com.clinic.entities.Client;
import com.clinic.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientService {

    @Transactional
    Client save(Client client);

    @Transactional
    void delete(Client client);

    List<Client> getAllClients();

}
